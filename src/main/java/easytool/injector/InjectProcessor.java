package easytool.injector;

import easytool.Utils;

import java.io.File;

public class InjectProcessor {
  private SmaliSearcher smaliSearcher;

  public InjectProcessor() {
  }

  public boolean injectMethods() {
    if (Utils.getInstance().getApkName() == null) {
      return false;
    }

    smaliSearcher = new SmaliSearcher();
    File inputPoint = smaliSearcher.getSmali();
    if (inputPoint != null) {
      JavaConverter javaConverter = new JavaConverter();
      SmaliPostProcess smaliPostProcess = new SmaliPostProcess(javaConverter.getAppodealSmaliCode(), smaliSearcher.getUserMainClassName());
      SmaliConnector smaliConnector = new SmaliConnector(inputPoint, smaliPostProcess.modifySmaliCode(), smaliSearcher.getUserMainClassName());

      return smaliConnector.connect();
    }

    return false;
  }
}
