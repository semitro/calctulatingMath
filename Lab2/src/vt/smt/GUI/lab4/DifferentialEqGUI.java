package vt.smt.GUI.lab4;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vt.smt.DynamicLoad.DynamicFunctionManager;
import vt.smt.GUI.lab2.Plot;
import vt.smt.Math.DifferentialEquations.FirstOrderEqSolver;
import vt.smt.Math.DifferentialEquations.FirstOrderEqSolverImpl;

import java.util.function.BiFunction;



public class DifferentialEqGUI extends BorderPane{
    private Plot plot = new Plot();
    private Label labelInputF = new Label("y'=");
    private TextField functionField = new TextField("x");

    private Label intervalLabel = new Label("f(");
    private TextField argumentCauchy = new TextField("0");
    private TextField valueCauchy = new TextField("1");
    private Label intervalLabelEnd = new Label(") = ");

    private HBox functionInputBox;
    private VBox inputAndDots;

    private Label sliderLabel = new Label("Оду; задача Коши");

    private final Label prec_label = new Label("Точность: ");
    private final TextField precision = new TextField("0.25");

    private final Label right_label = new Label("До: ");
    private final TextField rightBorder = new TextField("4");

    public DifferentialEqGUI(){
        super();
        argumentCauchy.setPrefWidth(52);
        precision.setPrefWidth(64.);
        valueCauchy.setPrefWidth(argumentCauchy.getPrefWidth());
        functionInputBox = new HBox(labelInputF,functionField,intervalLabel,argumentCauchy,
                                    intervalLabelEnd, valueCauchy, prec_label,precision,right_label,rightBorder);
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
    private final FirstOrderEqSolver eq_solver = new FirstOrderEqSolverImpl();
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
            if(e.getCode().equals(KeyCode.ENTER))
                precision.requestFocus();
        });
        precision.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER))
                rightBorder.requestFocus();
            }
        );
        rightBorder.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER)){
                    double x_0, y_0, to, prec;
                    try {
                         x_0 = Double.parseDouble(argumentCauchy.getText());
                         y_0 = Double.parseDouble(valueCauchy.getText());
                         prec = Double.parseDouble(precision.getText());
                         to  = Double.parseDouble(rightBorder.getText());
                         if(to <= x_0){
                             Alert alert = new Alert(Alert.AlertType.WARNING);
                             alert.setTitle("Границы");
                             alert.setContentText("Правая граница должна быть больше\n аргумента начального условия");
                             alert.show();
                         } else {
                             functionManager.setCode(functionField.getText(),2);

                             BiFunction<Number,Number,Number> f_x_y = functionManager.getBiFunction();
                             plot.clear();
                             plot.setFunction(eq_solver.getAns(f_x_y, x_0, y_0, to, prec)
                                     , x_0, to, "Интегральная кривая");
                         }
                    }catch (NumberFormatException numeirc){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Числа");
                        alert.setContentText("Убедитесь, всё введено верно");
                        alert.show();

                    }



            }
        });
    }
}
