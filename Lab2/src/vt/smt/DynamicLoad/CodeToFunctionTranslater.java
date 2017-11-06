package vt.smt.DynamicLoad;

import java.util.function.BiFunction;
import java.util.function.Function;


public interface CodeToFunctionTranslater {
    void setCode(String code);
    void setCode(String code, int arg_count);
    Function<? super Number,? extends Number> getFunction() throws ReflectiveOperationException;
    BiFunction<? extends Number, ? extends Number, ? extends Number> getBiFunction();
}
