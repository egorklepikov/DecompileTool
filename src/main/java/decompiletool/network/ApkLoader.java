package decompiletool.network;

import decompiletool.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
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
      try {
        FileUtils.moveFileToDirectory(new File(selectedApp.getAppName()), new File(targetApkPath), true);
      } catch (IOException exception) {
        exception.printStackTrace();
      } finally {
        Utils.getInstance().setInitialApkData(selectedApp.getAppName());
        isLoaded = true;
      }
    } else {
      try {
        URL url = new URL(selectedRelease.getDownloadingURL());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        FileUtils.copyInputStreamToFile(
            httpURLConnection.getInputStream(),
            new File(targetApkPath + selectedApp.getAppName() + ".apk")
        );
        isLoaded = true;
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
    return isLoaded;
  }
}
