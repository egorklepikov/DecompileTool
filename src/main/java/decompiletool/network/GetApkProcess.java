package decompiletool.network;

import decompiletool.cmd.Command;

public class GetApkProcess implements Command {
    public GetApkProcess(AppInformation selectedApp) {

    }

    @Override
    public boolean execute() {
        return false;
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
