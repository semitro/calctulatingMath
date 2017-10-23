package vt.smt.GUI.lab3;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import vt.smt.DynamicLoad.DynamicFunctionManager;
import vt.smt.GUI.lab2.Plot;

import java.util.LinkedList;
import java.util.function.Function;

/**
 * Created by semitro on 23.10.17.
 */
public class ApproximateGUI extends BorderPane {
    private Plot plot = new Plot();
    private Label labelInputF = new Label("Аппроксимируемая функция: ");
    private TextField functionField = new TextField("x");
    private HBox functionInputBox;
    private Slider dotsSlider = new Slider(5,30,5);
    private VBox inputAndDots;
    private Label sliderLabel = new Label("Количество узлов аппроксимации");
    public ApproximateGUI(){
        super();
        functionInputBox = new HBox(labelInputF,functionField);
        inputAndDots = new VBox(functionInputBox,dotsSlider,sliderLabel);
        dotsSlider.setShowTickLabels(true);
        dotsSlider.setShowTickMarks(true);
        initActions();
        this.setCenter(plot);
        this.setBottom(inputAndDots);
        try {
            functionManager = new DynamicFunctionManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private DynamicFunctionManager functionManager;
    private void initActions(){
        functionField.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER)){

                functionManager.setCode(functionField.getText());
                try {
                    Function<Number,Number> f = functionManager.getFunction();
                    plot.setFunction(f,0.01,100.0);
                    LinkedList<Pair<Double,Double>> l = new LinkedList<>();
                    l.add(new Pair<>(0.,-2.));
                    l.add(new Pair<>(1.,-5.));
                    l.add(new Pair<>(2., 0.));
                    l.add(new Pair<>(3.,-4.));
                    vt.smt.Math.Approximation.Approximation.getApproximateFunction(l);
                } catch (ReflectiveOperationException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
}
