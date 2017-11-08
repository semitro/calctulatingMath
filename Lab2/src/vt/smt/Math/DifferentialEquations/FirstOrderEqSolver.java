package vt.smt.Math.DifferentialEquations;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by semitro on 06.11.17.
 */
public interface FirstOrderEqSolver {
    // Возвращает функцию-решение дифференциального уравнения вида y' = f_x_y
    Function<Number,Number> getAns(BiFunction<Number,Number,Number> f_x_y, double x0, double y0,
                                   double to, double precision);
}
