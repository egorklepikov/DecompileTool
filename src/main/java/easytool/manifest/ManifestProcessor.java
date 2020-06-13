package easytool.manifest;

import easytool.Utils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManifestProcessor {
  private final String networkConfigContent =
      "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n" +
          "<network-security-config>\n" +
          "    <base-config cleartextTrafficPermitted=\"true\">\n" +
          "        <trust-anchors>\n" +
          "            <certificates src=\"system\"/>\n" +
          "            <certificates src=\"user\"/>\n" +
          "        </trust-anchors>\n" +
          "    </base-config>\n" +
          "</network-security-config>";
  protected Document androidManifest;
  protected boolean isManifestInitialized;
  private Document networkConfig;
  private Element application;

  public ManifestProcessor() {
    isManifestInitialized = false;
  }

  public void initializeAndroidManifest() {
    if (!isManifestInitialized) {
      androidManifest = openDocument(Utils.getInstance().getAndroidManifestPath());
      application = (Element) androidManifest.getElementsByTagName("application").item(0);
      isManifestInitialized = true;
    }
  }

  private Document openDocument(String path) {
    DocumentBuilder documentBuilder;
    Document document = null;
    try {
      documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      document = documentBuilder.parse(path);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      e.printStackTrace();
    }

    return document;
  }

  private void updateManifest(Document document, String path) {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer;
    try {
      transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(new File(path));
      transformer.transform(domSource, streamResult);
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  public boolean addDebugMode() {
    Attr debuggable = application.getAttributeNode("android:debuggable");
    if (debuggable == null) {
      application.setAttribute("android:debuggable", "true");
      System.out.println("Debug mode was added");
    } else {
      if (debuggable.getValue().equals("false")) {
        debuggable.setValue("true");
        System.out.println("Debug Mode state was changed");
      }
    }
    updateManifest(androidManifest, Utils.getInstance().getAndroidManifestPath());

    return true;
  }

  public boolean addNetworkSecurityConfig() {
    addNetworkConfigToManifest();
    if (isNetworkSecurityConfigAlreadyExist()) {
      networkConfig = openDocument(Utils.getInstance().getNetworkSecurityConfigPath());
      if (isNetworkConfigMatched(Utils.getInstance().getNetworkSecurityConfigPath())) {
        System.out.println("network_security_config is matched");
        changeNetworkSecurityConfigFile();
      } else {
        System.out.println("network_security_config is not matched. Check it manually and ask the customer to fix it");
        if (removeNetworkSecurityConfig()) {
          createNetworkSecurityConfigFile();
        } else {
          System.out.println("An error occurred during adding the network_security_config");
        }
      }
    } else {
      createNetworkSecurityConfigFile();
    }
    return true;
  }

  private boolean removeNetworkSecurityConfig() {
    File config = new File(Utils.getInstance().getNetworkSecurityConfigPath());
    if (config.exists()) {
      boolean isRemoved = config.delete();
      if (isRemoved) {
        System.out.println("network_security_config was removed");
      } else {
        System.out.println("An error occurred during removing the network_security_config");
      }
      return isRemoved;
    } else {
      return true;
    }
  }

  private void addNetworkConfigToManifest() {
    Attr networkConfig = application.getAttributeNode("android:networkSecurityConfig");
    if (networkConfig != null) {
      String configName = networkConfig.getValue().replaceAll("@xml/", "").replace(".xml", "");
      //В некоторых случаях при декомпиляции имя конфига в манифесте распознается некорректно
      if (configName.equals("@null")) {
        networkConfig.setValue("@xml/" + Utils.getInstance().getNetworkSecurityConfigFileName());
      } else {
        Utils.getInstance().setNetworkSecurityConfigFileName(configName);
      }
      System.out.println("NetworkSecurityConfig file name: " + configName);
    } else {
      application.setAttribute("android:networkSecurityConfig", "@xml/" + Utils.getInstance().getNetworkSecurityConfigFileName());
      System.out.println("NetworkSecurityConfig file name: " + Utils.getInstance().getNetworkSecurityConfigFileName());
    }

    updateManifest(androidManifest, Utils.getInstance().getAndroidManifestPath());
    Utils.getInstance().correctNetworkSecurityConfigPath();
  }

  private void createNetworkSecurityConfigFile() {
    File xmlDir = new File(Utils.getInstance().getXmlFolderPath());
    String[] files = xmlDir.list();
    if (files == null) {
      if (!xmlDir.mkdir()) {
        return;
      }
    }
    try {
      PrintWriter networkConfigFile = new PrintWriter(Utils.getInstance().getNetworkSecurityConfigPath(), "UTF-8");
      networkConfigFile.println(networkConfigContent);
      networkConfigFile.close();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    System.out.println("network_security_config was added");
  }

  private void changeNetworkSecurityConfigFile() {
    Node trustAnchors = networkConfig.getElementsByTagName("trust-anchors").item(0);

    Element userCertificates = networkConfig.createElement("certificates");
    userCertificates.setAttribute("src", "user");
    trustAnchors.appendChild(userCertificates);
    updateManifest(networkConfig, Utils.getInstance().getNetworkSecurityConfigPath());
    System.out.println("network_security_config was changed");
  }

  private boolean isNetworkSecurityConfigAlreadyExist() {
    boolean isNetworkConfigFileExist = false;
    File xmlDir = new File(Utils.getInstance().getXmlFolderPath());
    String[] files = xmlDir.list();
    if (files != null) {
      for (String file : files) {
        if (file.equals(Utils.getInstance().getNetworkSecurityConfigFileName() + ".xml")) {
          isNetworkConfigFileExist = true;
          System.out.println("network_security_config already exist");
        }
      }
    }
    return isNetworkConfigFileExist;
  }

  private boolean isNetworkConfigMatched(String networkConfigPath) {
    List<String> requiredAttrs = new ArrayList<>();
    requiredAttrs.add("<network-security-config>");
    requiredAttrs.add("</network-security-config>");
    requiredAttrs.add("<base-config cleartextTrafficPermitted=true>");
    requiredAttrs.add("<trust-anchors>");
    requiredAttrs.add("</trust-anchors>");
    requiredAttrs.add("</base-config>");

    int matchedCount = 0;
    int requiredCount = requiredAttrs.size() - 1;

    BufferedReader networkReader;
    try {
      networkReader = new BufferedReader(new FileReader(networkConfigPath));
      String networkConfigLine;
      while ((networkConfigLine = networkReader.readLine()) != null) {
        for (String attr : requiredAttrs) {
          if (networkConfigLine.contains(attr)) {
            matchedCount++;
            requiredAttrs.remove(attr);
            break;
          }
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return matchedCount == requiredCount;
  }

  public boolean addWriteExternalStorage() {
    if (!isWriteExternalStorageAlreadyExist()) {
      addWriteExternalStoragePermission();
    }
    return true;
  }

  private void addWriteExternalStoragePermission() {
    Node manifest = androidManifest.getDocumentElement();

    Element storagePermission = androidManifest.createElement("uses-permission");
    storagePermission.setAttribute("android:name", "android.permission.WRITE_EXTERNAL_STORAGE");
    manifest.appendChild(storagePermission);
    updateManifest(androidManifest, Utils.getInstance().getAndroidManifestPath());
    System.out.println("WRITE_EXTERNAL_STORAGE was added");
  }

  private boolean isWriteExternalStorageAlreadyExist() {
    boolean isStoragePermissionExist = false;
    NodeList usesPermissions = androidManifest.getElementsByTagName("uses-permission");
    for (int permissionNumber = 0; permissionNumber < usesPermissions.getLength(); permissionNumber++) {
      NamedNodeMap attributes = usesPermissions.item(permissionNumber).getAttributes();
      Node name = attributes.getNamedItem("android:name");
      if (name.getNodeValue().equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
        Node maxSdkVersion = attributes.getNamedItem("android:maxSdkVersion");
        if (removeMaxSdkVersion(attributes, maxSdkVersion)) {
          updateManifest(androidManifest, Utils.getInstance().getAndroidManifestPath());
        }
        isStoragePermissionExist = true;
        System.out.println("WRITE_EXTERNAL_STORAGE already exist");
        break;
      }
    }
    return isStoragePermissionExist;
  }

  private boolean removeMaxSdkVersion(NamedNodeMap attributes, Node maxSdkVersion) {
    boolean isRemoveNeed;
    if (maxSdkVersion == null) {
      isRemoveNeed = false;
    } else {
      isRemoveNeed = true;
      attributes.removeNamedItem(maxSdkVersion.getNodeName());
      System.out.println("maxSdkVersion attr was removed");
    }

    return isRemoveNeed;
  }
}
