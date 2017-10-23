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
import vt.smt.Math.Approximation.*;

import java.util.LinkedList;
import java.util.function.Function;

/**
 * Created by semitro on 23.10.17.
 */
public class ApproximateGUI extends BorderPane {
    private Plot plot = new Plot();
    private Label labelInputF = new Label("f(x)=");
    private TextField functionField = new TextField("x");

    private Label intervalLabel = new Label("Интервал: [");
    private TextField leftInterval = new TextField("1");
    private TextField rightInterval = new TextField("2");
    private Label intervalLabelEnd = new Label("];");

    private HBox functionInputBox;
    private Slider dotsSlider = new Slider(5,30,5);
    private VBox inputAndDots;

    private Label sliderLabel = new Label("Количество узлов аппроксимации");

    public ApproximateGUI(){
        super();
        leftInterval.setPrefWidth(52);
        rightInterval.setPrefWidth(leftInterval.getPrefWidth());
        functionInputBox = new HBox(labelInputF,functionField,intervalLabel,leftInterval,rightInterval,intervalLabelEnd);

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

                    Function<Number,Number> userFunction = functionManager.getFunction();

                    Double from, to;
                    from = Double.parseDouble(leftInterval.getText());
                    to = Double.parseDouble(rightInterval.getText());


                    LinkedList<Pair<Double,Double>> l = new LinkedList<>();
                    for(double i = from; i <= to; i += Math.abs(from-to)/((int)dotsSlider.getValue()-1)){
                        l.add(new Pair(new Double(i),userFunction.apply(i)));
                        System.out.println(i);
                    }

                    plot.clear();
                    plot.setFunction(userFunction,from,to,"f(x)");
                    Function<Number,Number> f = Approximation.getApproximateFunction(l);
                    plot.setFunction(f,from,to,"~f(x)");
                    // Отметки точек интерполяции
                    plot.addFunction(l);

                } catch (ReflectiveOperationException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
}
