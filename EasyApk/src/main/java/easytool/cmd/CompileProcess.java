package easytool.cmd;

import easytool.CmdProcessor;

public class CompileProcess implements Command {
    private TerminalProcessor cmdProcessor;

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
        System.out.println("Компилируем АПК");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Компиляция завершена успешно");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void errorNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("В процессе компиляции произошли ошибки");
        System.out.println("----------------------------------------------------");
    }
}
