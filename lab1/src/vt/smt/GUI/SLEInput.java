package vt.smt.GUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vt.smt.GUI.Observer.MyEvent;
import vt.smt.GUI.Observer.Observer;
import vt.smt.GUI.Observer.*;
/**
 * Created by semitro on 23.09.17.
 */
class SLEInput extends Pane implements Observer {

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

    // Чтобы отслеживать в гуи выполнение алгоритма
    @Override
    public void notice(MyEvent event) {
        System.out.println(((ChooseCeil) event).getColorID());
        if(event instanceof ChooseCeil)
            getFiledNumber( ((ChooseCeil)event).getPosition().getKey(),  ((ChooseCeil)event).getPosition().getValue() )
                    .setId( ((ChooseCeil)event).getColorID() );

    }

    public TextField getFiledNumber(int i, int j){
        return (TextField)((EquationStroke)vBox.getChildren().get(i)).getFieldNumber(j);
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