package vt.smt.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        Plot plot = new Plot();
        plot.setFunction((x)->{return Math.sin(x.doubleValue());},1.0,20.0);
        mainPane.setCenter(plot);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }
    private BorderPane mainPane = new BorderPane();
}
