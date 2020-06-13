package easytool.injector.packages.com.appodeal.ads.utils;

public class Log {
    public enum LogLevel {
        none(0),
        debug(1),
        verbose(2);

        LogLevel(int value) { }

        public int getValue() { return 0; }

        public static LogLevel fromInteger(Integer x) { return null; }
    }

    public static void log(Throwable throwable) { }

    public static void log(String key, String event) { }

    public static void log(String key, String event, LogLevel logLevel) { }

    public static void log(String key, String event, String message) { }

    public static void log(String key, String event, String message, LogLevel logLevel) { }
}
