package easytool.cmd;

import easytool.Utils;

public class DecompileProcess implements Command {
  private final TerminalProcessor cmdProcessor;

  public DecompileProcess(TerminalProcessor cmdProcessor) {
    this.cmdProcessor = cmdProcessor;
  }

  @Override
  public boolean execute() {
    return cmdProcessor.decompileApk();
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Decompile APK: " + Utils.getInstance().getApkName());
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Decompile process is finished.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("An error occurred during decompile process. Check the output to find out the reason.");
    System.out.println("----------------------------------------------------");
  }
}
