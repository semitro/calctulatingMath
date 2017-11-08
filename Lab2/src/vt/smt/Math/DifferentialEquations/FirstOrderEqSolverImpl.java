package vt.smt.Math.DifferentialEquations;

import javafx.util.Pair;
import vt.smt.Math.Approximation.Approximation;
import vt.smt.Math.Integrals.Integrator;
import vt.smt.Math.Integrals.SymsonsIntegral;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FirstOrderEqSolverImpl implements FirstOrderEqSolver {

    public FirstOrderEqSolverImpl(){

    }
    private Integrator integrator = new SymsonsIntegral();
    //private double h; // шаг
    @Override
    public Function<Number, Number> getAns(BiFunction<Number, Number, Number> f_x_y, double x0, double y0,
                                           double to, double precision) {
        //h = (to - x0) / 4.0; // Пусть сначала - 4 шага
        Function<Number, Number> ans = null;
        boolean repeat_flag = true;
        double h = 2.;
        while (repeat_flag) {
            h *= 2.;
            List<Pair<Double, Double>> predictor = (LinkedList) RoungeCo2(f_x_y, x0, y0, Math.abs(to - x0) / h, to);
            double nextValue;
            //corrector:
            for (int i = 3; i < predictor.size(); i++) {
                System.out.println(i);
//            nextValue = predictor.get(i+1).getValue();
                Function<Number, Number> approx = Approximation.getApproximateFunction(
                        new LinkedList<>(predictor.subList(0, i)));

                nextValue = predictor.get(i - 1).getValue() + integrator.integrate(
                        (x) -> {
                            return f_x_y.apply(x, approx.apply(x));
                        }, predictor.get(i - 1).getKey(), predictor.get(i).getKey(),
                        precision);
                //          do{
                if (predictor.get(i).getValue() - nextValue <= precision || h > 1025)
                    repeat_flag = false;

                predictor.set(i, new Pair<>(predictor.get(i).getKey(), nextValue));

//            }while ((nextValue - predictor.get(i+1).getValue()) > precision);

            }
            ans = Approximation.getApproximateFunction((LinkedList) predictor);
        }
        return ans;
    }
    // Метод Рунге-Кутта
    private List<Pair<Double,Double>> RoungeCo2(BiFunction<Number, Number, Number> f_x_y,
                                                double x0, double y0, double h, double to){
        List< Pair<Double,Double> > solution = new LinkedList<>();
        solution.add(new Pair<>(x0,y0));
        double y_prev = y0;
        double x_prev = x0;

        for(double xi = x0+h; xi <= to; xi += h){
            double y_i = y_prev +
                    h*f_x_y.apply(x_prev + h/2.,
                            y_prev + h*f_x_y.apply(x_prev,y_prev).doubleValue()/2.).doubleValue();
            solution.add(new Pair<>(xi,y_i));
            x_prev = xi;
            y_prev = y_i;

        }
        return solution;
    }
}
