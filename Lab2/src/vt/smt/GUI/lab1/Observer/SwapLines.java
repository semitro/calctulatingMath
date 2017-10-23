package vt.smt.GUI.lab1.Observer;

/**
 * Позволяет анимировать обмен строк или столбцов
 * Если rows == true, меняет строки, иначе - столбцы
 */
public class SwapLines implements MatrixEvent {
    private int i, j;
    private boolean rows;

    public SwapLines(int i, int j, boolean rows) {
        this.i = i;
        this.j = j;
        this.rows = rows;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public boolean isAboutRows() {
        return rows;
    }

    public void setRows(boolean rows) {
        this.rows = rows;
    }
}
