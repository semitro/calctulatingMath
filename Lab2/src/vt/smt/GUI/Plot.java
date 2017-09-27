package vt.smt.GUI;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Plot extends HBox {
    public Plot(){
        super();
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        lineChart = new LineChart(xAxis,yAxis);
        this.getChildren().add(lineChart);
    }
    public void setFunction(Function<Number,Number> function, Double from, Double to){
        List<XYChart.Data> d1 = new LinkedList<>();
        for(Double i = from; i<to; i+= (to-from)/55.0)
            d1.add(new XYChart.Data<Double,Double>(new Double(i), (Double)function.apply(i)));
        ObservableList<XYChart.Data> data = FXCollections.observableList(d1);
        XYChart.Series series = new XYChart.Series(data);
        lineChart.getData().add(series);
    }
    public void setTitle(String title){
        lineChart.setTitle(title);
    }
    public void clear(){
        lineChart.getData().clear();
    }
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart lineChart;
}
