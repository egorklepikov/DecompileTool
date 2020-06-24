package decompiletool.network;

import decompiletool.cmd.Command;

public class GetApkProcess implements Command {
    private final ApkGetter apkGetter;

    public GetApkProcess(String[] args) {
        apkGetter = new ApkGetter(args);
    }

    @Override
    public boolean execute() {
        return apkGetter.getApk();
    }

    @Override
    public void startNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Loading APK...");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Loading APK process is finished.");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void errorNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("The error occurred when loading the APK.");
        System.out.println("----------------------------------------------------");
    }
}
