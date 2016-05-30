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
import java.net.URL;

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
import org.apache.bcel.util.Repository;

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
    public void verify(String name, final VerificationContext verificationContext) throws IOException {
        name = slash2dot(name);
        if (!name.startsWith(".")) name = "." + name;
        this.context = verificationContext;
        try {
            URL underVerification = verificationContext.getUnderVerification();
            String prefix = underVerification.getFile();
            prefix = slash2dot(prefix);
            String nameWithoutPrefix = name.replace(prefix, "");
            String className = slash2dot(nameWithoutPrefix).replaceAll("\\.class$", "");
            Repository repository = this.context.getRepository();
            this.javaClass = repository.loadClass(className);
        } catch (final ClassNotFoundException e) {
            throw new IOException(e);
        }
        this.cpg = new ConstantPoolGen(this.javaClass.getConstantPool());
        this.visitor = new ClassVisitor(this.javaClass, this.cpg, verificationContext);

        this.analyzeClassSignature();
        this.analyzeFields();
        this.analyzeMethods();
    }


    private String slash2dot(String path) {
        return path.replace('/', '.').replace('\\', '.');
    }

    /**
     * analyze the class signature
     */
    private void analyzeClassSignature() {
        SignatureUtil.checkSignatureDependencies(this.context.getRepository(), this.context.getResult(), this.javaClass);
    }

    /**
     * analyze fields
     */
    private void analyzeFields() {
        for (final Field field : this.javaClass.getFields()) {
            this.analyzeField(field);
        }
    }

    /**
     * analyze field
     *
     * @param field field
     */
    private void analyzeField(final Field field) {
        SignatureUtil.checkSignatureDependencies(this.context.getRepository(), this.context.getResult(), field, this.javaClass.getClassName());
    }

    /**
     * analyze methods
     */
    private void analyzeMethods() {
        for (final Method method : this.javaClass.getMethods()) {
            this.analyzeMethod(method);
        }
    }

    /**
     * analyze method
     *
     * @param method method
     */
    private void analyzeMethod(final Method method) {

        // check real and generic signature of the method
        SignatureUtil.checkSignatureDependencies(this.context.getRepository(), this.context.getResult(), method, this.javaClass.getClassName());

        // check types of variables
        final LocalVariableTable localVariableTable = method.getLocalVariableTable();
        if (localVariableTable != null) {
            for (final LocalVariable localVariable : localVariableTable.getLocalVariableTable()) {
                SignatureUtil.checkSignatureDependencies(this.context.getRepository(), this.context.getResult(), localVariable.getSignature(), this.javaClass.getClassName());
            }
        }

        // check code
        final MethodGen mg = new MethodGen(method, this.javaClass.getClassName(), this.cpg);
        final InstructionList instructionList = mg.getInstructionList();
        if (instructionList != null) {
            InstructionHandle handle = instructionList.getStart();
            while (handle != null) {
                final Instruction instruction = handle.getInstruction();
                instruction.accept(this.visitor);
                handle = handle.getNext();
            }
        }
    }
}
