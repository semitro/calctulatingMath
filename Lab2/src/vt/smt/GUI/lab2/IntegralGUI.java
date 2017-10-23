package vt.smt.GUI.lab2;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vt.smt.DynamicLoad.CodeToFunctionTranslater;
import vt.smt.DynamicLoad.DynamicFunctionManager;
import vt.smt.Math.Integrals.Integral;
import vt.smt.Math.Integrals.SymsonsIntegral;

import java.util.function.Function;

/**
 * Created by semitro on 23.10.17.
 */
public class IntegralGUI extends BorderPane{

    public IntegralGUI(Stage stage) throws Exception{
        super();
        this.primaryStage  = stage;
        primaryStage.setTitle("Определённый интеграл оффлайн");
        this.setCenter(plot);
        this.setBottom(integralInput);
        primaryStage.getIcons().clear();
        primaryStage.getIcons().add( new Image(getClass().getResourceAsStream("/img/icon.png")));
        primaryStage.show();
        init();
    }
    // Сцена ожидания компиляции (жди меня)
    private WaitMeScene waitingScene = new WaitMeScene();
    private Stage primaryStage;

    public void init() throws Exception{

        functionManager = new DynamicFunctionManager();
        functionManager.setCode(integralInput.getFunction());

        integralInput.setOnEnterAction(e->{
            this.setCenter(waitingScene.getPane());
            this.setBottom(null);
            System.out.println("Вывы");
            new Thread(()->{
                Platform.runLater(()->{

                    functionManager.setCode(integralInput.getFunction());
                    try {
                        if(!previous_function.equals(integralInput.getFunction())) {
                            plot.clear();

                            plot.setFunction((Function<Number, Number>) functionManager.getFunction(),
                                    Double.parseDouble(integralInput.getFrom()), Double.parseDouble(integralInput.getTo()),null);
                            previous_function = integralInput.getFunction();
                        }
                        Integral integral = new SymsonsIntegral();

                        plot.setTitleUnderPlot(
                                "∫f(x)dx = " +
                                        Double.toString(
                                                integral.integrate(
                                                        (Function<Double, Double>) functionManager.getFunction(),
                                                        Double.parseDouble(integralInput.getFrom()),
                                                        Double.parseDouble(integralInput.getTo()),
                                                        Double.parseDouble(integralInput.getPrecision()))
                                        )
                        );


                        plot.setClueOnMouseEnterPlot("Количество разбиений: " + integral.getLastIntegrateSteps().toString()
                                + "\nПогрешность: " + integral.getLastIntegrateInfelicity());
                    }catch (ReflectiveOperationException exception){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(exception.getMessage());
                        alert.show();
                    }
                    this.setCenter(plot);
                    this.setBottom(integralInput);
                });
            }).start();
        });

    }

    // we don't wont to recompile the same code
    private String previous_function = new String();
    private Plot plot = new Plot();
    // То, что превращает введённый код в исполняемыйы
    private CodeToFunctionTranslater functionManager;
    //private BorderPane mainPane = new BorderPane();
    private IntegralInputGUI integralInput = new IntegralInputGUI("x");

}
