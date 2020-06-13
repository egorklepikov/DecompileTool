package easytool.manifest;

import easytool.Utils;
import easytool.cmd.Command;
import easytool.injector.InjectInvoker;
import easytool.injector.InjectProcess;
import easytool.injector.InjectProcessor;

public class ModifyProcess implements Command {
  private final ManifestProcessor manifestProcessor;
  private final ManifestInvoker manifestInvoker;

  private InjectProcessor injectProcessor;
  private InjectInvoker injectInvoker;
  private final boolean isInjectorRequired;

  public ModifyProcess(boolean isInjectorRequired) {
    this.isInjectorRequired = isInjectorRequired;
    manifestProcessor = new ManifestProcessor();
    manifestInvoker = new ManifestInvoker();
    manifestInvoker.putCommand(new NetworkSecurityConfig(manifestProcessor));
    manifestInvoker.putCommand(new WriteExternalStorage(manifestProcessor));
    manifestInvoker.putCommand(new DebugMode(manifestProcessor));

    if (isInjectorRequired) {
      injectProcessor = new InjectProcessor();
      injectInvoker = new InjectInvoker();
      injectInvoker.putCommand(new InjectProcess(injectProcessor));
    }
  }

  @Override
  public boolean execute() {
    if (isInjectorRequired) {
      return manifestInvoker.startExecuting() && injectInvoker.startExecuting();
    } else {
      return manifestInvoker.startExecuting();
    }
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Modifying APK: " + Utils.getInstance().getApkName());
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Modification process is finished.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("An error occurred during modification process. Check the output to find out the reason");
    System.out.println("----------------------------------------------------");
  }
}
