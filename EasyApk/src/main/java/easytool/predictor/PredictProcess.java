package easytool.predictor;

import easytool.cmd.Command;

public class PredictProcess implements Command {
    private Predictor predictor;

    public PredictProcess() {
        predictor = new JarPredictor();
    }

    @Override
    public boolean execute() {
        return predictor.predict();
    }

    @Override
    public void startNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Поиск проблем внутри АПК");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Поиск окончен.");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void errorNotification() {

    }
}
