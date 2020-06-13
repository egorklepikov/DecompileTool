package easytool.injector;

import java.util.ArrayList;
import java.util.List;

public class InjectInvoker {
  private final List<InjectCommand> injectCommandList;

  public InjectInvoker() {
    this.injectCommandList = new ArrayList<>();
  }

  public void putCommand(InjectCommand injectCommand) {
    injectCommandList.add(injectCommand);
  }

  public boolean startExecuting() {
    for (InjectCommand command : injectCommandList) {
      command.startNotification();
      if (command.execute()) {
        command.endNotification();
        return true;
      } else {
        command.errorNotification();
        break;
      }
    }
    return false;
  }
}