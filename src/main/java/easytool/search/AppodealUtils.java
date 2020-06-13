package easytool.search;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AppodealUtils {
  private static final AppodealUtils ourInstance = new AppodealUtils();
  private static ConcurrentLinkedQueue<String> appodealMethods;

  private AppodealUtils() {
  }

  public static AppodealUtils getInstance() {
    return ourInstance;
  }

  private static void fillList() {
    appodealMethods.add("Appodeal.setRequestCallbacks");
    appodealMethods.add("Appodeal.setInterstitialCallbacks");
    appodealMethods.add("Appodeal.setRewardedVideoCallbacks");
    appodealMethods.add("Appodeal.setNonSkippableVideoCallbacks");
    appodealMethods.add("Appodeal.setBannerCallbacks");
    appodealMethods.add("Appodeal.setMrecCallbacks");
    appodealMethods.add("Appodeal.setNativeCallbacks");
    appodealMethods.add("Appodeal.setAutoCache");
    appodealMethods.add("Appodeal.setTriggerOnLoadedOnPrecache");
    appodealMethods.add("Appodeal.setSmartBanners");
    appodealMethods.add("Appodeal.set728x90Banners");
    appodealMethods.add("Appodeal.setBannerAnimation");
    appodealMethods.add("Appodeal.disableNetwork");
    appodealMethods.add("Appodeal.disableLocationPermissionCheck");
    appodealMethods.add("Appodeal.disableWriteExternalStoragePermissionCheck");
    appodealMethods.add("Appodeal.setTesting");
    appodealMethods.add("Appodeal.initialize");
    appodealMethods.add("Appodeal.isInitialized");
    appodealMethods.add("Appodeal.isAutoCacheEnabled");
    appodealMethods.add("Appodeal.setNativeAdType");
    appodealMethods.add("Appodeal.cache");
    appodealMethods.add("Appodeal.show");
    appodealMethods.add("Appodeal.hide");
    appodealMethods.add("Appodeal.isLoaded");
    appodealMethods.add("Appodeal.isPrecache");
    appodealMethods.add("Appodeal.setBannerViewId");
    appodealMethods.add("Appodeal.getBannerView");
    appodealMethods.add("Appodeal.setMrecViewId");
    appodealMethods.add("Appodeal.getMrecView");
    appodealMethods.add("Appodeal.setRequiredNativeMediaAssetType");
    appodealMethods.add("Appodeal.onResume");
    appodealMethods.add("Appodeal.trackInAppPurchase");
    appodealMethods.add("Appodeal.getNetworks");
    appodealMethods.add("Appodeal.getUserSettings");
    appodealMethods.add("Appodeal.getVersion");
    appodealMethods.add("Appodeal.getBuildDate");
    appodealMethods.add("Appodeal.setLogLevel");
    appodealMethods.add("Appodeal.getLogLevel");
    appodealMethods.add("Appodeal.requestAndroidMPermissions");
    appodealMethods.add("Appodeal.getNativeAdBox");
    appodealMethods.add("Appodeal.getNativeAds");
    appodealMethods.add("Appodeal.getAvailableNativeAdsCount");
    appodealMethods.add("Appodeal.canShow");
    appodealMethods.add("Appodeal.getRewardParameters");
    appodealMethods.add("Appodeal.setFramework");
    appodealMethods.add("Appodeal.muteVideosIfCallsMuted");
    appodealMethods.add("Appodeal.disableWebViewCacheClear");
    appodealMethods.add("Appodeal.startTestActivity");
    appodealMethods.add("Appodeal.setChildDirectedTreatment");
    appodealMethods.add("Appodeal.destroy");
    appodealMethods.add("Appodeal.setExtraData");
    appodealMethods.add("Appodeal.getPredictedEcpm");
    appodealMethods.add("Appodeal.getSessionManager");
  }

  public ConcurrentLinkedQueue<String> getAppodealMethods() {
    if (appodealMethods == null) {
      appodealMethods = new ConcurrentLinkedQueue<>();
      fillList();
    }
    return appodealMethods;
  }
}
