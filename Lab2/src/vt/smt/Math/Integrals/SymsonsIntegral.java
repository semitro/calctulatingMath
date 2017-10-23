package vt.smt.Math.Integrals;

import java.util.function.Function;

/**
 * Integration using Symson's method
 * The main idea - approximate the square by parabolas
 */
public class SymsonsIntegral extends Integral{
    @Override
    protected Double getAtomSquare(Function<Double, Double> function, Double x1, Double x2) {
        // Just the formula. Square under an approximate parabola
        return (x2-x1)/6.0 * (function.apply(x1) + 4.0*function.apply((x1+x2 )/ 2.0) + function.apply(x2));
    }
}
