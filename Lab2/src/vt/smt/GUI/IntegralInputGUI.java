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
        this.setId("bottomMenu");
        formulaInput  = new TextField(defaultString);
        fromInput = new TextField("0");
        toInput = new TextField("10");


        fromInput.setId("borderLabel");
        toInput.setId("borderLabel");
        integralLabel.setId("integralLabel");
        formulaInput.setId("formulaInput");
        dxLabel.setId("dxLabel");
        precisionLabel.setId("precisionLabel");
        precisionBox.setId("precisionBox");
        precisionInput.setId("precisionInput");
        integralBorderBox.getChildren().addAll(toInput,integralLabel,fromInput);
        integralAndDx.getChildren().addAll(formulaInput,dxLabel);

        this.getChildren().addAll(integralBorderBox,new VBox(integralAndDx,precisionBox));

        fromInput.setTranslateY(toInput.getHeight());
        precisionInput.setOnAction(e->fromInput.requestFocus());
        fromInput.setOnAction(e->toInput.requestFocus());
        toInput.setOnAction(e->formulaInput.requestFocus());

    }

    public String getPrecision(){return precisionInput.getText();}
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
    private Label precisionLabel = new Label("Точность:");
    private TextField precisionInput = new TextField("0.25");
    private HBox precisionBox = new HBox(precisionLabel,precisionInput);

}
