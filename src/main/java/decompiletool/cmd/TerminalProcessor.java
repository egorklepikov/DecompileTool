package decompiletool.cmd;

import decompiletool.Utils;
import decompiletool.CmdProcessor;
import decompiletool.ResourceResolver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TerminalProcessor extends CmdProcessor {
  private final String apkToolBuild;
  private final String apkToolDecompile;
  private final String signApk;
  private final String installApk;
  private final String jadXDecompile;
  private String ilSpyDecompile;
  private final String installILSpy;

  public TerminalProcessor() {
    boolean isMacOS = Utils.getInstance().isMacOS();

    String decompileCommand = isMacOS ? "apktool-osx d -f " : "apktool.bat d -f ";
    String compileCommand = isMacOS ? "apktool-osx b " : "apktool.bat b ";

    String signApkCommand = "" +
        "jarsigner -keystore " +
        new ResourceResolver().resolve("debug.keystore") +
        "-storepass android -keypass android " +
        Utils.getInstance().getApkName() + "/dist/" + Utils.getInstance().getApkName() + ".apk " + " androiddebugkey";

    apkToolDecompile = decompileCommand + Utils.getInstance().getFullApkName();
    apkToolBuild = compileCommand + Utils.getInstance().getApkName();
    signApk = isMacOS ? signApkCommand : signApkCommand;
    installApk = "adb install " + Utils.getInstance().getApkName() + "/dist/" + Utils.getInstance().getApkName() + ".apk ";
    jadXDecompile = "jadx/bin/jadx --no-debug-info -d out " + Utils.getInstance().getFullApkName();
    installILSpy = "dotnet tool install ilspycmd -g";
  }

  public boolean decompileApk() {
    return processCmdCommand(apkToolDecompile, true);
  }

  public boolean compileApk() {
    return processCmdCommand(apkToolBuild, true);
  }

  public boolean signApk() {
    return processCmdCommand(signApk, false);
  }

  public boolean installApk() {
    return processCmdCommand(installApk, true);
  }

  public boolean decompileApkJadX() {
    try {
      FileUtils.deleteDirectory(new File(Utils.getInstance().getSourcesPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return processCmdCommand(jadXDecompile, true);
  }

  public boolean decompileApkIlSpy() {
    ilSpyDecompile = "ilspycmd " + Utils.getInstance().getDllFileFullPath() + " -o " + Utils.getInstance().getSourcesPath() + " -p";
    if (processCmdCommand(installILSpy, true)) {
      createSourcesDir();
      return (processCmdCommand(ilSpyDecompile, true));
    } else {
      System.out.println("Check that .NET Core is installed on your machine.");
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
