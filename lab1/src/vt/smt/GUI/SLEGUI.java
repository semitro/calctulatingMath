package vt.smt.GUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by semitro on 23.09.17.
 */
public class SLEGUI extends Application{

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(mainPane,400,300);
        scene.getStylesheets().add("css/theme.css");
        mainPane.setCenter(sle);
        matrixSize.setMin(2);
        matrixSize.setMax(20);
        matrixSize.adjustValue(4);
        matrixSize.setBlockIncrement(1);
        matrixSize.setShowTickMarks(true);
        matrixSize.setShowTickLabels(true);
        matrixSize.setMinorTickCount(1);
        matrixSize.setShowTickLabels(true);
        matrixSize.valueProperty().addListener( (e,oldValue,newValue)->{
            if(newValue.intValue() != oldValue.intValue())
                 sle.setSize(newValue.intValue()+1, newValue.intValue());
        });

        mainPane.setTop(matrixSize);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    SLEInput sle = new SLEInput(0,0);
    private BorderPane mainPane = new BorderPane();
    private Slider matrixSize = new Slider();

}
