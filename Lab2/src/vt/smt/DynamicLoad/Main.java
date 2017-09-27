package vt.smt.DynamicLoad;

/**
 * Created by semitro on 27.09.17.
 */
public class Main {
    public static void main(String[] argv) throws Exception{
        DynamicFunctionChanger changer = new DynamicFunctionChanger();
        changer.setCode("sin(x)");
        FunctionLoader loader = new FunctionLoader();
        System.out.println(loader.getFunction().apply(3.1415));

    }
}
