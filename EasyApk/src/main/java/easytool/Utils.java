package easytool;

import java.io.File;

public class Utils {

    private StringBuilder apkName;
    private String fullApkName;
    private String jarPath;
    private String xmlFolderPath;
    private String androidManifestPath;
    private String systemName;
    private String networkSecurityConfigPath;
    private String networkSecurityConfigFileName;
    private String prefix;
    private String sourcesPath;
    private String framework;

    private boolean isMacOS;

    private static Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }

    public StringBuilder getApkName() {
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

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getXmlFolderPath() {
        return xmlFolderPath;
    }

    public void setXmlFolderPath(String xmlFolderPath) {
        this.xmlFolderPath = prefix + apkName + xmlFolderPath;
    }

    public String getAndroidManifestPath() {
        return androidManifestPath;
    }

    public void setAndroidManifestPath(String androidManifestPath) {
        this.androidManifestPath = prefix + apkName + androidManifestPath;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        isMacOS = systemName.contains("Mac OS");
        prefix = getPrefix();
        this.systemName = systemName;
    }

    public String getNetworkSecurityConfigPath() {
        return networkSecurityConfigPath;
    }

    public void correctNetworkSecurityConfigPath() {
        this.networkSecurityConfigPath = prefix + apkName + "/res/xml/" + networkSecurityConfigFileName + ".xml";
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

    public String getPrefix() {
        return isMacOS ? "./" : jarPath;
    }

    private StringBuilder buildApkName(String path) {
        StringBuilder apkName = new StringBuilder();
        char[] seq = path.toCharArray();
        for (char ch : seq) {
            if (ch != '.') {
                apkName.append(ch);
            } else {
                //Если в названии будет несколько точек то поломается.
                break;
            }
        }
        return apkName;
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
