package vt.smt.Math;


import java.util.function.Function;

public interface Integrator {
    Double integrate(Function<Double,Double> function,Double from, Double to, Double precision);
}
