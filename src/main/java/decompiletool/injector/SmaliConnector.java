package decompiletool.injector;

import java.io.*;
import java.util.ArrayList;

public class SmaliConnector {
  private final File userSmali;
  private final File appodealSmali;
  private final ArrayList<String> appodealCode;
  private final String userMainClassName;

  public SmaliConnector(File userSmali, File appodealSmali, String userMainClassName) {
    this.userSmali = userSmali;
    this.appodealSmali = appodealSmali;
    this.appodealCode = new ArrayList<>();
    this.userMainClassName = userMainClassName;
    fillBuilder();
  }

  private void fillBuilder() {
    BufferedReader bufferedReader;
    try {
      bufferedReader = new BufferedReader(new FileReader(appodealSmali));
      String codeLine;
      while ((codeLine = bufferedReader.readLine()) != null) {
        appodealCode.add(codeLine);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean connect() {
    return insertAppodealCode() && insertAppodealCall();
  }

  /**
   * Если инжектирование было вызвано несколько раз для одного и того же АПК то будет баг.
   */
  private boolean insertAppodealCode() {
    FileWriter writer = null;
    BufferedWriter bufferWriter;
    try {
      writer = new FileWriter(userSmali, true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (writer != null) {
      bufferWriter = new BufferedWriter(writer);
    } else {
      return false;
    }
    try {
      bufferWriter.append("\n");
      for (String codeLine : appodealCode) {
        bufferWriter.append(codeLine).append("\n");
        bufferWriter.flush();
      }
      writer.close();
      bufferWriter.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }

  private boolean insertAppodealCall() {
    File userSmaliTemp = createTempSmali();
    if (userSmaliTemp == null) {
      return false;
    }

    if (fillTempSmali(userSmaliTemp)) {
      return renameTempSmali(userSmaliTemp);
    } else {
      return false;
    }
  }

  private boolean renameTempSmali(File userSmaliTemp) {
    return userSmaliTemp.renameTo(userSmali);
  }

  private boolean fillTempSmali(File userSmaliTemp) {
    try {
      FileOutputStream userSmaliTempStream = new FileOutputStream(userSmaliTemp);
      BufferedReader smaliReader = new BufferedReader(new FileReader(userSmali));

      String smaliLine;
      while ((smaliLine = smaliReader.readLine()) != null) {
        userSmaliTempStream.write("\n".getBytes());
        userSmaliTempStream.write(smaliLine.getBytes());

        if (smaliLine.contains("setContentView")) {
          userSmaliTempStream.write("\n\n".getBytes());
          userSmaliTempStream.write(("\t" + buildcall()).getBytes());
        }

        userSmaliTempStream.flush();
      }
      userSmaliTempStream.close();
      smaliReader.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  private File createTempSmali() {
    File userSmaliTemp = new File(userSmali.getAbsolutePath().replace(".smali", "") + "Temp.smali");
    if (!userSmaliTemp.exists()) {
      try {
        if (userSmaliTemp.createNewFile()) {
          return userSmaliTemp;
        } else {
          return null;
        }
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return userSmaliTemp;
    }
  }

  private String buildcall() {
    return "invoke-virtual {p0}, L" + userMainClassName + ";->injectAppodealCode()V";
  }
}
