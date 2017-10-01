package vt.smt.Math;

import java.util.function.Function;

/**
 * Created by semitro on 01.10.17.
 */
public abstract class Integral implements Integrator {

    @Override
    public Double integrate(Function<Double, Double> function, Double from, Double to, Double precision) {
        // The number of the separations
        Integer steps = 1;
        // We need to do this to use the Runge's rule
        // (This rule is based on a difference between
        // integration with n separations and 2n separation)
        Double previousSquare = 0.0;
        Double currentSquare = integrateSeparation(function, from, to, steps);
        // As the theory require, we're integrating the function until
        // The infelicity is greater than precision that an user set
        while (infelicityRunge(previousSquare,currentSquare) > precision){
            previousSquare = currentSquare;
            steps *= 2;
            currentSquare = integrateSeparation(function, from, to, steps);
        }
        this.lastIntegrateSteps = steps;
        return currentSquare;
    }
    // Integrate the function clearly setting the numbers of the separations
    public Double integrateSeparation(Function<Double, Double> function, Double from, Double to, Integer steps){
        Double square = 0.0; // The undef integral is a square under the plot
        Double xLeft = from; // Current point to get function value
        Double deltaX = (to - from )/ steps.doubleValue(); // The offset to get the second point
        for (int i = 0; i < steps; i++ ) {
            // The current simple piece's square is getting from an any integration method
            square += getAtomSquare(function, xLeft, xLeft + deltaX);
            xLeft += deltaX;
        }
        return square;
    }
    protected abstract Double getAtomSquare(Function<Double,Double> function, Double x1, Double x2);
    // Оценка погрешности правилом Рунге
    protected Double infelicityRunge(Double I1,Double I2){
        return Math.abs(I1 - I2)/rungeRuleCoeff;
    }
    protected Double rungeRuleCoeff = 15.0;
    // Сколько разбиений было совершено при последнем интегрировании?
    protected Integer lastIntegrateSteps;
    public Integer getLastIntegrateSteps(){return lastIntegrateSteps;}

}
