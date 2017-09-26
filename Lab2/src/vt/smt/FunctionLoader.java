package vt.smt;

/**
 * Created by semitro on 26.09.17.
 */

public class FunctionLoader {

    public static void main(String[] argv){
        try {

            Runtime.getRuntime().exec("javac out/production/Lab2/vt/smt/DynamicFunction/Function.java");
            //FunctionLoader.class.getClassLoader().loadClass("vt.smt.DynamicFunction.Function");
            Thread.currentThread().sleep(5000);
            Class funk = Class.forName("vt.smt.DynamicFunction.Function");
            System.out.println(funk.getMethods()[0].invoke(funk,2.0));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
