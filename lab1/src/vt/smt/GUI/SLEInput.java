package vt.smt.GUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by semitro on 23.09.17.
 */
class SLEInput extends Pane {
    TextField f = new TextField("fsf");
    private VBox vBox = new VBox();

    public  SLEInput(int rows, int columns){
        super();
        vBox.setSpacing(5);
        setSize(rows,columns);
        this.getChildren().add(vBox);
        //this.setStyle("fx-min");
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
}