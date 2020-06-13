package decompiletool.injector;

import java.io.*;
import java.util.ArrayList;

public class SmaliPostProcess {
  private final String toolPackage = "decompiletool/injector/packages/";
  private final String toolClassName = "decompiletool/injector/code/AppodealCode";
  private final File defaultSmaliFile;
  private final File modifiedSmaliFile;
  private final String userMainClassName;
  private final ArrayList<String> activityRequireMethods;

  public SmaliPostProcess(File smaliFile, String userMainClassName) {
    this.defaultSmaliFile = smaliFile;
    this.modifiedSmaliFile = new File("dexout/easytool/injector/code/ACModified.smali");
    this.userMainClassName = userMainClassName;

    this.activityRequireMethods = new ArrayList<>();
    fillActivityMethods();
  }

  private void fillActivityMethods() {
    activityRequireMethods.add("initialize");
    activityRequireMethods.add("cache");
    activityRequireMethods.add("show");
    activityRequireMethods.add("hide");
    activityRequireMethods.add("onResume");
    activityRequireMethods.add("startTestActivity");
    activityRequireMethods.add("trackInAppPurchase");
    activityRequireMethods.add("disableNetwork");
  }

  public File modifySmaliCode() {
    if (defaultSmaliFile == null) return null;
    try {
      if (modifiedSmaliFile.exists()) {
        modifiedSmaliFile.delete();
      }
      if (modifiedSmaliFile.createNewFile()) {
        if (formatSmaliFile()) {
          return modifiedSmaliFile;
        } else {
          System.out.println("В процессе удаления пакета easytool.injector.packages произошли проблемы");
        }
      } else {
        System.out.println("В процессе создания файла хранения модфицированного smali кода произошли проблемы");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return modifiedSmaliFile;
  }

  /**
   * Метод записывает в отдельный файл injectAppodealCode метод с применением модификаций
   * - удаление все лишнего кроме метода injectAppodealCode
   * - смена названия класса
   * - смена импортов для Activity и Appodeal SDK
   * - подставление Activity приложения вместо null
   *
   * @return true - если форматирование файла произошло успешно, иначе false
   */
  private boolean formatSmaliFile() {
    BufferedReader bufferedReader;
    FileOutputStream outStream;
    try {
      bufferedReader = new BufferedReader(new FileReader(defaultSmaliFile));
      outStream = new FileOutputStream(modifiedSmaliFile);
      String smaliLine;
      boolean methodStarted = false;

      while ((smaliLine = bufferedReader.readLine()) != null) {
        StringBuilder finalString = new StringBuilder();
        if (methodStarted && smaliLine.equals(".end method")) {
          finalString.append(smaliLine);
          pushData(outStream, finalString.toString());
          bufferedReader.close();
          outStream.close();
          return true;
        }

        if (smaliLine.startsWith(".method public injectAppodealCode")) {
          methodStarted = true;
        }

        if (methodStarted) {
          if (smaliLine.contains(toolPackage) && smaliLine.contains(toolClassName)) {
            finalString.append(smaliLine.replace(toolPackage, "").replace(toolClassName, userMainClassName));
          } else if (smaliLine.contains(toolPackage)) {
            finalString.append(smaliLine.replace(toolPackage, ""));
          } else if (smaliLine.contains(toolClassName)) {
            finalString.append(smaliLine.replace(toolClassName, userMainClassName));
          } else {
            finalString.append(smaliLine);
          }

          boolean isActivityRequired = false;
          for (String method : activityRequireMethods) {
            if (finalString.toString().contains(method)) {
              finalString.append("\n");
              pushData(outStream, finalString.toString().replace(findTargetRegister(finalString.toString()), "p0"));
              isActivityRequired = true;
              break;
            }
          }

          if (!isActivityRequired) {
            finalString.append("\n");
            pushData(outStream, finalString.toString());
          }
        }
      }
      outStream.close();
      bufferedReader.close();
      return true;
    } catch (IOException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private void pushData(FileOutputStream outStream, String data) {
    if (outStream != null) {
      try {
        outStream.write(data.getBytes());
        outStream.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String findTargetRegister(String codeLine) {
    StringBuilder targetRegister = new StringBuilder();
    char[] codeLineChars = codeLine.toCharArray();

    boolean isRequiredRegister = false;
    for (char codeSymbol : codeLineChars) {
      if (codeSymbol == '{') {
        isRequiredRegister = true;
        continue;
      }

      if ((codeSymbol == ',' || codeSymbol == '}') && isRequiredRegister) {
        break;
      }

      if (isRequiredRegister) {
        targetRegister.append(codeSymbol);
      }
    }

    return targetRegister.toString();
  }
}
