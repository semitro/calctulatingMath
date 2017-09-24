package vt.smt.GUI.Observer;

import javafx.util.Pair;

/**
 * Created by semitro on 24.09.17.
 */
public class ChooseCeil implements MatrixEvent{
    private Pair<Integer,Integer> position;
    // Цвет мы передаём как айди для css-стиля
    private String colorID;
    public ChooseCeil(Pair<Integer,Integer> position, String colorID){
        this.position = position;
        this.colorID = colorID;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public String getColorID() {
        return colorID;
    }

    public void setColorID(String colorID) {
        this.colorID = colorID;
    }

    public void setPosition(Pair<Integer, Integer> position) {
        this.position = position;
    }
}
