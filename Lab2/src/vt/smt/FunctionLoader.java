package vt.smt;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by semitro on 26.09.17.
 */

public class FunctionLoader {

    public static void main(String[] argv){
        try {

            Runtime.getRuntime().exec("javac DynamicFunction/Function.java");
            Thread.currentThread().sleep(5000);
            File file = new File(System.getProperty("user.dir"));
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            Class funk = loader.loadClass("DynamicFunction.Function");
            System.out.println(funk.getMethods()[0].invoke(funk, 10));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
