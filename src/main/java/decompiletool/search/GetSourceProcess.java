package decompiletool.search;

import decompiletool.Utils;
import decompiletool.cmd.Command;
import decompiletool.cmd.TerminalProcessor;

public class GetSourceProcess implements Command {
  private final TerminalProcessor cmdProcessor;

  public GetSourceProcess(TerminalProcessor cmdProcessor) {
    this.cmdProcessor = cmdProcessor;
  }

  @Override
  public boolean execute() {
    String frameworkName = Utils.getInstance().getFramework();
    if (frameworkName.equals("Android")) {
      return cmdProcessor.decompileApkJadX();
    } else if (frameworkName.equals("Unity")) {
      return cmdProcessor.decompileApkIlSpy();
    } else {
      System.out.println("В проекте включен IL2CPP. Dll файл не найден");
      return false;
    }
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Запущен процесс получения исходников");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Исходники были получены");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("В процессе декомпиляции возникли проблемы");
    System.out.println("----------------------------------------------------");
  }
}
