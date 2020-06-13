package decompiletool.cmd;

public interface Command {
  boolean execute();
  void startNotification();
  void endNotification();
  void errorNotification();
}
