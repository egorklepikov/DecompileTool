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

    //Getting app versions URL
    Elements nYDivElements = htmlResponse.select("div.ny-down").first().select("a");
    if (nYDivElements.size() == 1) {
      throw new RuntimeException("Application is paid only.");
    } else {
      String versionURL = nYDivElements.get(1).attr("href");
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
    }
    return releaseInformation;
  }

  //       <div class="ny-down">
  //        <a rel="nofollow" class="google-play-badge" href="https://play.google.com/store/apps/details?id=air.com.noodlecake.luminocity&amp;referrer=utm_source%3Dapkpure.com" target="_blank" title="Download on Google Play"> <img src="https://static.apkpure.com/www/static/imgs/google-play-badge.png" height="50"> </a>
  //        <div class="pricing" itemscope itemprop="offers" itemtype="https://schema.org/Offer">
  //         <div class="price only">
  //          $4.99
  //         </div>
  //         <meta itemprop="price" content="4.99">
  //         <meta itemprop="priceCurrency" content="USD">
  //         <meta itemprop="availability" content="https://schema.org/InStock">
  //        </div>
  //       </div>

  private int buildVariants(Element release) {
    Element verItemDiv = release.select("a").first().select("div.ver-item-v").first();
    if (verItemDiv == null) {
      return 1;
    }
    return Integer.parseInt(verItemDiv.select("span.ver-n").first().ownText());
  }

  private String findReleaseURL(Element release) throws IOException {
    String downloadingPageURL = "https://apkpure.com" + release.select("a").first().attr("href");
    Document htmlResponse = Jsoup.connect(downloadingPageURL).get();
    return htmlResponse.select("iframe.iframe_download").first().attr("src");
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
