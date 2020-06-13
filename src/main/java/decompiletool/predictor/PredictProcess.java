package decompiletool.predictor;

import decompiletool.cmd.Command;

public class PredictProcess implements Command {
  private final Predictor predictor;

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
    System.out.println("Predictor process was started.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Predictor project was finished.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {

  }
}
