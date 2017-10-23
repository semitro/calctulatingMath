package vt.smt.GUI.lab2;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Created by semitro on 27.09.17.
 */
public class WaitScene {
    private Scene scene;
    private BorderPane mainPane = new BorderPane();
    public WaitScene(){
        scene = new Scene(mainPane);
        ImageView img = new ImageView("img/wait.jpg");
    }
}
