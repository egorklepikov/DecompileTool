package easytool.injector.packages.com.appodeal.ads.utils;

public class Log {
  public static void log(Throwable throwable) {
  }

  public static void log(String key, String event) {
  }

  public static void log(String key, String event, LogLevel logLevel) {
  }

  public static void log(String key, String event, String message) {
  }

  public static void log(String key, String event, String message, LogLevel logLevel) {
  }

  public enum LogLevel {
    none(0),
    debug(1),
    verbose(2);

    LogLevel(int value) {
    }

    public static LogLevel fromInteger(Integer x) {
      return null;
    }

    public int getValue() {
      return 0;
    }
  }
}
