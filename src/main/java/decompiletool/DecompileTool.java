package decompiletool;

import decompiletool.cmd.*;
import decompiletool.manifest.ModifyProcess;
import decompiletool.network.ApkInfoLoader;
import decompiletool.network.AppInformation;
import decompiletool.network.GetApkProcess;
import decompiletool.network.PreparationInvoker;

import java.io.IOException;
import java.util.ArrayList;

public class DecompileTool {
  private static AppInformation selectedApp;
  private static ApkInfoLoader infoLoader;

  public DecompileTool() {
    infoLoader = new ApkInfoLoader();
    Utils.getInstance().setSystemName(System.getProperty("os.name"));
  }

  public static ArrayList<AppInformation> getAppsList(String searchQuery) {
    try {
      return infoLoader.loadData(searchQuery);
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public static void selectApp(AppInformation appInformation) {
    selectedApp = appInformation;
  }

  public static void selectApp(String applicationPath) {
    selectedApp = new AppInformation();
    selectedApp.setAppName(applicationPath);
    selectedApp.setVendor("local_installation");
    selectedApp.setAppVersions(null);
  }

  public static void startAppProcessing() {
    if (selectedApp == null) {
      throw new RuntimeException("The application is not selected");
    }

    PreparationInvoker preparationInvoker = new PreparationInvoker();
    preparationInvoker.putCommand(new GetApkProcess(selectedApp));
    preparationInvoker.startExecuting();

    if (preparationInvoker.isApplicationReady()) {
      TerminalProcessor terminalProcessor = new TerminalProcessor();
      CommandInvoker invoker = new CommandInvoker();
      invoker.putCommand(new DecompileProcess(terminalProcessor));
      invoker.putCommand(new ModifyProcess(false));
      invoker.putCommand(new CompileProcess(terminalProcessor));
      invoker.putCommand(new SignProcess(terminalProcessor));
      invoker.putCommand(new InstallProcess(terminalProcessor));
      invoker.startExecuting();
    }
  }
}
