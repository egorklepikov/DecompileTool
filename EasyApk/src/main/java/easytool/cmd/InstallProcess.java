package easytool.cmd;

public class InstallProcess implements Command {
    private TerminalProcessor cmdProcessor;

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
        System.out.println("Устанавливаем АПК");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Установка завершена");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void errorNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("В процессе установши возникли проблемы");
        System.out.println("----------------------------------------------------");
    }
}
