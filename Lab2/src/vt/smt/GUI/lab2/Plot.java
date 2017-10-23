package vt.smt.GUI.lab2;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Plot extends HBox {
    public Plot(){
        super();
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        lineChart = new AreaChart(xAxis,yAxis);
        this.getChildren().add(lineChart);
        lineChart.setCreateSymbols(false);
        lineChart.setManaged(true);
        lineChart.setId("Определённый интеграл");
    }

    private XYChart.Series integrateSeries;
    public void setFunction(Function<Number,Number> function, Double from, Double to){
        List<XYChart.Data> d1 = new LinkedList<>();
        // 72 точки
        for(Double i = Math.min(from,to); i<Math.max(to,from); i+= Math.abs(to-from)/72.0)
            d1.add(new XYChart.Data<Double,Double>(new Double(i), (Double)function.apply(i)));
        ObservableList<XYChart.Data> data = FXCollections.observableList(d1);
        integrateSeries = new XYChart.Series(data);
        integrateSeries.setName("Интегрируемая функция");
        lineChart.getData().add(integrateSeries);
        integrateSeries.getNode().setId("chartAreaWithoutFill");

    }
    public void addFunction(List<Pair<Double,Double>> table){
        List<XYChart.Data> d1 = new LinkedList<>();
        table.forEach(e->d1.add( new XYChart.Data<>(e.getKey(),e.getValue())));
        ObservableList<XYChart.Data> data = FXCollections.observableList(d1);
        lineChart.getData().add(new XYChart.Series(data));
    }
    public void setTitle(String title){
        lineChart.setTitle(title);
    }
    public void setTitleUnderPlot(String title){
        integrateSeries.setName(title);
    }
    public void setClueOnMouseEnterPlot(String helpMessage){
        Tooltip tooltip = new Tooltip(helpMessage);
//        Tooltip.install(lineChart.getClip(), tooltip);
        Tooltip.install(integrateSeries.getNode(),tooltip);
    }
    public void clear(){
        lineChart.getData().clear();
    }
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();

    private AreaChart lineChart;
}
