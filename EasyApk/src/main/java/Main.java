import easytool.ArgsParser;
import easytool.Utils;
import easytool.cmd.*;
import easytool.manifest.ModifyProcess;
import easytool.search.GetSourceProcess;
import easytool.search.SearchProcess;
import easytool.search.SearchProcessor;

import java.io.*;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        try {
            String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath().replace("EasyApk.jar", "");
            Utils.getInstance().setJarPath(jarPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ArgsParser argsParser = new ArgsParser(args);

        Utils.getInstance().setApkName(args[0]);
        Utils.getInstance().setNetworkSecurityConfigFileName("network_security_config");
        Utils.getInstance().setSystemName(System.getProperty("os.name"));
        Utils.getInstance().setXmlFolderPath("/res/xml");
        Utils.getInstance().setAndroidManifestPath("/AndroidManifest.xml");

        TerminalProcessor terminalProcessor = new TerminalProcessor();
        CommandInvoker invoker = new CommandInvoker();
        invoker.putCommand(new DecompileProcess(terminalProcessor));

        if (!argsParser.isDecompileOnly()) {
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