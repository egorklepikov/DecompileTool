package decompiletool.injector.code;

import decompiletool.injector.packages.com.appodeal.ads.Appodeal;
import decompiletool.injector.packages.com.appodeal.ads.utils.Log;

public class AppodealCode {

  public static void main(String[] args) {

  }

  /**
   * В этот метод добавляем желаемую интеграцию. Важные моменты:
   * - коллбэки пока что не смогут добавиться. Нечто реализуещее Runnable интерфейс тоже. (в целом все что пораждает доп. классы на этапе сборки).
   * - помимо методов в Appodeal классе можно использовать большинство функционала идущего вместе с JDK.
   * - Вместо Activity нужно передавать null.
   */
  public void injectAppodealCode() {
    Appodeal.setLogLevel(Log.LogLevel.verbose);
    Appodeal.disableNetwork(null, "admob");
    Appodeal.disableNetwork(null, "startapp");
    Appodeal.initialize(null, "APP_KEY", Appodeal.BANNER, true);
    Appodeal.show(null, Appodeal.BANNER_TOP);
  }
}
