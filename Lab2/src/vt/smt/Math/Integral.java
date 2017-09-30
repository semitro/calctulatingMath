package vt.smt.Math;

import java.util.function.Function;

/**
 * Created by semitro on 01.10.17.
 */
public abstract class Integral implements Integrator {
    protected Double rungeRuleCoeff = 15.0;

    @Override
    public Double integrate(Function<Double, Double> function, Double from, Double to, Double precision) {
        // The number of the separations
        Integer steps = 1;
        // We need to do this to use the Runge's rule
        // (This rule is based on a difference between
        // integration with n separations and 2n separation)
        Double previousSquare = getAtomSquare(function,from, to);
        Double currentSquare = integrateSeparation(function, from, to, steps);
        // As the theory require, we're integrating the function until
        // The infelicity is greater than precision that an user set
        while (infelicityRunge(previousSquare,currentSquare) > precision){
            previousSquare = currentSquare;
            steps *= 2;
        }
        return currentSquare;
    }
    // Integrate the function clearly setting the numbers of the separations
    protected Double integrateSeparation(Function<Double, Double> function, Double from, Double to, Integer steps){
        Double square = 0.0; // The undef integral
        Double xLeft = from; // f(x + delta x)
        // Size of one line on the x axis
        Double deltaX = to - from / steps.doubleValue(); // f(x + delta x)

        for (int i =0; i<steps;i++) {
            square += getAtomSquare(function, xLeft, xLeft + deltaX);
            xLeft += deltaX;
        }
    }
    protected abstract Double getAtomSquare(Function<Double,Double> function, Double x1, Double x2);
    // Оценка погрешности правилом Рунге
    protected Double infelicityRunge(Double I1,Double I2){
        return Math.abs(I1 - I2)/rungeRuleCoeff;
    }

}
