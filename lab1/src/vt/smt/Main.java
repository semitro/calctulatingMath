package vt.smt;
import vt.smt.MyMath.Matrix;

public class Main {

    public static void main(String[] args) {
        //Matrix m = new Matirx
        Double test[][] = {
                {1.0, 4.0, 2.0, 3.4},
                {2.0, 4.0, 8.0, 1.5},
                {3.5, 8.0, 6.0, 1.0},
                {3.0, 2.6, 6.0, 1.0}
        };

        Matrix m = new Matrix(test);
        System.out.println(m.getX() + " " + m.getY());
        System.out.println(m.toString());
        m.triangulate();
        System.out.println(m);
    }
}
