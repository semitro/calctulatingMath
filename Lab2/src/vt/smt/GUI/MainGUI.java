package vt.smt.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vt.smt.DynamicLoad.CodeToFunctionTranslater;
import vt.smt.DynamicLoad.DynamicFunctionManager;
import vt.smt.Math.Integral;
import vt.smt.Math.SymsonsIntegral;

import java.util.function.Function;

/**
 * The main entry point
**/
public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage  = primaryStage;
        primaryStage.setTitle("Определённый интеграл оффлайн");
        scene = new Scene(mainPane);
        scene.getStylesheets().add("/css/theme.css");
        primaryStage.setScene(scene);
        mainPane.setCenter(plot);
        mainPane.setBottom(integralInput);
        primaryStage.show();

    }
    // Главная сцена с графиком
    private Scene scene;
    // Сцена ожидания компиляции (жди меня)
    private WaitMeScene waitingScene = new WaitMeScene();
    private Stage primaryStage;
    @Override
    public void init() throws Exception {
        super.init();
        functionManager = new DynamicFunctionManager();
        functionManager.setCode(integralInput.getFunction());
        integralInput.setOnEnterAction(e->{
            primaryStage.setScene(waitingScene.getScene());
            primaryStage.show();
            new Thread(()->{
                    Platform.runLater(()->{
                        functionManager.setCode(integralInput.getFunction());
                        try {
                            plot.clear();
                            plot.setFunction((Function<Number,Number>)functionManager.getFunction(),
                                    Double.parseDouble(integralInput.getFrom()),Double.parseDouble(integralInput.getTo()));
                            Integral integral = new SymsonsIntegral();
                            System.out.println(integral.integrate(
                                    (Function<Double, Double>) functionManager.getFunction(),
                                    Double.parseDouble(integralInput.getFrom()),
                                    Double.parseDouble(integralInput.getTo()), 0.025));
                        }catch (ReflectiveOperationException exception){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(exception.getMessage());
                            alert.show();
                        }
                        primaryStage.setScene(scene);
                    });
                    }).start();
            });
    }
    private Plot plot = new Plot();
    // То, что превращает введённый код в исполняемыйы
    private CodeToFunctionTranslater functionManager;
    private BorderPane mainPane = new BorderPane();
    private IntegralInputGUI integralInput = new IntegralInputGUI("x");
}
