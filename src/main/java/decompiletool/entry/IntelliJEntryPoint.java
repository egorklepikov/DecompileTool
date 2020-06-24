package decompiletool.entry;

import decompiletool.Utils;
import decompiletool.cmd.*;
import decompiletool.manifest.ModifyProcess;

public class IntelliJEntryPoint {
  public static void main(String[] args) {
    Utils.getInstance().setApkName(args[0]);
    Utils.getInstance().setSystemName(System.getProperty("os.name"));
    Utils.getInstance().setXmlFolderPath("/res/xml");
    Utils.getInstance().setAndroidManifestPath("/AndroidManifest.xml");

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
