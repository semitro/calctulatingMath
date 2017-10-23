package vt.smt.Math.Integrals;


import java.util.function.Function;

public interface Integrator {
    // With precision
    Double integrate(Function<Double,Double> function,Double from, Double to, Double precision);
    // Clearly set the numbers of separations
    Double integrateSeparation(Function<Double, Double> function, Double from, Double to, Integer steps);
}
