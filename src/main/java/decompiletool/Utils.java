package decompiletool;

import java.io.File;

public class Utils {
  private static final Utils ourInstance = new Utils();
  private String apkName;
  private String fullApkName;
  private String xmlFolderPath;
  private String androidManifestPath;
  private String networkSecurityConfigPath;
  private String networkSecurityConfigFileName = "network_security_config";
  private String sourcesPath;
  private String framework;
  private boolean isMacOS;

  private Utils() {
  }

  public static Utils getInstance() {
    return ourInstance;
  }

  public String getApkName() {
    return apkName;
  }

  public void setApkName(String apkName) {
    this.fullApkName = apkName;
    this.apkName = buildApkName(apkName);
  }

  public String getSourcesPath() {
    String framework = getFramework();
    if (framework.equals("Android")) {
      sourcesPath = "out/sources";
    } else if (framework.equals("Unity")) {
      sourcesPath = "unity_out";
    }
    return sourcesPath;
  }

  public String getFullApkName() {
    return fullApkName;
  }

  public String getXmlFolderPath() {
    return xmlFolderPath;
  }

  public void setXmlFolderPath(String xmlFolderPath) {
    this.xmlFolderPath = apkName + xmlFolderPath;
  }

  public String getAndroidManifestPath() {
    return androidManifestPath;
  }

  public void setAndroidManifestPath(String androidManifestPath) {
    this.androidManifestPath = apkName + androidManifestPath;
  }

  public void setSystemName(String systemName) {
    isMacOS = systemName.contains("Mac OS");
  }

  public String getNetworkSecurityConfigPath() {
    return networkSecurityConfigPath;
  }

  public void correctNetworkSecurityConfigPath() {
    this.networkSecurityConfigPath = apkName + "/res/xml/" + networkSecurityConfigFileName + ".xml";
  }

  public String getNetworkSecurityConfigFileName() {
    return networkSecurityConfigFileName;
  }

  public void setNetworkSecurityConfigFileName(String networkSecurityConfigFileName) {
    this.networkSecurityConfigFileName = networkSecurityConfigFileName;
  }

  public String getClassFormat() {
    if (framework == null) {
      framework = getFrameworkName();
    }
    if (framework.equals("Android")) {
      return ".java";
    } else if (framework.equals("Unity")) {
      return ".cs";
    }
    return null;
  }

  public String getFramework() {
    if (framework == null) {
      framework = getFrameworkName();
    }
    return framework;
  }

  private String getFrameworkName() {
    String frameworkName = null;
    File unityFolder = new File(getManagedFolderPath());
    File[] dllFiles = unityFolder.listFiles();
    if (dllFiles != null) {
      boolean isIL2CPPEnabled = false;
      for (File dll : dllFiles) {
        if (dll.getName().contains(getDllName())) {
          frameworkName = "Unity";
          isIL2CPPEnabled = false;
          break;

        } else {
          isIL2CPPEnabled = true;
        }
      }
      if (isIL2CPPEnabled) {
        frameworkName = "Unity(IL2CPP)";
      }
    } else {
      frameworkName = "Android";
    }
    return frameworkName;
  }

  private String getDllName() {
    return "Assembly-CSharp.dll";
  }

  private String getManagedFolderPath() {
    return apkName + "/assets/bin/Data/Managed/";
  }

  public boolean isMacOS() {
    return isMacOS;
  }

  private String buildApkName(String path) {
    StringBuilder apkName = new StringBuilder();
    String[] pathElements = path.split("/");
    String apkNameWithExtension = pathElements[pathElements.length - 1];

    char[] apkNameChars = apkNameWithExtension.toCharArray();
    for (char apkNameChar : apkNameChars) {
      if (apkNameChar != '.') {
        apkName.append(apkNameChar);
      } else {
        break;
      }
    }

    return apkName.toString();
  }

  public String getDllFileFullPath() {
    return getManagedFolderPath() + getDllName();
  }

  public String[] getExcludedFiles() {
    if (framework == null) {
      framework = getFramework();
    }
    if (framework.equals("Android")) {
      return new String[]{"com/appodeal/ads", "com/appodeal/iab", "com/appodeal/sdk"};
    } else if (framework.equals("Unity")) {
      return new String[]{"DummyClient.cs"};
    } else {
      return new String[]{""};
    }
  }
}
