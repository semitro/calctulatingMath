package vt.smt.GUI;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
    public EquationStroke(){
        super();
    }

    // Добавить поля ввода
    public void addRow(int number){
        while(0 != number) {
            number--;
            TextField prototypeField = new TextField("x");
            prototypeField.setPrefWidth(55);
            prototypeField.setId("inputValueCeil");
            Text prototypeLabel = new Text();
            prototypeLabel.setId("inputValueCeil");
            this.getChildren().add(prototypeField);
            this.getChildren().add(prototypeLabel);
        }


    }
    // We need to do this because there's a '=' sign before the last input element
    private void refreshLabels(){
        for(int i = 1, k = 1; i < getChildren().size();i+=2, k++){
            if(i == getChildren().size()-1)
                ((Text)getChildren().get(i)).setText(" ");
            else
            if(i == getChildren().size()-3)
                ((Text)getChildren().get(i)).setText("X "+ k + "  =  ");
            else
                ((Text)getChildren().get(i)).setText("X" + k + " + " );
        }
    }
    public Double[] getValues(){
        Double ans[] = new Double[getChildren().size()/2];
        for(int i = 0, k = 0 ; i < getChildren().size();i+=2, k++){
            ans[k] = Double.parseDouble( ((TextField)getChildren().get(i)).getText() );
        }
        return ans;
    }
    public void setValues(Double[] values){
        setLength(values.length);
        for(int i = 0, k = 0; i < getChildren().size();i+=2, k++)
            ((TextField)this.getChildren().get(i)).setText(Double.toString(values[k]));
    }
    public void setLength(int number){
        // Умножаем или делим на два, потому что после каждго инпута есть лейбл
        if(number > getNumber()/2)
            addRow(number - getNumber() / 2);
        else
            this.getChildren().remove(number*2,getChildren().size());

        refreshLabels();
    }
    public int getNumber(){
        return getChildren().size();
    }
    public EquationStroke(EquationStroke prototype){
        //Ссылки??
        this.addRow(prototype.getNumber() - this.getNumber());
    }
}
