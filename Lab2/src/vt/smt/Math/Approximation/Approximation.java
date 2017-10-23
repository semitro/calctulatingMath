package vt.smt.Math.Approximation;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.function.Function;

/**
 * Created by semitro on 23.10.17.
 */
public class Approximation {
    public static Function<Number,Number> getApproximateFunction(LinkedList<Pair<Double,Double>> table){
        int n = table.size();
        Double[][] dif = new Double[n][n];
        for(int i = 0; i < n; i++){
            dif[i][0] = table.get(i).getValue();
        }
        // Вычисляем конечные разности
        for(int j = 1; j < n; j++)
            for(int i = 0; i < n-j;i++){
                dif[i][j] = (dif[i+1][j-1] - dif[i][j-1])/
                            ( -table.get(i).getKey() + table.get(i+j).getKey());
            }
        vt.smt.Math.Util.printMatrix(dif);
        return new Function<Number, Number>() {
            @Override
            public Number apply(Number x) {
                // Многочлен Ньютона
                Double ans = 0.;
                Double currentTerm;
                for(int i = 0; i < n;i++) {
                    currentTerm = dif[0][i];
                    for (int brackets = 0; brackets < i; brackets++) {
                        currentTerm *= (Double)x - table.get(brackets).getKey();
                    }
                    ans += currentTerm;
                }

                return ans;
            }
        };
    }
}
