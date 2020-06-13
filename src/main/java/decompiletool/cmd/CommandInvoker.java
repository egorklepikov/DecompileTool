package decompiletool.cmd;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {
  private final List<Command> cmdCommandList;

  public CommandInvoker() {
    cmdCommandList = new ArrayList<>();
  }

  public void putCommand(Command cmdCommand) {
    cmdCommandList.add(cmdCommand);
  }

  public void startExecuting() {
    for (Command cmdCommand : cmdCommandList) {
      cmdCommand.startNotification();
      if (cmdCommand.execute()) {
        cmdCommand.endNotification();
      } else {
        cmdCommand.errorNotification();
        break;
      }
    }
  }
}
