package vt.smt.GUI;

import com.sun.istack.internal.Nullable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Created by semitro on 01.10.17.
 */
public class IntegralInputGUI extends HBox {

    public IntegralInputGUI(@Nullable String defaultString){
        super();
        formulaInput  = new TextField(defaultString);
        fromInput = new TextField("10");
        toInput = new TextField("0");


        fromInput.setId("borderLabel");
        toInput.setId("borderLabel");
        integralLabel.setId("integralLabel");
        formulaInput.setId("formulaInput");
        dxLabel.setId("dxLabel");

        integralBorderBox.getChildren().addAll(toInput,integralLabel,fromInput);
        integralAndDx.getChildren().addAll(formulaInput,dxLabel);
        this.getChildren().addAll(integralBorderBox,integralAndDx);

        fromInput.setTranslateY(toInput.getHeight());
        fromInput.setOnAction(e->toInput.requestFocus());
        toInput.setOnAction(e->formulaInput.requestFocus());

    }
    public String getFrom(){
        return fromInput.getText();
    }
    public String getTo(){
        return toInput.getText();
    }
    public String getFunction(){
        return formulaInput.getText();
    }
    public void setOnEnterAction(EventHandler<ActionEvent> e){
        formulaInput.setOnAction(e);
    }
    private TextField formulaInput;
    private Pane integralBorderBox = new VBox();
    private TextField fromInput, toInput;
    private Label integralLabel = new Label("∫");
    private Label dxLabel = new Label("dx");
    private Pane integralAndDx = new HBox();

}
