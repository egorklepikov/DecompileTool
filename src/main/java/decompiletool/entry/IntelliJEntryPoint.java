package decompiletool.entry;

import decompiletool.Utils;
import decompiletool.cmd.*;
import decompiletool.manifest.ModifyProcess;
import decompiletool.network.GetApkProcess;
import decompiletool.network.PreparationInvoker;

/**
 * Case 1:
 * args[0] = full APK path
 * args[1] = target path
 * ----------------------
 * Case 2:
 * args[0] = bundle id
 * args[1] = app version
 * args[2] = target path
 */
public class IntelliJEntryPoint {
  public static void main(String[] args) {
    Utils.getInstance().setSystemName(System.getProperty("os.name"));

    PreparationInvoker preparationInvoker = new PreparationInvoker();
    preparationInvoker.putCommand(new GetApkProcess(args));
    preparationInvoker.startExecuting();

    if (preparationInvoker.isApplicationReady()) {
      TerminalProcessor terminalProcessor = new TerminalProcessor();
      CommandInvoker invoker = new CommandInvoker();
      invoker.putCommand(new DecompileProcess(terminalProcessor));
      invoker.putCommand(new ModifyProcess(false));
      invoker.putCommand(new CompileProcess(terminalProcessor));
      invoker.putCommand(new SignProcess(terminalProcessor));
      invoker.putCommand(new InstallProcess(terminalProcessor));
      invoker.startExecuting();
    }
  }
}
