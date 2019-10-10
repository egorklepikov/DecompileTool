package easytool.injector;

import easytool.Utils;
import easytool.manifest.ManifestProcessor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;

public class SmaliSearcher extends ManifestProcessor {
    private File smaliFile;

    public SmaliSearcher() {
        smaliFile = null;
        if (!isManifestInitialized) {
            initializeAndroidManifest();
        }
    }

    public File getSmali() {
        String smaliPath;
        File[] apkFiles = new File(Utils.getInstance().getApkName().toString()).listFiles();
        if (apkFiles != null) {
            smaliPath = getSmaliPath();
            for (File file : apkFiles) {
                if (file.getName().contains("smali")) {
                    smaliFile = new File(file.getAbsolutePath() + "/" + smaliPath);
                    if (smaliFile.exists()) {
                        break;
                    }
                }
            }
        }

        return smaliFile;
    }

    private String getSmaliPath() {
        String activityPath = null;
        NodeList activities = androidManifest.getElementsByTagName("activity");
        for (int activityIndex = 0; activityIndex < activities.getLength(); activityIndex++) {
            if (checkActivity(activities.item(activityIndex))) {
                activityPath = activities.item(activityIndex).getAttributes().getNamedItem("android:name").getNodeValue();
            }
        }

        return (activityPath != null ? activityPath.replace(".", "/") : null) + ".smali";
    }

    private boolean checkActivity(Node activity) {
        int attrsCount = 0;
        NodeList activityAttrs = activity.getChildNodes();
        for (int attrNumber = 0; attrNumber < activityAttrs.getLength(); attrNumber++) {
            if (activityAttrs.item(attrNumber).getNodeName().equals("intent-filter")) {
                NodeList intentFilterAttrs = activityAttrs.item(attrNumber).getChildNodes();
                if (intentFilterAttrs != null) {
                    for (int intentChild = 0; intentChild < intentFilterAttrs.getLength(); intentChild++) {
                        Node intentNode = intentFilterAttrs.item(intentChild);
                        if (intentNode.getNodeName().equals("action") || intentNode.getNodeName().equals("category")) {
                            NamedNodeMap actionAttrs = intentFilterAttrs.item(intentChild).getAttributes();
                            if (actionAttrs != null) {
                                if (actionAttrs.getNamedItem("android:name").getNodeValue().equals("android.intent.action.MAIN")) {
                                    attrsCount++;
                                } else if (actionAttrs.getNamedItem("android:name").getNodeValue().equals("android.intent.category.LAUNCHER")) {
                                    attrsCount++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return attrsCount == 2;
    }

    public String getUserMainClassName () {
        if (smaliFile == null) return null;

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(smaliFile));
            String codeLine;
            while ((codeLine = bufferedReader.readLine()) != null) {
                if (codeLine.contains(".class")) {
                    bufferedReader.close();
                    return findMainClass(codeLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String findMainClass(String codeLine) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] codeLineChars = codeLine.toCharArray();
        boolean isMainClassPlace = false;

        for (char codeSymbol: codeLineChars) {
            if (codeSymbol == ';' && isMainClassPlace) {
                break;
            }

            if (codeSymbol == 'L' && !isMainClassPlace) {
                isMainClassPlace = true;
                continue;
            }

            if (isMainClassPlace) {
                stringBuilder.append(codeSymbol);
            }
        }

        return stringBuilder.toString();
    }
}
