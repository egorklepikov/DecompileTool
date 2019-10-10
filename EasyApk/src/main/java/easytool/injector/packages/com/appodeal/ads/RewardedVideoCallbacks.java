package easytool.injector.packages.com.appodeal.ads;

public interface RewardedVideoCallbacks {
    /**
     * Called when rewarded video was loaded
     * @param isPrecache {@code true} if video is precache
     */
    void onRewardedVideoLoaded(boolean isPrecache);

    /**
     * Called when rewarded video is fail to load.
     * But if auto cache enabled for rewarded videos, loading will be continued.
     */
    void onRewardedVideoFailedToLoad();

    /**
     * Called when rewarded video was shown.
     */
    void onRewardedVideoShown();

    /**
     * Called when rewarded video was finished.
     * @param amount amount of reward
     * @param name   name of currency
     */
    void onRewardedVideoFinished(double amount, String name);

    /**
     * Called when rewarded video was closed.
     * @param finished {@code true} if video was finished
     */
    void onRewardedVideoClosed(boolean finished);

    /**
     * Called when rewarded video was expired by time.
     */
    void onRewardedVideoExpired();

    /**
     * Called when rewarded video was clicked.
     */
    void onRewardedVideoClicked();
}
