package decompiletool.network;

import decompiletool.Utils;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class ApkGetter {
  private final String[] apkInfo;

  public ApkGetter(String[] args) {
    apkInfo = args;
  }

  public boolean getApk() {
    if (isApkLoadedFromHardDisk()) {
      Utils.getInstance().setInitialApkData(apkInfo[0]);
      return true;
    }
    File apkFile = loadApk(apkInfo[0], apkInfo[1]);
    Utils.getInstance().setInitialApkData(Objects.requireNonNull(apkFile).getAbsolutePath());
    return apkFile.exists();
  }

  private File loadApk(String bundleId, String version) {
      URL apkURL = getApkURL(bundleId, version);
    return null;
  }

    private URL getApkURL(String bundleId, String version) {
        return null;
    }

    private boolean isApkLoadedFromHardDisk() {
    return apkInfo.length == 2;
  }
}
