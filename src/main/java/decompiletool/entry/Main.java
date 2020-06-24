package decompiletool.entry;

import decompiletool.Utils;
import decompiletool.cmd.*;
import decompiletool.manifest.ModifyProcess;
import decompiletool.predictor.PredictProcess;
import decompiletool.ArgsParser;
import decompiletool.search.GetSourceProcess;
import decompiletool.search.SearchProcess;
import decompiletool.search.SearchProcessor;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0) {
      return;
    }
    ArgsParser argsParser = new ArgsParser(args);

    Utils.getInstance().setApkName(args[0]);
    Utils.getInstance().setSystemName(System.getProperty("os.name"));
    Utils.getInstance().setXmlFolderPath("/res/xml");
    Utils.getInstance().setAndroidManifestPath("/AndroidManifest.xml");

    TerminalProcessor terminalProcessor = new TerminalProcessor();
    CommandInvoker invoker = new CommandInvoker();
    invoker.putCommand(new DecompileProcess(terminalProcessor));

    if (!argsParser.isDecompileOnly()) {
      if (argsParser.isPredictRequired()) {
        invoker.putCommand(new PredictProcess());
      }
      invoker.putCommand(new ModifyProcess(argsParser.isInjectorRequired()));
      invoker.putCommand(new CompileProcess(terminalProcessor));
      invoker.putCommand(new SignProcess(terminalProcessor));
      if (!argsParser.isInstallRequired()) {
        invoker.putCommand(new InstallProcess(terminalProcessor));
      }
    }

    if (argsParser.isCodeSearcherRequired()) {
      invoker.putCommand(new GetSourceProcess(terminalProcessor));
      invoker.putCommand(new SearchProcess(new SearchProcessor()));
    }
    invoker.startExecuting();
  }
}