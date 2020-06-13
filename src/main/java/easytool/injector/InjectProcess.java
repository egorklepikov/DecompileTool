package easytool.injector;

public class InjectProcess implements InjectCommand {
    private InjectProcessor injectProcessor;

    public InjectProcess(InjectProcessor injectProcessor) {
        this.injectProcessor = injectProcessor;
    }

    @Override
    public boolean execute() {
        return injectProcessor.injectMethods();
    }

    @Override
    public void startNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Добавляем методы");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Добавление методов завершено");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void errorNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("В процессе добавления сметодов возникли проблемы");
        System.out.println("----------------------------------------------------");
    }
}
