package vt.smt.GUI.lab4;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import vt.smt.DynamicLoad.DynamicFunctionManager;
import vt.smt.GUI.lab2.Plot;
import vt.smt.Math.Approximation.Approximation;

import java.util.LinkedList;
import java.util.function.Function;



public class DifferentialEqGUI extends BorderPane{
    private Plot plot = new Plot();
    private Label labelInputF = new Label("y'=");
    private TextField functionField = new TextField("x");

    private Label intervalLabel = new Label("f(");
    private TextField argumentCauchy = new TextField("0");
    private TextField valueCauchy = new TextField("2");
    private Label intervalLabelEnd = new Label(") = ");

    private HBox functionInputBox;
    private VBox inputAndDots;

    private Label sliderLabel = new Label("Оду; задача Коши");

    private final Label prec_label = new Label("Точность: ");
    private final TextField precision = new TextField("0.25");
    public DifferentialEqGUI(){
        super();
        argumentCauchy.setPrefWidth(52);
        valueCauchy.setPrefWidth(argumentCauchy.getPrefWidth());
        functionInputBox = new HBox(labelInputF,functionField,intervalLabel,argumentCauchy,
                                    intervalLabelEnd, valueCauchy, prec_label,precision);
        precision.setTextFormatter(new TextFormatter<Object>(change -> {
            if(!change.isAdded())
                return change;
            
            if(change.getText().matches("[0-9]|\\.|-|"))
                return change;
            else
                return null;
        }));
        inputAndDots = new VBox(functionInputBox,sliderLabel);
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
        argumentCauchy.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER))
                valueCauchy.requestFocus();
        });
        functionField.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER))
                argumentCauchy.requestFocus();
        });

        valueCauchy.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER)){
                functionManager.setCode(functionField.getText());
                try {

                    Function<Number,Number> userFunction = functionManager.getFunction();

                    Double from, to;
                    from = Math.min(Double.parseDouble(argumentCauchy.getText()),
                            Double.parseDouble(valueCauchy.getText()));
                    to  =  Math.max(Double.parseDouble(argumentCauchy.getText()),
                            Double.parseDouble(valueCauchy.getText()));


                    LinkedList<Pair<Double,Double>> l = new LinkedList<>();
                    for(double i = from; i <= to; i += Math.abs(from-to)/1){
                        l.add(new Pair(new Double(i),userFunction.apply(i)));
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
