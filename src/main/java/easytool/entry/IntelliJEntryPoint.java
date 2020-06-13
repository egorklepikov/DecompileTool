package easytool.entry;

import easytool.ArgsParser;
import easytool.Utils;
import easytool.cmd.*;
import easytool.manifest.ModifyProcess;

public class IntelliJEntryPoint {
  public static void main(String[] args) {
    ArgsParser argsParser = new ArgsParser(args);

    Utils.getInstance().setApkName(args[0]);
    Utils.getInstance().setNetworkSecurityConfigFileName("network_security_config");
    Utils.getInstance().setSystemName(System.getProperty("os.name"));
    Utils.getInstance().setXmlFolderPath("/res/xml");
    Utils.getInstance().setAndroidManifestPath("/AndroidManifest.xml");

    TerminalProcessor terminalProcessor = new TerminalProcessor();
    CommandInvoker invoker = new CommandInvoker();
    invoker.putCommand(new DecompileProcess(terminalProcessor));
    invoker.putCommand(new ModifyProcess(argsParser.isInjectorRequired()));
    invoker.putCommand(new CompileProcess(terminalProcessor));
    invoker.putCommand(new SignProcess(terminalProcessor));
    invoker.putCommand(new InstallProcess(terminalProcessor));
    invoker.startExecuting();
  }
}
