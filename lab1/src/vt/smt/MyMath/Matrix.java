package vt.smt.MyMath;


import com.sun.istack.internal.Nullable;
import javafx.util.Pair;

import java.util.Collection;
import java.util.TreeSet;

public class Matrix {
    // First - stroke
    public Matrix(Double m[][]){
        int s1 = m.length;
        int s2 = m[0].length;
        this.m = new Double[s1][s2];
        for (int i = 0; i < s1; i++)
            for (int j = 0; j < s2; j++)
                this.m[i][j] = m[i][j];

    }
    public boolean isSquare(){return m.length == m[0].length;}
    public int getX(){return m[0].length;}
    public int getY(){return m.length;}
    public Double get(int y, int x){
        return m[y][x];
    }
    public Double[][] get(){
        return m;
    }
    private Double m[][];

    // Returns the position of the max absolute value
    // It can accepts the set of strokes to skip (we need this in Main-element-method
    public Pair<Integer, Integer> findMaxAbs(@Nullable Collection<Integer> without){
        Double currentMax = Math.abs(m[0][0]);
        int x = 0, y = 0;

        for(int i = 0; i < getY(); i++) {
            if(without != null && without.contains(i))
                continue;
            for (int j = 0; j < getX(); j++)
                if (Math.abs(m[i][j]) > currentMax) {
                    x = i;
                    y = j;
                    currentMax = m[i][j];
                }
        }

        return new Pair<>(x,y);
    }

    // Using Gaussian method with selection of the main element
    public void triangulate(){
        if(isTriangle) // If the matrix is already triangle there's no reason to perform this function
            return;

        // We imitate closing strokes and columns using two Lists remembering which of its we need to skip
        // We use TreeSet Instead of LinkedList or something else because its have high
        Collection<Integer> strokesToSkip = new TreeSet<>();
        Collection<Integer> columnsToSkip = new TreeSet<>();
        Pair<Integer, Integer> mainPos = null;
        for(int loop = 0; loop < getY()-1;loop++) {

            mainPos = findMaxAbs(strokesToSkip);
            strokesToSkip.add(mainPos.getKey());

            Double main = get(mainPos.getKey(), mainPos.getValue());

            for (int i = 0; i < getY(); i++) {
                if (strokesToSkip.contains(i)) // We don't need to mul the main stroke by itself
                    continue;

                // Coefficient to mul each stroke by this
                Double factor = -get(i, mainPos.getValue()) / main;

                for (int j = 0; j < getX(); j++) {
                    if (columnsToSkip.contains(j))
                        continue;

                    m[i][j] += get(mainPos.getKey(), j) * factor;
                }

            }
            // At first we need to close the column to skip it later
            columnsToSkip.add(mainPos.getValue());
        }
        /*
        // Small optimization:
        // We let the matrix to stay not actually triangle: instead of mix strokes
        // (This operation use too many write-requests to slow RAM)
        // We will calculate, imitate, and imagine the matrix like its triangle
        */
        normalize();
        isTriangle = true;
    }
    // We don't want to preform the triangle() function again
    boolean isTriangle = false;

    private void normalize(){
        System.out.println(findStrokeWithZeros(0));
    }

    // Which stroke contains zerosNumber zeros?
    private int findStrokeWithZeros(int zerosNumber) throws IndexOutOfBoundsException{
        int currentZero = -1;

        for(int i = 0; i < getY(); i++) {

            currentZero = 0;
            for (int j = 0; j < getX(); j++)
                if(get(i,j) == 0.0)
                    currentZero++;

            if(currentZero == zerosNumber)
                return i;
        }
        throw new IndexOutOfBoundsException("There's no a stroke with " + zerosNumber + " zeros!");
    }
    public Double det(){
        triangulate();
        return 0.0;
    }
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < getY(); i++) {
            for(int j = 0; j < getX(); j++)
                str.append(m[i][j] + " ");
            str.append('\n');
        }

        return str.toString();
    }
}
