package easytool.manifest;

import java.util.ArrayList;
import java.util.List;

public class ManifestInvoker {
  private final List<ManifestCommand> manifestCommands;

  public ManifestInvoker() {
    manifestCommands = new ArrayList<>();
  }

  public void putCommand(ManifestCommand manifestCommand) {
    manifestCommands.add(manifestCommand);
  }

  public boolean startExecuting() {
    boolean executingResult = false;
    for (ManifestCommand manifestCommand : manifestCommands) {
      manifestCommand.initialize();
      executingResult = manifestCommand.execute();
    }
    return executingResult;
  }
}
