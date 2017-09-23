package vt.smt.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by semitro on 23.09.17.
 */
public class SLEGUI extends Application{

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(mainPane,800,600);
        scene.getStylesheets().add("css/theme.css");
        mainPane.setCenter(sle);

        matrixSize.valueProperty().addListener( (e,oldValue,newValue)->{
            if(newValue.intValue() != oldValue.intValue())
                 sle.setSize(newValue.intValue()+1, newValue.intValue());
        });

        mainPane.setTop(matrixSize);

        bottomBox.getChildren().addAll(fileButton,solveSLE);
        mainPane.setBottom(bottomBox);
        solveSLE.setOnMouseClicked(e->{

        });
        fileButton.setOnMouseClicked(e->{
            File file = fileChooser.showOpenDialog(primaryStage);
            try {
                vt.smt.MyMath.Matrix matrix = new vt.smt.MyMath.Matrix(vt.smt.MyMath.Util.loadMatrixFromFile(file));
            }catch (IOException exeption){
                exeption.printStackTrace();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        super.init();
        matrixSize.setMin(2);
        matrixSize.setMax(12);
        matrixSize.adjustValue(4);
        matrixSize.setBlockIncrement(1);
        matrixSize.setShowTickMarks(true);
        matrixSize.setShowTickLabels(true);
        matrixSize.setMinorTickCount(1);
        matrixSize.setShowTickLabels(true);
        fileChooser.setInitialDirectory(new File("tests"));
    }

    SLEInput sle = new SLEInput(4,3);
    private BorderPane mainPane = new BorderPane();
    private Slider matrixSize = new Slider();
    private Button solveSLE = new Button("Решить СЛАУ");
    private Button fileButton = new Button("Загрузить из файла");
    private FileChooser fileChooser = new FileChooser();
    private HBox bottomBox = new HBox();
}
