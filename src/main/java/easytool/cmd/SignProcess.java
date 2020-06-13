package easytool.cmd;

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
    System.out.println("Подписываем АПК");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Подписание прошло успешно");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("В процессе подписи произошли ошибки");
    System.out.println("----------------------------------------------------");
  }
}
