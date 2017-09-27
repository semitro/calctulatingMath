package vt.smt.GUI;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Created by semitro on 27.09.17.
 */
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
}
