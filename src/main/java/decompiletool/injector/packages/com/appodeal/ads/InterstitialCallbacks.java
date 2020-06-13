package decompiletool.injector.packages.com.appodeal.ads;

public interface InterstitialCallbacks {
  /**
   * Called when interstitial was loaded
   *
   * @param isPrecache {@code true} if interstitial is precache
   */
  void onInterstitialLoaded(boolean isPrecache);

  /**
   * Called when interstitial is fail to load.
   * But if auto cache enabled for interstitials, loading will be continued.
   */
  void onInterstitialFailedToLoad();

  /**
   * Called when interstitial was shown.
   */
  void onInterstitialShown();

  /**
   * Called when interstitial was clicked.
   */
  void onInterstitialClicked();

  /**
   * Called when interstitial was closed.
   */
  void onInterstitialClosed();

  /**
   * Called when interstitial was expired by time.
   */
  void onInterstitialExpired();
}
