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
import vt.smt.GUI.lab3.ApproximateGUI;

/**
 * The main entry point
**/
public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage  = primaryStage;
        primaryStage.setTitle("Определённый интеграл оффлайн");

        BorderPane b = new BorderPane();
        Scene sc = new Scene(b);
        sc.getStylesheets().add("/css/theme.css");
//        integralGUI = new IntegralGUI(primaryStage);
//        sleGUI      = new SLEGUI(primaryStage);
        approximateGUI = new ApproximateGUI();
      //  b.setCenter(((IntegralGUI)integralGUI).getMainNode());

        //  sleGUI      = new SLEGUI     (primaryStage);

//        integralGUI.setRoot((Parent)b.getCenter());
//        sleGUI     .setRoot((Parent)b.getCenter());
        primaryStage.setScene(sc);



        final MenuItem menuInt = new MenuItem("Интегировать");
        menuInt.setOnAction(e-> b.setCenter(integralGUI));

        final MenuItem menuMatrix = new MenuItem("Решать слау");
        menuMatrix.setOnAction(e->b.setCenter(sleGUI));

        final MenuItem menuApprox = new MenuItem("Аппроксимировать");
        menuApprox.setOnAction(e->b.setCenter(approximateGUI));
        final Menu menu = new Menu("Что делать?");
        menu.getItems().addAll(menuInt,menuMatrix,menuApprox);

        menuMatrix.setOnAction(e->b.setCenter(sleGUI));
        menuBar = new MenuBar(menu);
        menuBar.setUseSystemMenuBar(true);

        b.setTop(menuBar);
        b.setCenter(approximateGUI);
        primaryStage.getIcons().add( new Image(getClass().getResourceAsStream("/img/icon.png")));
        primaryStage.show();

    }
    private MenuBar menuBar;
    private Node integralGUI;
    private Node sleGUI, approximateGUI;
    // Главная сцена с графиком

    private Stage primaryStage;

}
