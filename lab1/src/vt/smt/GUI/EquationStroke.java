package vt.smt.GUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Created by semitro on 23.09.17.
 */
// Cтрока для СЛАУ
public class EquationStroke extends HBox {
    public EquationStroke(int number){
        super();
        this.setSpacing(5);
        addRow(number);
    }


    // Добавить поля ввода
    public void addRow(int number){
        while(0 != number--) {
            TextField prototypeField = new TextField("x");
            prototypeField.setPrefWidth(55);
            prototypeField.setId("inputValueCeil");
            //prototypeField.setStyle("color: green;");

            this.getChildren().add(prototypeField);
        }
    }
    public void setLength(int number){

        if(number > getNumber())
            addRow(number - getNumber());
        else
            this.getChildren().remove(number,getChildren().size());

    }
    public int getNumber(){
        return getChildren().size();
    }
    public EquationStroke(EquationStroke prototype){
        //Ссылки??
        this.addRow(prototype.getNumber() - this.getNumber());
    }
}
