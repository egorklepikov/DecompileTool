package decompiletool.predictor;

import decompiletool.CmdProcessor;
import decompiletool.Utils;

public class JarPredictor extends CmdProcessor implements Predictor {
  @Override
  public boolean predict() {
    return processCmdCommand("java -jar apd-analyzer-0.1.0-beta.jar -af " + Utils.getInstance().getFullApkName(), true);
  }
}
