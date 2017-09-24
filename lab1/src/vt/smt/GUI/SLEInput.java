package vt.smt.GUI;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vt.smt.GUI.Observer.*;

import java.util.LinkedList;
import java.util.List;

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

        });
    }

    // Эти поля нужны, чтобы вернуть все цвета назад
    private List<Node> lastNodeChanged = new LinkedList<>();
    public void resetStyles(){
            if(lastNodeChanged.isEmpty())
                return;

            for (Node l : lastNodeChanged) try { l.setId("inputValueCeil");
            }catch (Exception e){
                // do nothing. It's wrong, but xren c nim pocka chto. Вылазиет неочевидный ArrayOutOfBound exeption
            }

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
    Double[][] getMatrix() {
        Double[][] ans = new Double[getRows()][getColumns()];
        for (int i = 0; i < ans.length; i++)
            ans[i] = ((EquationStroke) vBox.getChildren().get(i)).getValues();
        return ans;
    }

}