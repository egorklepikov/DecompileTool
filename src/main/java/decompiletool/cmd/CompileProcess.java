package decompiletool.cmd;

import decompiletool.Utils;

public class CompileProcess implements Command {
  private final TerminalProcessor cmdProcessor;

  public CompileProcess(TerminalProcessor cmdProcessor) {
    this.cmdProcessor = cmdProcessor;
  }

  @Override
  public boolean execute() {
    return cmdProcessor.compileApk();
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Compile APK: " + Utils.getInstance().getApkName());
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Compile process is finished.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("An error occurred during compile process. Check the output to find out the reason.");
    System.out.println("----------------------------------------------------");
  }
}
