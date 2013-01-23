/*
 *  Copyright 2013
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may use this file in compliance with the Apache License, Version 2.0.
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.sf.plugfy.verifier.java;

import java.io.IOException;

import net.sf.plugfy.verifier.VerificationContext;
import net.sf.plugfy.verifier.Verifier;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;

/**
 * verifies a java class
 *
 * @author hendrik
 */
public class ClassVerifier implements Verifier {
    private VerificationContext context;
    private JavaClass javaClass;
    private ConstantPoolGen cpg;
    private ClassVisitor visitor;

    /**
     * verifies the resource
     * @param name filename
     * @param verificationContext verification context
     *
     * @throws IOException in case of an input/output error
     */
    @Override
    public void verify(String name, VerificationContext verificationContext) throws IOException {
        this.context = verificationContext;
        try {
            javaClass = context.getRepository().loadClass(name.replace('/', '.').replaceAll("\\.class$", ""));
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        cpg = new ConstantPoolGen(javaClass.getConstantPool());
        visitor = new ClassVisitor(javaClass, cpg, verificationContext);

        analyzeClassSignature();
        analyzeFields();
        analyzeMethods();
    }

    /**
     * analyze the class signature
     */
    private void analyzeClassSignature() {
        SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), javaClass);
    }

    /**
     * analyze fields
     */
    private void analyzeFields() {
        for (Field field : javaClass.getFields()) {
            analyzeField(field);
        }
    }

    /**
     * analyze field
     *
     * @param field field
     */
    private void analyzeField(Field field) {
        SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), field);
    }

    /**
     * analyze methods
     */
    private void analyzeMethods() {
        for (Method method : javaClass.getMethods()) {
            analyzeMethod(method);
        }
    }

    /**
     * analyze method
     *
     * @param method method
     */
    private void analyzeMethod(Method method) {

        // check real and generic signature of the method
        SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), method);

        // check types of variables
        LocalVariableTable localVariableTable = method.getLocalVariableTable();
        if (localVariableTable != null) {
            for (LocalVariable localVariable : localVariableTable.getLocalVariableTable()) {
                SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), localVariable.getSignature());
            }
        }

        // check code
        final MethodGen mg = new MethodGen(method, javaClass.getClassName(), this.cpg);
        InstructionList instructionList = mg.getInstructionList();
        if (instructionList != null) {
            InstructionHandle handle = instructionList.getStart();
            while (handle != null) {
                final Instruction instruction = handle.getInstruction();
                instruction.accept(visitor);
                handle = handle.getNext();
            }
        }
    }
}
