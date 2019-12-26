package easytool.cmd;

import easytool.CmdProcessor;
import easytool.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TerminalProcessor extends CmdProcessor {
    private String apkToolBuild;
    private String apkToolDecompile;
    private String signApk;
    private String installApk;
    private String jadXDecompile;
    private String ilSpyDecompile;
    private String installILSpy;

    public TerminalProcessor() {
        boolean isMacOS = Utils.getInstance().isMacOS();
        String prefix = Utils.getInstance().getPrefix();

        String decompileCommand = isMacOS ? "apktool-osx d -f " : "apktool.bat d -f ";
        String compileCommand = isMacOS ? "apktool-osx b " : "apktool.bat b ";
        String signApkCommand = "jarsigner -keystore debug.keystore -storepass android -keypass android " +
                prefix + Utils.getInstance().getApkName() + "/dist/" + Utils.getInstance().getFullApkName() + " androiddebugkey";

        apkToolDecompile = prefix + decompileCommand + prefix + Utils.getInstance().getFullApkName();
        apkToolBuild = prefix + compileCommand + prefix + Utils.getInstance().getApkName();
        signApk = isMacOS ? signApkCommand : prefix + signApkCommand;
        installApk = prefix + "adb install " + prefix + Utils.getInstance().getApkName() + "/dist/" + Utils.getInstance().getFullApkName();
        jadXDecompile = "jadx/bin/jadx --no-debug-info -d out " + Utils.getInstance().getFullApkName();
        installILSpy = "dotnet tool install ilspycmd -g";
    }

    public boolean decompileApk() {
        return processCmdCommand(apkToolDecompile);
    }

    public boolean compileApk() {
        return processCmdCommand(apkToolBuild);
    }

    public boolean signApk() {
        return processCmdCommand(signApk);
    }

    public boolean installApk() {
        return processCmdCommand(installApk);
    }

    public boolean decompileApkJadX() {
        try {
            FileUtils.deleteDirectory(new File(Utils.getInstance().getSourcesPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processCmdCommand(jadXDecompile);
    }

    public boolean decompileApkIlSpy() {
        ilSpyDecompile = "ilspycmd " + Utils.getInstance().getDllFileFullPath() + " -o " + Utils.getInstance().getSourcesPath() + " -p";
        if (processCmdCommand(installILSpy)) {
            createSourcesDir();
            return (processCmdCommand(ilSpyDecompile));
        } else {
            System.out.println("Проверьте что .NET Core установлен корректно");
            return false;
        }
    }

    private boolean createSourcesDir() {
        String path = Utils.getInstance().getSourcesPath();
        File dir = new File(path);
        if (dir.exists()) {
            try {
                FileUtils.deleteDirectory(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dir.mkdir();
    }
}
