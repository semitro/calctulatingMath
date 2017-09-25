package vt.smt.GUI.Observer;

/**
 * Created by semitro on 25.09.17.
 */
public class PopUpText implements MyEvent, MatrixEvent{
    private String str;

    public PopUpText(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
