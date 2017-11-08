package vt.smt.Math.Integrals;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by semitro on 01.10.17.
 */
public abstract class Integral implements Integrator {

    @Override
    public Double integrate(Function<? super Number,? extends Number> function, Double from, Double to, Double precision) {
        // Erase information about a previous primitive
        this.primitiveValues.clear();
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
        this.lastIntegrateInfelicity = infelicityRunge(previousSquare,currentSquare);
        this.lastIntegrateSteps = steps;
        return currentSquare;
    }
    // Integrate the function clearly setting the numbers of the separations
    public Double integrateSeparation(Function<? super Number, ? extends Number> function, Double from, Double to, Integer steps){
        Double square = 0.0; // The undef integral is a square under the plot
        Double xLeft = from; // Current point to get function value
        Double deltaX = (to - from )/ steps.doubleValue(); // The offset to get the second point
        for (int i = 0; i < steps; i++ ) {
            // The current simple piece's square is getting from an any integration method
            square += getAtomSquare(function, xLeft, xLeft + deltaX);
            // The primitive is the square on the every single step
            primitiveValues.add(new Pair<>(xLeft+deltaX, square));
            xLeft += deltaX;
            if(i > 64000)
                return square;
        }
        return square;
    }
    protected abstract Double getAtomSquare(Function<? super  Number,? extends Number> function, Double x1, Double x2);
    // Оценка погрешности правилом Рунге
    protected Double infelicityRunge(Double I1,Double I2){
        return Math.abs(I1 - I2)/rungeRuleCoeff;
    }
    protected Double rungeRuleCoeff = 15.0;
    // Сколько разбиений было совершено при последнем интегрировании?
    protected Integer lastIntegrateSteps;
    // Погрешность последнего измерения
    protected Double  lastIntegrateInfelicity;
    public Double getLastIntegrateInfelicity(){return lastIntegrateInfelicity;}
    public Integer getLastIntegrateSteps(){return lastIntegrateSteps;}
    // Таблица значений первообрзаной
    protected List<Pair<Double,Double>> primitiveValues = new LinkedList<>();
    public List<Pair<Double, Double>> getPrimitiveValues() {
        return primitiveValues;
    }
}
