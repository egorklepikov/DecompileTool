package easytool.injector.packages.com.appodeal.ads;

import easytool.injector.packages.android.app.Activity;
import easytool.injector.packages.android.content.Context;
import easytool.injector.packages.com.appodeal.ads.utils.Log;

public class Appodeal {
    public static final int INTERSTITIAL = 3;               //0000000011
    public static final int BANNER = 4;                     //0000000100
    public static final int BANNER_BOTTOM = 8;              //0000001000
    public static final int BANNER_TOP = 16;                //0000010000
    public static final int REWARDED_VIDEO = 128;           //0010000000

    public static void initialize(Activity activity, final String appKey, final int adTypes) { }

    public static void initialize(Activity activity, final String appKey, final int adTypes, final boolean hasConsent) { }

    public static boolean isInitialized(int adType) { return true; }

    public static boolean isAutoCacheEnabled(int adType) { return true; }

    public static void setInterstitialCallbacks(InterstitialCallbacks callbacks) { }

    public static void setRewardedVideoCallbacks(RewardedVideoCallbacks callbacks) { }

    public static void setBannerCallbacks(BannerCallbacks callbacks) { }

    public static void cache(Activity activity, int adTypes) { }

    public static boolean show(Activity activity, int adTypes) { return true; }

    public static boolean show(Activity activity, int adTypes, String placementName) { return true; }

    public static void hide(Activity activity, int adTypes) { }

    public static void setAutoCache(int adTypes, boolean autoCache) { }

    public static void setTriggerOnLoadedOnPrecache(int adTypes, boolean triggerOnLoadedOnPrecache) { }

    public static boolean isLoaded(int adTypes) { return true; }

    public static boolean isPrecache(int adType) { return true; }

    public static void setSmartBanners(boolean enabled) { }

    public static void set728x90Banners(boolean enabled) { }

    public static void setBannerAnimation(boolean animate) { }

    public static void onResume(Activity activity, int adTypes) { }

    public static void trackInAppPurchase(Context context, double amount, String currency) { }

    public static void disableNetwork(Context context, String network) { }

    public static void disableNetwork(Context context, String network, int adTypes) { }

    public static void disableLocationPermissionCheck() { }

    public static void disableWriteExternalStoragePermissionCheck() { }

    public static String getVersion() { return null; }

    public static void setTesting(boolean testMode) { }

    public static void setLogLevel(Log.LogLevel logLevel) { }

    public static void setSegmentFilter(String name, boolean value) { }

    public static void setSegmentFilter(String name, int value) { }

    public static void setSegmentFilter(String name, double value) { }

    public static void setSegmentFilter(String name, String value) { }

    public static boolean canShow(int adTypes) { return true; }

    public static boolean canShow(int adTypes, String placementName) { return true; }

    public static void muteVideosIfCallsMuted(boolean muteVideosIfCallsMuted) { }

    public static void startTestActivity(Activity activity) { }

    public static void setChildDirectedTreatment(boolean value) { }

    public static void destroy(int adTypes) { }

    public static void setExtraData(String key, String value) { }

    public static void setExtraData(String key, int value) { }

    public static void setExtraData(String key, double value) { }

    public static void setExtraData(String key, boolean value) { }

    public static double getPredictedEcpm(final int adType) { return 0.0f; }
}