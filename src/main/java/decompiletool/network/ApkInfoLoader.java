package decompiletool.network;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ApkInfoLoader {
  public ArrayList<AppInformation> loadData(String searchQuery) throws IOException {
    ArrayList<AppInformation> applications = new ArrayList<>();
    Document htmlResponse = Jsoup.connect(buildRequest(searchQuery)).get();
    Elements appsList = htmlResponse.select("dl.search-dl");
    for (Element app : appsList) {
      AppInformation appInformation = new AppInformation();
      appInformation.setAppName(findAppName(app));
      appInformation.setVendor(findAppVendor(app));
      appInformation.setAppURL(findAppURL(app));
      appInformation.setAppVersions(findAppVersions(appInformation.getAppURL()));
      applications.add(appInformation);
    }
    return applications;
  }

  private String findAppVendor(Element app) {
    return app.select("dd").first().select("p").get(1).select("a").first().ownText();
  }

  private String findAppName(Element app) {
    return app.select("dt").first().select("a").attr("title");
  }

  private String findAppURL(Element app) {
    return "https://apkpure.com" + app.select("dt").first().select("a").attr("href");
  }

  private ArrayList<AppInformation.AppRelease> findAppVersions(String appURL) throws IOException {
    ArrayList<AppInformation.AppRelease> releaseInformation = new ArrayList<>();
    Document htmlResponse = Jsoup.connect(appURL).get();

    //Getting versions page URL
    String versionURL = htmlResponse.select("div.ny-down").first().select("a").get(1).attr("href");
    htmlResponse = Jsoup.connect("https://apkpure.com" + versionURL).get();

    Elements appReleases = htmlResponse.select("div.ver").first().select("li");
    for (Element release : appReleases) {
      AppInformation.AppRelease appRelease = new AppInformation.AppRelease();
      if (buildVariants(release) > 1) {
        appRelease.setDownloadingURL("skipped_release");
      } else {
        appRelease.setDownloadingURL(findReleaseURL(release));
      }
      appRelease.setReleaseDate(findReleaseDate(release));
      appRelease.setReleaseVersion(findReleaseVersion(release));
      appRelease.setReleaseSize(findReleaseSize(release));
      releaseInformation.add(appRelease);
    }
    return releaseInformation;
  }

  private int buildVariants(Element release) {
    Element verItemDiv = release.select("a").first().select("div.ver-item-v").first();
    if (verItemDiv == null) {
      return 1;
    }
    return Integer.parseInt(verItemDiv.select("span.ver-n").first().ownText());
  }

  private String findReleaseURL(Element release) {
    return "https://apkpure.com" + release.select("a").first().attr("href");
  }

  private String findReleaseSize(Element release) {
    return release.select("div.ver-item").first().
        select("div.ver-item-wrap").first().
        select("span.ver-item-s").first().ownText();
  }

  private String findReleaseVersion(Element release) {
    return release.select("div.ver-item").first().
        select("div.ver-item-wrap").first().
        select("span.ver-item-n").first().ownText();
  }

  private String findReleaseDate(Element release) {
    return release.select("div.ver-item-a").first().
        select("p.update-on").first().ownText();
  }

  private String buildRequest(String searchQuery) {
    return "https://apkpure.com/search" + "?q=" + convertSearchQuery(searchQuery) + "&t=app";
  }

  private String convertSearchQuery(String searchQuery) {
    return searchQuery.replace(" ", "+");
  }
}
