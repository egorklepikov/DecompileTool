package decompiletool.cmd;

import decompiletool.Utils;
import decompiletool.CmdProcessor;
import decompiletool.ResourceResolver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TerminalProcessor extends CmdProcessor {
  public TerminalProcessor() {
  }

  public boolean decompileApk() {
    StringBuilder decompileCommand = new StringBuilder();
    if (Utils.getInstance().isMacOS()) {
      decompileCommand.append("apktool-osx");
    } else {
      decompileCommand.append("apktool.bat");
    }
    decompileCommand
        .append(" d ").append(" -f ").append(Utils.getInstance().getFullApkName())
        .append(" -o ").append(Utils.getInstance().getTargetApkPath()).append("/").append(Utils.getInstance().getApkName());

    return processCmdCommand(decompileCommand.toString(), true);
  }

  public boolean compileApk() {
    StringBuilder compileCommand = new StringBuilder();
    if (Utils.getInstance().isMacOS()) {
      compileCommand.append("apktool-osx");
    } else {
      compileCommand.append("apktool.bat");
    }
    compileCommand
        .append(" b ").append(Utils.getInstance().getTargetApkPath())
        .append("/")
        .append(Utils.getInstance().getApkName());

    return processCmdCommand(compileCommand.toString(), true);
  }

  public boolean signApk() {
    StringBuilder signCommand = new StringBuilder();
    signCommand
        .append("jarsigner")
        .append(" -keystore ")
        .append(ResourceResolver.getInstance().resolve("debug.keystore"))
        .append("-storepass android -keypass android ")
        .append(Utils.getInstance().getTargetApkPath()).append("/")
        .append(Utils.getInstance().getApkName()).append("/dist/").append(Utils.getInstance().getApkName()).append(".apk ")
        .append(" androiddebugkey");

    return processCmdCommand(signCommand.toString(), false);
  }

  public boolean installApk() {
    StringBuilder installCommand = new StringBuilder();
    installCommand
        .append("adb install ")
        .append(Utils.getInstance().getTargetApkPath()).append("/")
        .append(Utils.getInstance().getApkName()).append("/dist/").append(Utils.getInstance().getApkName()).append(".apk");

    return processCmdCommand(installCommand.toString(), true);
  }

  public boolean decompileApkJadX() {
    try {
      FileUtils.deleteDirectory(new File(Utils.getInstance().getSourcesPath()));
      StringBuilder jadXCommand = new StringBuilder();
      jadXCommand
          .append("jadx/bin/jadx")
          .append(" --no-debug-info ")
          .append(" -d ")
          .append(" out ").append(Utils.getInstance().getFullApkName());

      return processCmdCommand(jadXCommand.toString(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean decompileApkIlSpy() {
    StringBuilder installILSpyCommand = new StringBuilder();
    installILSpyCommand
        .append("dotnet tool install ilspycmd")
        .append(" -g");

    if (processCmdCommand(installILSpyCommand.toString(), true)) {
      if (createSourcesDir()) {
        StringBuilder ilSpyDecompileCommand = new StringBuilder();
        ilSpyDecompileCommand
            .append("ilspycmd ")
            .append(Utils.getInstance().getDllFileFullPath())
            .append(" -o ")
            .append(Utils.getInstance().getSourcesPath())
            .append(" -p");

        return processCmdCommand(ilSpyDecompileCommand.toString(), true);
      } else {
        throw new RuntimeException("Unable to create folder for source code (iLSpy");
      }
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
