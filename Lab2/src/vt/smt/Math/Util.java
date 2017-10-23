package vt.smt.Math;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by semitro on 23.09.17.
 */

public class Util {
    public static Double[][] loadMatrixFromFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        List<List<String>> line = new LinkedList<>();

        while (scanner.hasNextLine())
            line.add(
                    Arrays.asList(scanner.nextLine().trim().split(" +"))
            );


        Double numbers[][] = new Double[line.size()][line.get(0).size()];
        int i = 0;
        int j = 0;
        for (List<String> stroke : line) {
            for (String row : stroke) {
                numbers[i][j] = Double.parseDouble(row);
                j++;
            }
            i++;
            j = 0;
        }
        return numbers;
    }
    public static Matrix getRandomMatrix(){
        Random random = new Random(System.currentTimeMillis());
        int size = random.nextInt(7);
        size+=3;
        Double m[][] = new Double[size-1][size];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length ; j++)
                m[i][j] = random.nextInt(25)+random.nextDouble()-15;

        return new Matrix(m);
    }
    public static void printMatrix(Double[][] m){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length ; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }

    }
}
