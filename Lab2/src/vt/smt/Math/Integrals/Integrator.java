package vt.smt.Math.Integrals;


import java.util.function.Function;

public interface Integrator {
    // With precision
    Double integrate(Function<? super Number,? extends Number> function,Double from, Double to, Double precision);
    // Clearly set the numbers of separations
    Double integrateSeparation(Function<? super Number,? extends Number> function, Double from, Double to, Integer steps);
}
