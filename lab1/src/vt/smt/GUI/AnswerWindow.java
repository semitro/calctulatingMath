package vt.smt.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vt.smt.MyMath.Matrix;


public class AnswerWindow extends Stage {
    public AnswerWindow(){
        super(StageStyle.DECORATED);
        this.setTitle("СЛАУ");
        Scene scene = new Scene(mainPane,700,320);

        scene.getStylesheets().add("css/theme.css");
        this.setScene(scene);
        matrixTable = new SLEInput(0,0);
        matrixAndDet.getChildren().addAll(matrixInfoLabel,matrixTable,detLabel);
        mainPane.setCenter(matrixAndDet);
        mainPane.setBottom(answers);
    }

    public void setMatrix(Double[][] matrix){
        this.matrixTable.setMatrix(new Matrix(matrix));
    }
    public void setDet(Double value){
        detLabel.setText("det = " + value.toString());
        detLabel.setId("determinantLabel");

    }
    public void setAnswers(Double[] x){
        StringBuilder str = new StringBuilder();
        for (int i =0; i<x.length;i++)
            str.append("x" + (i+1) + " = " +
                    (Double.toString(x[i]).length() > 4 ? Double.toString(x[i]).substring(0,4) : Double.toString(i))
                    + ( (i+1) % 7 == 0 ? "\n" : "  "));
        answers.setText(str.toString());
        answers.setId("determinantLabel");
        matrixInfoLabel.setId("determinantLabel");
        matrixAndDet.setTranslateX(20);
        mainPane.getBottom().setTranslateX(20);
    }
    private BorderPane mainPane = new BorderPane();
    private VBox matrixAndDet = new VBox(15);
    private Label detLabel = new Label("det =  ");
    private Label matrixInfoLabel = new Label("Исходная матрица:");
    private Label answers = new Label();
    private SLEInput matrixTable;
}
