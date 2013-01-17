package net.sf.plugfy;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import net.sf.plugfy.util.Predicate;
import net.sf.plugfy.util.Util;
import net.sf.plugfy.verifier.VerificationResult;
import net.sf.plugfy.verifier.container.JarVerifier;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.TypedInstruction;

/**
 * a proof of concept demo
 *
 * @author hendrik
 */
public class Demo extends EmptyVisitor {
    private ConstantPoolGen cpg;

    /**
     * demo
     */
    public void demo() {
        JavaClass javaClass = Repository.lookupClass("net.sf.plugfy.Demo");
        cpg = new ConstantPoolGen(javaClass.getConstantPool());

        // TODO: handle class parents and interfaces
        // TODO: handle class generic types
        // TODO: handle class fields
        // TODO: handle method parameters
        // TODO: handle method return types
        // TODO: handle method thrown exceptions

        Method[] methods = javaClass.getMethods();
        System.out.println("Methods:");
        for (Method method : methods) {
            System.out.println(method);
        }
        Method method = Util.find(methods, new Predicate<Method>() {

            @Override
            public boolean apply(Method input) {
                return input.getName().equals("demo");
            }
        });
        System.out.println("-----------------------");

        Code code = method.getCode();
        System.out.println("Code: " + code);
        System.out.println("-----------------------");

        MethodGen mg = new MethodGen(method, javaClass.getClassName(), cpg);
        System.out.println(mg.getInstructionList());
        System.out.println("-----------------------");


        InstructionHandle handle = mg.getInstructionList().getStart();
        while (handle != null) {
            Instruction instruction = handle.getInstruction();
            instruction.accept(this);
            handle = handle.getNext();
        }
    }
    @Override
    public void visitInvokeInstruction(InvokeInstruction invokeInstruction) {
        System.out.println("invoke: " + invokeInstruction.getClassName(cpg) + " " + invokeInstruction.getMethodName(cpg));
        System.out.println("    Return: " + invokeInstruction.getType(cpg));
        System.out.println("    Params: " + Arrays.toString(invokeInstruction.getArgumentTypes(cpg)));
        System.out.println("    Except: " + Arrays.toString(invokeInstruction.getExceptions()));
    }

    @Override
    public void visitTypedInstruction(TypedInstruction typedinstruction) {
        System.out.println("type: " + typedinstruction.getType(cpg));
    }


    /**
     * executes the demo
     *
     * @param args ignored
     * @throws IOException
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        String filename = "/home/brummermann/workspace/HEAD/cs.sys.configuration.center.iface/dist/cs.sys.configuration.center.iface.jar";
        System.out.println(new URLClassLoader(new URL[] {new File(filename).toURI().toURL()}).getResource("extension.beans.spring.xml"));
        VerificationResult result = new VerificationResult();
        new JarVerifier().verify(new File(filename).toURI().toURL(), result);
        System.out.println(result);
    }

}
