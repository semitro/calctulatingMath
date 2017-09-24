package vt.smt.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by semitro on 23.09.17.
 */
class SLEInput extends Pane {

    private VBox vBox = new VBox();
    public  SLEInput(int rows, int columns){
        super();
        vBox.setSpacing(5);
        setSize(rows,columns);
        this.getChildren().add(vBox);
        //this.setStyle("fx-min");
    }

    public void setMatrix(vt.smt.MyMath.Matrix matrix){
        setSize(matrix.getX(),matrix.getY());
        for (int i = 0; i < matrix.getY(); i++)
            ((EquationStroke)vBox.getChildren().get(i)).setValues(matrix.getRow(i));
    }
    // Количество полей для ввода
    public void setSize(int rows, int columns){
        if( columns > vBox.getChildren().size()  )
            while ( vBox.getChildren().size() < columns)
                vBox.getChildren().add(new EquationStroke());
        else
            vBox.getChildren().remove(columns,vBox.getChildren().size());

        vBox.getChildren().forEach(e->{
            ((EquationStroke)e).setLength(rows);
        });
    }


    public int getRows(){
        return vBox.getChildren().size();
    }
    public int getColumns(){
        return ((EquationStroke)vBox.getChildren().get(0)).getNumber();

    }
    Double[][] getMatrix() {
        Double[][] ans = new Double[getRows()][getColumns()];
        for (int i = 0; i < ans.length; i++)
            ans[i] = ((EquationStroke) vBox.getChildren().get(i)).getValues();
        return ans;
    }

}