package net.sf.plugfy;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import net.sf.plugfy.util.Predicate;
import net.sf.plugfy.util.Util;

import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypedInstruction;
import org.apache.bcel.util.ClassLoaderRepository;
import org.apache.bcel.util.Repository;

/**
 * a proof of concept demo
 *
 * @author hendrik
 */
public class Demo extends EmptyVisitor {
    private ConstantPoolGen cpg;

    /**
     * demo
     * @throws ClassNotFoundException
     */
    public void demo(Repository repository) throws ClassNotFoundException {
        final JavaClass javaClass = repository.loadClass("net.sf.plugfy.sample.SampleClass");
        this.cpg = new ConstantPoolGen(javaClass.getConstantPool());

        // TODO: handle class parents and interfaces
        // TODO: handle class generic types
        // TODO: handle class fields
        // TODO: handle method parameters
        // TODO: handle method return types
        // TODO: handle method thrown exceptions

        final Method[] methods = javaClass.getMethods();
        System.out.println("Methods:");
        for (final Method method : methods) {
            System.out.println(method);
        }
        final Method method = Util.find(methods, new Predicate<Method>() {

            @Override
            public boolean apply(final Method input) {
                return input.getName().equals("method");
            }
        });
        System.out.println("-----------------------");

        final Code code = method.getCode();
        System.out.println("Code: " + code);
        System.out.println("-----------------------");

        final MethodGen mg = new MethodGen(method, javaClass.getClassName(), this.cpg);
        System.out.println(mg.getInstructionList());
        System.out.println("-----------------------");


        InstructionHandle handle = mg.getInstructionList().getStart();
        while (handle != null) {
            final Instruction instruction = handle.getInstruction();
            instruction.accept(this);
            handle = handle.getNext();
        }

        System.out.println(mg.getInstructionList());
        System.out.println("-----------------------");

        for (LocalVariable localVariable : method.getLocalVariableTable().getLocalVariableTable()) {
            System.out.println(localVariable.getName() + " / " + localVariable.getSignature() + " / " + Type.getType(localVariable.getSignature()));
        }
    }

    @Override
    public void visitInvokeInstruction(final InvokeInstruction invokeInstruction) {
        System.out.println("invoke: " + invokeInstruction.getReferenceType(this.cpg) + " " + invokeInstruction.getMethodName(this.cpg));
        System.out.println("    Return: " + invokeInstruction.getType(this.cpg));
        System.out.println("    Params: " + Arrays.toString(invokeInstruction.getArgumentTypes(this.cpg)));
        System.out.println("    Except: " + Arrays.toString(invokeInstruction.getExceptions()));
    }

    @Override
    public void visitTypedInstruction(final TypedInstruction typedinstruction) {
        System.out.println("type: " + typedinstruction.getType(this.cpg));
    }


    /**
     * executes the demo
     *
     * @param args ignored
     * @throws IOException
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     */
    public static void main(final String[] args) throws MalformedURLException, IOException, ClassNotFoundException {
        final String filename = "sample/sample.jar";
        URL url = new File("sample/sample.jar").toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[] {url});

        new Demo().demo(new ClassLoaderRepository(classLoader));

        /*
        VerificationContext context = new VerificationContext(new ClassLoaderRepository(classLoader));
        new JarVerifier().verify(new File(filename).toURI().toURL(), context);
        System.out.println(context);*/

    }
}
