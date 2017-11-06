package vt.smt.DynamicLoad;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiFunction;
import java.util.function.Function;

/***
 *
 *  Этот класс позволяет превращать текстовый код функции на языке Java
 *  В непосредственно рабочую функцию, к которой можно обращаться в процессе исполнения
 *  <p>
 *  Помни! Важно запускать программу из каталога DynamicFunction, в котором есть файл Function.java
***/
public class DynamicFunctionManager implements CodeToFunctionTranslater{
    public DynamicFunctionManager()
            throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        functionChanger = new DynamicFunctionChanger();
        functionLoader = new FunctionLoader();
    }
    // Установить код строкой, который мы получим кодом из функции getFunction()
    @Override
    public void setCode(String code){
        functionChanger.setCode(code);
    }

    @Override
    public void setCode(String code, int arg_count) {
        functionChanger.setCode(code,arg_count);
    }

    @Override
    public Function<Number,Number> getFunction()  throws ReflectiveOperationException{
        try{
            functionLoader.reloadFunction();
            return functionLoader.getFunction();
        }catch (IOException | ClassNotFoundException | InvocationTargetException e){
            throw new ReflectiveOperationException("Ошибка при чтении пользовательской функции\nУдостоверсесть," +
                    "что запускаете программу из каталога, содержащем директирую DynamicFunction,\n" +
                    "в котором есть файл Function.java \n В крайнем случае, обратитесь к разработчику:\n" +
                    "vk.com/oshepkovtemka");
        }
    }

    @Override
    public BiFunction<Number, Number, Number> getBiFunction() {
        try{
            functionLoader.reloadFunction();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Это случилось при загрузке BiFunction");
        }
        return functionLoader.getBiFunction();
    }

    private FunctionLoader functionLoader;
    private DynamicFunctionChanger functionChanger;
}
