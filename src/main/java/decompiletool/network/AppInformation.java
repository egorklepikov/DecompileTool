package decompiletool.network;

import java.util.ArrayList;

public class AppInformation {
  private String appName;
  private String vendor;
  private String appURL;
  private ArrayList<AppRelease> appVersions;

  public static class AppRelease {
    private String releaseVersion;
    private String releaseDate;
    private String downloadingURL;
    private String releaseSize;

    public String getReleaseSize() {
      return releaseSize;
    }

    public void setReleaseSize(String releaseSize) {
      this.releaseSize = releaseSize;
    }

    public String getReleaseVersion() {
      return releaseVersion;
    }

    public void setReleaseVersion(String appVersion) {
      this.releaseVersion = appVersion;
    }

    public String getReleaseDate() {
      return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
      this.releaseDate = releaseDate;
    }

    public String getDownloadingURL() {
      return downloadingURL;
    }

    public void setDownloadingURL(String downloadingURL) {
      this.downloadingURL = downloadingURL;
    }

    @Override
    public String toString() {
      return "AppRelease{" +
          "releaseVersion='" + releaseVersion + '\'' +
          ", releaseDate='" + releaseDate + '\'' +
          ", downloadingURL='" + downloadingURL + '\'' +
          ", releaseSize='" + releaseSize + '\'' +
          '}';
    }
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public ArrayList<AppRelease> getAppVersions() {
    return appVersions;
  }

  public void setAppVersions(ArrayList<AppRelease> appVersions) {
    this.appVersions = appVersions;
  }

  public String getAppURL() {
    return appURL;
  }

  public void setAppURL(String appURL) {
    this.appURL = appURL;
  }

  @Override
  public String toString() {
    return "AppInformation { " + "'\n" +
        "  appName = '" + appName + "'\n" +
        "  vendor = '" + vendor + "'\n" +
        "  appURL = '" + appURL + "'\n" +
        "  appVersions = " + appVersions + "'\n" +
        '}';
  }
}