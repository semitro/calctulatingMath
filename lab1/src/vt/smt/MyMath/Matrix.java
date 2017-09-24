package vt.smt.MyMath;


import com.sun.istack.internal.Nullable;
import javafx.util.Pair;
import vt.smt.GUI.Observer.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
public class Matrix implements vt.smt.GUI.Observer.Observable {
    // First - stroke
    private List<vt.smt.GUI.Observer.Observer> observers = new LinkedList<>();

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void noticeAll(MatrixEvent event, int delay){
        observers.forEach(e->e.notice(event));
        try {
            Thread.currentThread().sleep(delay+40);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
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
    public Pair<Integer, Integer> findMaxAbs(@Nullable Collection<Integer> withoutRow, @Nullable Collection<Integer> withoutColumn){
        Double currentMax = Math.abs(m[0][0]);
        int x = 0, y = 0;

        for(int i = 0; i < getY(); i++) {
            if(withoutRow != null && withoutRow.contains(i))
                continue;
            for (int j = 0; j < getX(); j++) {
                if(withoutColumn != null && withoutColumn.contains(j))
                    continue;
                noticeAll(new ChooseCeil(new Pair<Integer, Integer>(i,j),"justSelectedElement"),40);

                if (Math.abs(m[i][j]) > currentMax) {
                    noticeAll(new ChooseCeil(new Pair<Integer, Integer>(i,j),"newMaximumElement"),50);
                    x = i;
                    y = j;
                    currentMax = Math.abs(m[i][j]);
                }
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

        for(int loop = 0; loop < getY();loop++) {
            final Pair<Integer, Integer> mainPos = findMaxAbs(strokesToSkip,columnsToSkip);
            noticeAll(new ChooseCeil(mainPos, new String("mainElementCeil")),40);
            strokesToSkip.add(mainPos.getKey());

            Double main = get(mainPos.getKey(), mainPos.getValue());

            for (int i = 0; i < getY(); i++) {
                if (strokesToSkip.contains(i)) // We don't need to mul the main stroke by itself
                    continue;

                // Coefficient to mul each column by this
                Double factor = -get(i, mainPos.getValue()) / main;
                for (int j = 0; j < getX(); j++) {
                    if (columnsToSkip.contains(j))
                        continue;
                    // Прибавляем почленно главную строку, умножив её на коэффициент
                    m[i][j] += get(mainPos.getKey(), j) * factor;
                    if(Math.abs(m[i][j]) < 7.440892098500626E-16 )
                        m[i][j] = 0.0;
                    noticeAll(new ChangeCeil(new Pair<Integer, Integer>(i,j),Double.toString(m[i][j])),50);
                }

            }
            // At first we need to close the column to skip it later
            columnsToSkip.add(mainPos.getValue());
        }

        System.out.println("Before mormalize: ");
        vt.smt.MyMath.Util.printMatrix(this.get());
        normalize();
        isTriangle = true;
    }
    // We don't want to preform the triangle() function again
    boolean isTriangle = false;


    public void normalize(){
        // Can we reduce the cycle steps?
        for (int i = 0; i < getY(); i++)try {
             swapStrokes(findStrokeWithZeros(getX() - i - 1), getX() - i - 1);
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < getX()-1; i++)try {
            int currentColumn = findColumnWithoutZeros(getY() - i);
            if(currentColumn != m[0].length - 1)
             swapColumns(currentColumn, i);
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

        System.out.println(this);

    }

    public void swapStrokes(int i, int j){
        Double tmp;
        for (int k = 0; k < getX(); k++){
            tmp = m[i][k];
            m[i][k] = m[j][k];
            m[j][k] = tmp;

            noticeAll(new ChangeCeil(
                    new Pair<Integer, Integer>(i,k),Double.toString(m[i][k])),
                    40
            );
            noticeAll(new ChangeCeil(
                            new Pair<Integer, Integer>(j,k),Double.toString(m[j][k])),
                    40
            );
        }

        noticeAll(new SwapLines(i,j,true),200);
    }

    public void swapColumns(int i, int j) {
        Double tmp;
        for (int k = 0; k < getY(); k++) {
            tmp = m[k][i];
            m[k][i] = m[k][j];
            m[k][j] = tmp;
            noticeAll(new ChangeCeil(
                            new Pair<Integer, Integer>(i,k),Double.toString(m[i][k])),
                    10
            );
            noticeAll(new ChangeCeil(
                            new Pair<Integer, Integer>(j,k),Double.toString(m[j][k])),
                    10
            );
        }
        noticeAll(new SwapLines(i, j, false), 200);
    }

    // Which stroke contains zerosNumber zeros?
    private int findStrokeWithZeros(int zerosNumber) throws IndexOutOfBoundsException{
        int currentZero = -1;

        for(int i = 0; i < getY(); i++) {

            currentZero = 0 ;

            for (int j = 0; j < getX(); j++)
                if(get(i,j) == 0.0)
                    currentZero++;

            if(currentZero == zerosNumber)
                return i;
        }
        System.out.println(this);
        throw new IndexOutOfBoundsException("There's no a stroke with " + zerosNumber + " zeros!");
    }

    // To insert columns in the right position
    // Returns an index of a column contains %count non-zero numbers
    private int findColumnWithoutZeros(int count) throws IndexOutOfBoundsException{
        int currentCount = -1;

        for(int i = 0; i < getX(); i++) {

            currentCount = 0;
            for (int j = 0; j < getY(); j++)
                if(get(j,i) != 0.0)
                    currentCount++;

            if(currentCount == count)
                return i;
        }
        throw new IndexOutOfBoundsException("There's no a column without " + currentCount + " zeros!");
    }
    // It's calculated by diagonal multiplication
    public Double det() throws IllegalArgumentException{
        if(!isSquare())
            throw new IllegalArgumentException("A matrix must be square to calculate the det of it");
        triangulate();
        Double det = 1.0;
        // Be careful with the borders
        for(int i = getY()-1; i >= 0;i--)
            det *= m[i][getY()-1-i];


        return det;
    }

    public Double[] getRow(int n){
        return this.m[n];
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
