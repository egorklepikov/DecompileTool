package decompiletool.network;

import decompiletool.cmd.Command;

import java.util.ArrayList;
import java.util.List;

public class PreparationInvoker {
  private final List<Command> commandsList;
  private boolean isReady = true;

  public PreparationInvoker() {
    commandsList = new ArrayList<>();
  }

  public void putCommand(Command cmdCommand) {
    commandsList.add(cmdCommand);
  }

  public void startExecuting() {
    for (Command command : commandsList) {
      command.startNotification();
      if (command.execute()) {
        command.endNotification();
      } else {
        command.errorNotification();
        isReady = false;
        break;
      }
    }
  }

  public boolean isApplicationReady() {
    return isReady;
  }
}
