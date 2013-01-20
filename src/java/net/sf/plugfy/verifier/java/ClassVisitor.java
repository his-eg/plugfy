package net.sf.plugfy.verifier.java;

import java.util.Arrays;

import net.sf.plugfy.verifier.VerificationContext;

import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.EmptyVisitor;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;
import org.apache.bcel.generic.TypedInstruction;

/**
 * visits instructions
 *
 * @author hendrik
 */
class ClassVisitor extends EmptyVisitor {

    private final ConstantPoolGen cpg;
    private final VerificationContext context;

    /**
     * class visitor
     *
     * @param cpg ConstantPoolGen
     * @param verificationContext VerificationContext
     */
    ClassVisitor(ConstantPoolGen cpg, VerificationContext verificationContext) {
        this.cpg = cpg;
        this.context = verificationContext;
    }

    @Override
    public void visitInvokeInstruction(final InvokeInstruction invokeInstruction) {
        Type type = invokeInstruction.getReferenceType(this.cpg);
        SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), type.getSignature());

        // TODO: check that the method exists in the target type
        // TODO: check that the message signature is compatible
        System.out.println("invoke: " + type + " " + invokeInstruction.getMethodName(this.cpg));
        System.out.println("    Return: " + invokeInstruction.getType(this.cpg));
        System.out.println("    Params: " + Arrays.toString(invokeInstruction.getArgumentTypes(this.cpg)));
        System.out.println("    Except: " + Arrays.toString(invokeInstruction.getExceptions()));
    }

    @Override
    public void visitTypedInstruction(final TypedInstruction typedinstruction) {
        Type type = typedinstruction.getType(this.cpg);
        while (type instanceof ArrayType) {
            type = ((ArrayType) type).getElementType();
        }

        if (type instanceof ObjectType) {
            SignatureUtil.checkSignatureDependencies(context.getRepository(), context.getResult(), type.getSignature());
        }
    }

}
