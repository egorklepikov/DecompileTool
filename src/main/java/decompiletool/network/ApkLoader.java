package decompiletool.network;

import decompiletool.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ApkLoader {
  private final AppInformation selectedApp;
  private final AppInformation.AppRelease selectedRelease;
  private final String targetApkPath;

  public ApkLoader(AppInformation selectedApp, AppInformation.AppRelease selectedRelease, String targetApkPath) {
    this.selectedApp = selectedApp;
    this.selectedRelease = selectedRelease;
    this.targetApkPath = targetApkPath;
  }

  public boolean loadApk() {
    boolean isLoaded = false;
    if (selectedApp.getVendor().equals("local_installation") && selectedRelease == null) {
      Utils.getInstance().setInitialApkData(selectedApp.getAppName());
      isLoaded = true;
    } else {
      try {
        URL url = new URL(selectedRelease.getDownloadingURL());
        FileUtils.copyURLToFile(
            url,
            new File(targetApkPath + selectedApp.getAppName() + ".apk"),
            5000,
            300000
        );
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isLoaded;
  }
}
