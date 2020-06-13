package decompiletool.cmd;

import decompiletool.Utils;

public class InstallProcess implements Command {
  private final TerminalProcessor cmdProcessor;

  public InstallProcess(TerminalProcessor cmdProcessor) {
    this.cmdProcessor = cmdProcessor;
  }

  @Override
  public boolean execute() {
    return cmdProcessor.installApk();
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Installing APK: " + Utils.getInstance().getApkName());
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Installation process is finished.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("An error occurred during installation process. Check the output to find out the reason.");
    System.out.println("----------------------------------------------------");
  }
}
