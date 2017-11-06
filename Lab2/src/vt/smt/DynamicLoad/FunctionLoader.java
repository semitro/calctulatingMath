package vt.smt.DynamicLoad;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Итак, этот класс умеет загружать функцию из файла
 * <p>
 * То есть
 * Концепция программы:
 * Чтобы динамечески обрабиться к любую математическую функции как функции Java,
 * Мы компилируем исходники Java, в которые другим модулем программы записана функция
 * Исходники Java-классы, оттуда мы вытаскиваем класс-считатель и функцию-считалку
 *
 * <p>
 * Предполагается, что файл для компиляции лежит в
 * (директория Запуска Jar-ника)/DynamicFunction/Function.java
 * @author Ощепков А.А.
 * @since v1.0
 */

class FunctionLoader {
    // Искомая математическая функция одной переменной
    public Function<Number,Number> getFunction(){
        return function;
    }
    public FunctionLoader()
            throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException{
        reloadFunction();
    }
    // Сама функция
    private Function<Number,Number> function;
    private BiFunction<Number, Number, Number> biFunction;
    // Класс, подгруженный из файла
    private Class loadedClass = null;
    // Когда файл меняется, возникает необходимость перекомпилировать функцию
    public void reloadFunction() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException{
        loadedClass = compileAndLoadTheClass();
        function = new Function<Number,Number>(){
            @Override
            public Number apply(Number arg) {
                try {
                    if(loadedClass.getMethods()[0].getName().equals("f"))
                        return (Number)loadedClass.getMethods()[0].invoke(loadedClass, arg);
                         else
                        return (Number)loadedClass.getMethods()[1].invoke(loadedClass, arg);

                }catch (Exception e){
                    System.out.println("reloadFunction()::createNewFunction");
                    System.out.println("loadedClass.getMethods()[0].invoke(loadedClass, 10)");
                    e.printStackTrace();
                }
                return -0.0;
            }
        };
        biFunction = (x,y)->{
            try {
                if(loadedClass.getMethods()[0].getName().equals("f_2"))
                    return (Number)loadedClass.getMethods()[0].invoke(loadedClass, x, y);
                else
                    return (Number)loadedClass.getMethods()[1].invoke(loadedClass, x, y);
            }catch (Exception e){
                System.out.println("reloadFunction()::createNewFunction(bi)");
                System.out.println("loadedClass.getMethods()[0].invoke(loadedClass,x,y)");
                e.printStackTrace();
            }
            return -0.0;
        };
    }

    public BiFunction<Number, Number, Number> getBiFunction() {
        return biFunction;
    }

    private Class compileAndLoadTheClass() throws IOException, ClassNotFoundException{
        Runtime.getRuntime().exec("javac ./DynamicFunction/Function.java");
        try {
            // Возможно, это тупо,
            // но нужно дождаться, пока системный вызов запишет результаты компиляции на диск
            Thread.currentThread().sleep(4000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        // Предполагается, что исходники должы храниться в директории
        File file = new File(System.getProperty("user.dir"));
        URL[] urls = {file.toURI().toURL()};
        // Загружаем откомпилированный класс (он лежит рядом с исходником
        ClassLoader loader = new URLClassLoader(urls);
        return loader.loadClass("DynamicFunction.Function");
    }
}
