package easytool.cmd;

import easytool.Utils;

public class SignProcess implements Command {
  private final TerminalProcessor cmdProcessor;

  public SignProcess(TerminalProcessor cmdProcessor) {
    this.cmdProcessor = cmdProcessor;
  }

  @Override
  public boolean execute() {
    return cmdProcessor.signApk();
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Signing APK: " + Utils.getInstance().getApkName());
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Signing process is finished.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("An error occurred during signing process. Check the output to find out the reason.");
    System.out.println("----------------------------------------------------");
  }
}
