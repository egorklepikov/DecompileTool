package easytool.cmd;

public class DecompileProcess implements Command {
    private TerminalProcessor cmdProcessor;

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
        System.out.println("Декомпилируем АПК");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Декомпиляция завершена");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void errorNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("В процессе декомпиляции произошли ошибки");
        System.out.println("----------------------------------------------------");
    }
}
