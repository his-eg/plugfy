package net.sf.plugfy;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

/**
 * a proof of concept demo
 *
 * @author hendrik
 */
public class Demo {

    /**
     * demo
     */
    public void demo() {
        JavaClass javaClass = Repository.lookupClass("net.sf.plugfy.Demo");
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

        Code code = method.getCode();
        System.out.println("Code: " + code);
    }

    /**
     * executes the demo
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        new Demo().demo();
    }
}
