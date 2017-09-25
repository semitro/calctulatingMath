package vt.smt.MyMath;


import com.sun.istack.internal.Nullable;
import javafx.util.Pair;
import vt.smt.GUI.Observer.*;
import vt.smt.GUI.Observer.Observer;

import java.util.*;

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
            Thread.currentThread().sleep(delay+5);
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
    public Matrix(Matrix m, int size_y, int size_x){
        this.m = new Double[size_y][size_x];
        Double[][] inital = m.get();
        for(int i = 0; i < size_y;i++)
            this.m[i] = Arrays.copyOf(inital[i],size_x);
        this.isTriangle = m.areYouTriangle();
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
    // We don't want to preform the triangle() function again
    private boolean isTriangle = false;

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
    public int finMaxAbsInColumn(int column, @Nullable Collection<Integer> withoutRow){
        Double max = 0.0;
        int position = 0;
        for (int i = 0; i < getY();i++){
            if(withoutRow.contains(i))
                continue;
            if(Math.abs(m[i][column]) > max) {
                max = Math.abs(m[i][column]);
                position = i;
            }

        }
        return position;
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
            //final Pair<Integer, Integer> mainPos = findMaxAbs(strokesToSkip,columnsToSkip);
            int mainPositionY = finMaxAbsInColumn(loop,strokesToSkip);
           // noticeAll(new ChooseCeil(mainPos, "mainElementCeil"),40);
            noticeAll(new ChooseCeil(new Pair<Integer, Integer>(mainPositionY,loop), "mainElementCeil"),400);           // strokesToSkip.add(mainPos.getKey());
            strokesToSkip.add(mainPositionY);

           // Double main = get(mainPos.getKey(), mainPos.getValue());
            Double main = m[mainPositionY][loop];
            for (int i = 0; i < getY(); i++) {
                if (strokesToSkip.contains(i)) // We don't need to mul the main stroke by itself
                    continue;

                // Coefficient to mul each column by this
//                Double factor = -get(i, mainPos.getValue()) / main;
                Double factor = -get(i, loop) / main;
                for (int j = 0; j < getX(); j++) {
//                    if (columnsToSkip.contains(j))
//                        continue;
                    // Прибавляем почленно главную строку, умножив её на коэффициент
                    m[i][j] += get(mainPositionY, j) * factor;
                    if(Math.abs(m[i][j]) < 10.440892098500626E-12 )
                        m[i][j] = 0.0;
                    noticeAll(new ChangeCeil(new Pair<Integer, Integer>(i,j),Double.toString(m[i][j])),50);
                }

            }
            // we need to close the column to skip it later
            //columnsToSkip.add(mainPos.getValue());
        }

        vt.smt.MyMath.Util.printMatrix(this.get());
        normalize();
        isTriangle = true;
    }


    public boolean areYouTriangle(){
        return isTriangle;
    }
    public void normalize(){
        // Обязательно есть колонки с количеством нулей от n до n-1
//        int k = 0;
//        for(int i = getY()-1; i >= 0; i--)try{
//            swapColumns(
//                    findColumnWithZeros(i),
//                    k++
//            );
//        }catch (IndexOutOfBoundsException e){
//            System.out.println(e.getMessage());
//        }

        int k = 0;
        for(int i = getX(); i >= 0;i--)try{
            swapStrokes(
                    findStrokeWithoutZeros(i),
                    k++
            );
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
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
                            new Pair<Integer, Integer>(k,i),Double.toString(m[k][i])),
                    10
            );
            noticeAll(new ChangeCeil(
                            new Pair<Integer, Integer>(k,j),Double.toString(m[k][j])),
                    10
            );
        }
        noticeAll(new SwapLines(i, j, false), 200);
    }

    private int findStrokeWithoutZeros(int count) throws IndexOutOfBoundsException{
        int currentZero = 0;

        for(int i = 0; i < getY() ; i++) {
            currentZero = 0 ;
            for (int j = 0; j < getX()-1; j++)
                if(get(i,j) != 0.0)
                    currentZero++;

            if(currentZero == count)
                return i;
        }

        throw new IndexOutOfBoundsException("There's no a stroke with " + count + " zeros!");
    }

    private int findColumnWithZeros(int count) throws IndexOutOfBoundsException{
        int currentZero = 0;
        // Минус один, потому что мы не имеем права переставлять столбец ответов
        for(int i = 0; i < getX()-1 ; i++) {
            currentZero = 0 ;
            for (int j = 0; j < getY(); j++)
                if(get(j,i) == 0.0)
                    currentZero++;

            if(currentZero == count)
                return i;
        }
        throw new IndexOutOfBoundsException("There's no a column with " + count + " zeros!");
    }

    // It's calculated by diagonal multiplication
    public Double det() throws IllegalArgumentException{
        if(!isSquare())
            throw new IllegalArgumentException("A matrix must be square to calculate the det of it");
        triangulate();
        Double det = 1.0;
        // Be careful with the borders
        for(int i = 0; i < getX();i++)
            det *= m[i][i];

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

    // Если берём расширенную матрицу
    public Double[] getAnswersSLAU(){
        Double xxs[] = new Double[getY()-1];
        int currentX = getY()-1;
        Double factor = 1.0;
        // Допилить на свежую голову!
//        for(int i = getY()-1; i >= 0; i--)
//            for(int mulJ = getX()-1; mulJ >= xxs.length - i;mulJ--)
//
//            xxs[i] = factor/get()

        return xxs;
    }
}
