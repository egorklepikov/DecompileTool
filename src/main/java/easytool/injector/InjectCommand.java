package easytool.injector;

public interface InjectCommand {
  boolean execute();
  void startNotification();
  void endNotification();
  void errorNotification();
}
