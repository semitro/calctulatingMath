package vt.smt.DynamicLoad;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Function;

/**
 * Created by semitro on 26.09.17.
 */

public class FunctionLoader {

    public Function<Double,Double> getFunction(){
        return function;
    }
    public FunctionLoader()throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException{
        reloadFunction();
    }
    private Function<Double,Double> function;
    private Class loadedClass = null;

    public void reloadFunction() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException{
        loadedClass = compileAndLoadTheClass();
        function = new Function<Double,Double>(){
            @Override
            public Double apply(Double aDouble) {
                try {
                   return (Double)loadedClass.getMethods()[0].invoke(loadedClass, aDouble);
                }catch (Exception e){
                    System.out.println("reloadFunction()::createNewFunction");
                    System.out.println("loadedClass.getMethods()[0].invoke(loadedClass, 10)");
                    e.printStackTrace();
                }
                return -0.0;
            }
        };
    }

    private Class compileAndLoadTheClass() throws IOException, ClassNotFoundException{
        Runtime.getRuntime().exec("javac DynamicFunction/Function.java");
        try {
            Thread.currentThread().sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        File file = new File(System.getProperty("user.dir"));
        URL[] urls = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);
        try {
            System.out.println();
        }catch (Exception e){
            System.out.println("Ошибка в вызове функции динамически подгружаемого класса.");
            e.printStackTrace();
        }
        return loader.loadClass("DynamicFunction.Function");
    }
}
