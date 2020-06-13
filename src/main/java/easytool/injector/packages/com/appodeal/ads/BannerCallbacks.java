package easytool.injector.packages.com.appodeal.ads;

public interface BannerCallbacks {
  /**
   * Called when banner was loaded
   *
   * @param height     height of the banner (currently 32, 50 or 90 dp)
   * @param isPrecache {@code true} if banner is precache
   */
  void onBannerLoaded(int height, boolean isPrecache);

  /**
   * Called when banner is fail to load.
   * But if auto cache enabled for banners, loading will be continued.
   */
  void onBannerFailedToLoad();

  /**
   * Called when banner was shown.
   */
  void onBannerShown();

  /**
   * Called when banner was clicked.
   */
  void onBannerClicked();

  /**
   * Called when banner was expired by time.
   */
  void onBannerExpired();
}
