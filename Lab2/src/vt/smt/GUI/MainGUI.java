package vt.smt.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vt.smt.DynamicLoad.CodeToFunctionTranslater;
import vt.smt.DynamicLoad.DynamicFunctionManager;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        mainPane.setCenter(plot);
        mainPane.setBottom(formulaInput);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        functionManager = new DynamicFunctionManager();
        functionManager.setCode(formulaInput.getText());
        formulaInput.setOnAction(e->{
                new Thread(()->{
                    Platform.runLater(()->{
                        functionManager.setCode(formulaInput.getText());
                        try {
                            plot.clear();
                            plot.setFunction(functionManager.getFunction(),0.0,100.0);
                        }catch (ReflectiveOperationException exception){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(exception.getMessage());
                            alert.show();
                        }
                    });
                    }).start();
            });
    }
    private Plot plot = new Plot();
    // То, что превращает введённый код в исполняемыйы
    private CodeToFunctionTranslater functionManager;
    private BorderPane mainPane = new BorderPane();
    private TextField formulaInput = new TextField("x");
}
