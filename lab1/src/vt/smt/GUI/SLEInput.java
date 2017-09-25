package vt.smt.GUI;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import vt.smt.GUI.Observer.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by semitro on 23.09.17.
 */
class SLEInput extends Pane implements Observer {

    private VBox vBox = new VBox();
    public  SLEInput(int rows, int columns){
        super();
        vBox.setSpacing(5);
        setSize(rows,columns);
        this.getChildren().add(vBox);
        //this.setStyle("fx-min");

    }

    public void setMatrix(vt.smt.MyMath.Matrix matrix){
        setSize(matrix.getX(),matrix.getY());
        for (int i = 0; i < matrix.getY(); i++)
            ((EquationStroke)vBox.getChildren().get(i)).setValues(matrix.getRow(i));
    }
    // Количество полей для ввода
    public void setSize(int rows, int columns){
        if( columns > vBox.getChildren().size()  )
            while ( vBox.getChildren().size() < columns)
                vBox.getChildren().add(new EquationStroke());
        else
            vBox.getChildren().remove(columns,vBox.getChildren().size());

        vBox.getChildren().forEach(e->{
            ((EquationStroke)e).setLength(rows);
        });
    }

    // Отрисовка изменений в самой матрице. Обработка сообщений от Obserable Matirx
    @Override
    public synchronized void notice(MyEvent event) {
        Platform.runLater(()->{
            resetStyles();
            // Перекрашиваем цвет выбранной ячейки, меняя css-стиль
            if(event instanceof ChooseCeil) {
                lastNodeChanged.add(
                        getFiledNumber( ((ChooseCeil)event).getPosition().getKey(),((ChooseCeil)event).getPosition().getValue())
                );
                getFiledNumber( ((ChooseCeil)event).getPosition().getKey(),((ChooseCeil)event).getPosition().getValue())
                        .setId( ((ChooseCeil)event).getColorID());
            }
            else
            if(event instanceof ChangeCeil){
                getFiledNumber( ((ChangeCeil)event ).getPosition().getKey(), ((ChangeCeil)event ).getPosition().getValue())
                        .setText(((ChangeCeil)event ).getText());
            }
            else
            if(event instanceof SwapLines) {
                if (((SwapLines) event).isAboutRows()) {
                    lastNodeChanged.addAll(
                            ((EquationStroke) vBox.getChildren().get(((SwapLines) event).getI()))
                                    .getAllTextFields()
                    );
                    lastNodeChanged.addAll(
                            ((EquationStroke) vBox.getChildren().get(((SwapLines) event).getJ()))
                                    .getAllTextFields()
                    );

                    ((EquationStroke) vBox.getChildren().get(((SwapLines) event).getI()))
                            .forEachInput(e -> e.setId("lineIsChanging"));
                    ((EquationStroke) vBox.getChildren().get(((SwapLines) event).getJ()))
                            .forEachInput(e -> e.setId("lineIsChanging"));
                } else {
                    for (int i = 0; i < vBox.getChildren().size(); i++) {
                        ((EquationStroke) vBox.getChildren().get(i)).getFieldNumber(((SwapLines) event).getI())
                                .setId("lineIsChanging");
                        ((EquationStroke) vBox.getChildren().get(i)).getFieldNumber(((SwapLines) event).getJ())
                                .setId("lineIsChanging");
                        lastNodeChanged.add(
                                ((EquationStroke) vBox.getChildren().get(i)).getFieldNumber(((SwapLines) event).getJ()));
                        lastNodeChanged.add(
                                ((EquationStroke) vBox.getChildren().get(i)).getFieldNumber(((SwapLines) event).getI()));
                    }
                }
            }
            else // Крутящаяся красота
            if(event instanceof PopUpText){
                Random r = new Random(System.currentTimeMillis());

                Label label = new Label( ((PopUpText)event).getStr());
                RotateTransition rotate = new RotateTransition(Duration.millis(7400),label);
                rotate.setByAngle(45 + r.nextInt(15));
                ScaleTransition scale = new ScaleTransition(Duration.millis(7500),label);
                scale.setByX(3);
                scale.setByY(3);
                FadeTransition fade = new FadeTransition(Duration.millis(5800),label);
                fade.setToValue(0);
                fade.setDelay(Duration.millis(2000));
                ParallelTransition parallelTransition = new ParallelTransition();
                parallelTransition.getChildren().addAll(
                  rotate,scale,fade
                );
                Popup popup = new Popup();
                label.setId("popUpText");
                popup.getContent().add(label);
                popup.setY(r.nextDouble() + r.nextInt(500));
                popup.show(stageToKeepPopUp);
                parallelTransition.setDelay(Duration.millis(200));
                parallelTransition.play();
                parallelTransition.setOnFinished(e->popup.hide());
                popup.setX(r.nextDouble() + r.nextInt(860));

                }

        });
    }

    // Эти поля нужны, чтобы вернуть все цвета назад
    private List<Node> lastNodeChanged = new LinkedList<>();
    public void resetStyles(){
            for (Node l : lastNodeChanged) l.setId("inputValueCeil");
            lastNodeChanged.clear();
    }
    public TextField getFiledNumber(int i, int j){
        return (TextField)((EquationStroke)vBox.getChildren().get(i)).getFieldNumber(j);
    }
    public int getRows(){
        return vBox.getChildren().size();
    }
    public int getColumns(){
        return ((EquationStroke)vBox.getChildren().get(0)).getNumber();

    }
    public Double[][] getMatrix() {
        Double[][] ans = new Double[getRows()][getColumns()];
        for (int i = 0; i < ans.length; i++)
            ans[i] = ((EquationStroke) vBox.getChildren().get(i)).getValues();
        return ans;
    }
    private Stage stageToKeepPopUp;

    public void setStageToKeepPopUp(Stage stageToKeepPopUp) {
        this.stageToKeepPopUp = stageToKeepPopUp;
    }
}