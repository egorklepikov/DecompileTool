package easytool.manifest;

import easytool.cmd.Command;
import easytool.injector.InjectInvoker;
import easytool.injector.InjectProcess;
import easytool.injector.InjectProcessor;

public class ModifyProcess implements Command {
    private ManifestProcessor manifestProcessor;
    private ManifestInvoker manifestInvoker;

    private InjectProcessor injectProcessor;
    private InjectInvoker injectInvoker;

    public ModifyProcess(boolean isInjectorRequired) {
        manifestProcessor = new ManifestProcessor();
        manifestInvoker = new ManifestInvoker();
        manifestInvoker.putCommand(new NetworkSecurityConfig(manifestProcessor));
        manifestInvoker.putCommand(new WriteExternalStorage(manifestProcessor));
        manifestInvoker.putCommand(new DebugMode(manifestProcessor));

        if (isInjectorRequired) {
            injectProcessor = new InjectProcessor();
            injectInvoker = new InjectInvoker();
            injectInvoker.putCommand(new InjectProcess(injectProcessor));
        }
    }

    @Override
    public boolean execute() {
        return manifestInvoker.startExecuting() && injectInvoker.startExecuting();
    }

    @Override
    public void startNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Модифицируем АПК");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Модификация закончена");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void errorNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("В процессе модицикации возникли проблемы");
        System.out.println("----------------------------------------------------");
    }
}
