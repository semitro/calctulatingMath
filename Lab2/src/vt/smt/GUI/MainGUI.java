package vt.smt.GUI;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vt.smt.GUI.lab1.SLEGUI;
import vt.smt.GUI.lab2.IntegralGUI;
import vt.smt.GUI.lab3.ApproximateGUI;
import vt.smt.GUI.lab4.DifferentialEqGUI;
/**
 * The main entry point
**/
public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage  = primaryStage;

        final BorderPane manPain = new BorderPane();
        final Scene sc = new Scene(manPain);

        sc.getStylesheets().add("/css/theme.css");
        integralGUI = new IntegralGUI(primaryStage);
        sleGUI      = new SLEGUI(primaryStage);
        approximateGUI = new ApproximateGUI();
        differentialGUI = new DifferentialEqGUI();

        primaryStage.setScene(sc);



        final MenuItem menuInt = new MenuItem("Интегировать");
        menuInt.setOnAction(e-> manPain.setCenter(integralGUI));

        final MenuItem menuMatrix = new MenuItem("Решать слау");
        menuMatrix.setOnAction(e->manPain.setCenter(sleGUI));

        final MenuItem menuApprox = new MenuItem("Аппроксимировать");
        menuApprox.setOnAction(e->manPain.setCenter(approximateGUI));

        final MenuItem menuDifferential = new MenuItem("Решать ОДУ 1-го порядка");
        menuDifferential.setOnAction(e->manPain.setCenter(differentialGUI));

        final Menu menu = new Menu("Что делать?");
        menu.getItems().addAll(menuInt,menuMatrix,menuApprox,menuDifferential);


        menuMatrix.setOnAction(e->manPain.setCenter(sleGUI));
        menuBar = new MenuBar(menu);
        menuBar.setUseSystemMenuBar(true);

        manPain.setTop(menuBar);
        manPain.setCenter(differentialGUI);
        primaryStage.getIcons().add( new Image(getClass().getResourceAsStream("/img/icon.png")));

        primaryStage.setTitle("Вычмат без смс");
        primaryStage.show();

    }
    private MenuBar menuBar;
    private Node integralGUI;
    private Node sleGUI, approximateGUI, differentialGUI ;
    // Главная сцена с графиком

    private Stage primaryStage;

}
