package vt.smt.GUI.lab1;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vt.smt.Math.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by semitro on 23.09.17.
 */
public class SLEGUI extends Scene{


    public SLEGUI(Stage primaryStage) throws Exception {
        super(new BorderPane());
       // Scene scene = new Scene(mainPane,820,272);
        this.setRoot(mainPane);
        init();
        primaryStage.setTitle("Решение слау методом Гаусса оффлайн");
        this.getStylesheets().add("/css/theme.css");
        HBox spaceSLEGUI = new HBox(40);
        spaceSLEGUI.getChildren().add(sleGUI);
        spaceSLEGUI.setTranslateX(40);
        mainPane.setCenter(spaceSLEGUI);
        primaryStage.setOnCloseRequest(e->System.exit(0));
        matrixSize.valueProperty().addListener( (e,oldValue,newValue)->{
            if(newValue.intValue() != oldValue.intValue())
                 sleGUI.setSize(newValue.intValue()+1, newValue.intValue());
        });
        animationSpeedSlider.valueProperty().addListener((e,oldValue,newValue)->{
            m.setDelayAfterotice(newValue.intValue());
        } );
        mainPane.setTop(matrixSize);
        bottomBox.getChildren().addAll(fileButton,solveSLE,fillRandomButton);
        mainPane.setBottom(bottomBox);
        mainPane.setRight(animationSpeedSlider);
        sleGUI.setStageToKeepPopUp(primaryStage);
        solveSLE.setOnMouseClicked(e->onSolveSLEClicked());

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileButton.setOnMouseClicked(e->{
            File file = fileChooser.showOpenDialog(primaryStage);
            try {
                m = new Matrix(vt.smt.Math.Util.loadMatrixFromFile(file));
                sleGUI.setMatrix(m);
            }catch (IOException | NumberFormatException | IndexOutOfBoundsException exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Убедитесь в адекватности файла");
                alert.setTitle("Не");
                alert.setHeaderText("Ну не");
                alert.show();
                exception.printStackTrace();
            }
        });
        fillRandomButton.setOnMouseClicked(e->{
            m = vt.smt.Math.Util.getRandomMatrix();
            sleGUI.setMatrix(m);
        });
        primaryStage.setScene(this);
        primaryStage.show();

    }
    private Matrix m;
    private void onSolveSLEClicked(){
         m = new Matrix(sleGUI.getMatrix());
         Double[][] initalMatrix = sleGUI.getMatrix().clone();
         answerWindow.setMatrix(initalMatrix);
        // Связываем наблюдателя с источником событий
        m.subscribe(sleGUI);
        Platform.runLater(()->{
            Thread t = new Thread(()->{
                vt.smt.Math.Util.printMatrix(m.get());
                System.out.println();
                m.triangulate();
                System.out.println("Решение слау: ");
                System.out.println(Arrays.asList(m.getAnswersSLAU()));

                sleGUI.resetStyles();
                Matrix square = new Matrix(m,m.getY(),m.getX()-1);
                vt.smt.Math.Util.printMatrix(m.get());
                Double det = square.det();
                sleGUI.notice(new vt.smt.GUI.lab1.Observer.PopUpText
                        ("det основной матрицы:\n" + Double.toString(det)));
                System.out.println("Неувязки:");
                System.out.println(Arrays.asList(m.getDiscrepancy(m.getAnswersSLAU())));
                Platform.runLater(()->{
                    answerWindow.setDet(det);
                    answerWindow.setAnswers(m.getAnswersSLAU());
                    answerWindow.show();
                });
            });
            t.setDaemon(true);
            t.start();
        });
    }

    public void init() throws Exception {
        matrixSize.setMin(2);
        matrixSize.setMax(12);
        matrixSize.adjustValue(4);
        matrixSize.setBlockIncrement(1);
        matrixSize.setShowTickMarks(true);
        matrixSize.setShowTickLabels(true);
        matrixSize.setMinorTickCount(1);
        matrixSize.setShowTickLabels(true);
        fileChooser.setInitialDirectory(new File("tests"));
        animationSpeedSlider.setOrientation(Orientation.VERTICAL);
        animationSpeedSlider.setRotate(180);
        animationSpeedSlider.setShowTickLabels(true);
        animationSpeedSlider.setShowTickMarks(true);
    }

    private SLEInput sleGUI = new SLEInput(4,3);
    private BorderPane mainPane = new BorderPane();
    private Slider matrixSize = new Slider();
    private Button fillRandomButton = new Button("Тебе повезёт");
    private Button solveSLE = new Button("Решить СЛАУ");
    private Button fileButton = new Button("Загрузить из файла");
    private FileChooser fileChooser = new FileChooser();
    private HBox bottomBox = new HBox();
    private Slider animationSpeedSlider = new Slider(0,4000,100);
    private AnswerWindow answerWindow = new AnswerWindow();
}
