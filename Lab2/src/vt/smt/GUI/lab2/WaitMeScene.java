package vt.smt.GUI.lab2;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class WaitMeScene {
    private Scene scene;
    private BorderPane pane = new BorderPane();
    public WaitMeScene(){
        pane.setCenter(new ImageView(new Image(getClass().getResourceAsStream("/img/wait.jpg"))));
        scene = new Scene(pane);
    }
    public Scene getScene(){
        return scene;
    }
    public Pane getPane()   { return  pane; }
}
