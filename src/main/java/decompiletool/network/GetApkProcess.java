package decompiletool.network;

import decompiletool.cmd.Command;

public class GetApkProcess implements Command {
  private ApkLoader apkLoader;

  public GetApkProcess(AppInformation selectedApp, AppInformation.AppRelease selectedRelease, String targetApkPath) {
    apkLoader = new ApkLoader(selectedApp, selectedRelease, targetApkPath);
  }

  @Override
  public boolean execute() {
    return apkLoader.loadApk();
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
