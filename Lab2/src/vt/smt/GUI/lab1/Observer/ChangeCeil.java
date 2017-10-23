package vt.smt.GUI.lab1.Observer;

import javafx.util.Pair;

/**
 * Этот класс был бы слишком похож на CooseCeil, но он всё же другой.
 * Используем какой-то паттерн, о котором меня спрашивал Иван в прошлом году
 * 24.09.2017
 * 19:07
 */
public class ChangeCeil implements MatrixEvent {
    private ChooseCeil wrapped;

    public ChangeCeil(Pair<Integer, Integer> position, String text) {
        wrapped = new ChooseCeil(position, text);
    }

    public String getText() {
        return wrapped.getColorID();
    }

    public Pair<Integer, Integer> getPosition() {
        return wrapped.getPosition();
    }
}
