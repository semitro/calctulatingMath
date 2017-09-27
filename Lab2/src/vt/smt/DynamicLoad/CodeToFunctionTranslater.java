package vt.smt.DynamicLoad;

import java.util.function.Function;


public interface CodeToFunctionTranslater {
    void setCode(String code);
    Function<Number,Number> getFunction() throws ReflectiveOperationException;
}
