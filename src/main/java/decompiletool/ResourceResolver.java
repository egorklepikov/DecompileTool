package decompiletool;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.attribute.PosixFilePermission.*;

public class ResourceResolver {
  private static ResourceResolver instance;
  private HashMap<String, File> resources = new HashMap<>();

  private ResourceResolver() {
    cleanDirectory();
    if (createDirectory()) {
      loadResources(
          "adb",
          "apd-analyzer-0.1.0-beta.jar",
          "apktool.bat",
          "apktool-osx",
          "apktool-linux",
          "debug.keystore",
          "dx",
          "apktool.jar"
      );
      grantPermissions();
    } else {
      throw new RuntimeException("Unable to create cliTools directory.");
    }
  }

  private void grantPermissions() {
    for (Map.Entry<String, File> resource : resources.entrySet()) {
      try {
        Files.setPosixFilePermissions(
            resource.getValue().toPath(),
            EnumSet.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, GROUP_EXECUTE));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean createDirectory() {
    return new File("cliTools").mkdir();
  }

  private void cleanDirectory() {
    try {
      File cliToolsDirectory = new File("cliTools");
      if (cliToolsDirectory.isDirectory()) {
        FileUtils.deleteDirectory(cliToolsDirectory);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadResources(String... toolNames) {
    ClassLoader classLoader = ResourceResolver.class.getClassLoader();
    for (String toolName : toolNames) {
      try {
        InputStream resourceInputStream = classLoader.getResourceAsStream(toolName);
        if (resourceInputStream != null) {
          File resourceFile = new File("cliTools/" + toolName);
          FileUtils.copyInputStreamToFile(resourceInputStream, resourceFile);
          resources.put(toolName, resourceFile);
        }
      } catch (IOException | NullPointerException e) {
        e.printStackTrace();
      }
    }
  }

  public String resolve(String command) {
    StringBuilder resultCommand = new StringBuilder();
    String[] attrs = command.split(" ");

    resultCommand.append(resources.get(attrs[0]).getAbsolutePath());
    resultCommand.append(" ");

    for (int index = 0; index < attrs.length; index++) {
      if (index == 0) {
        continue;
      }
      resultCommand.append(attrs[index]).append(" ");
    }
    return resultCommand.toString();
  }

  public static ResourceResolver getInstance() {
    if (instance == null) {
      instance = new ResourceResolver();
    }
    return instance;
  }
}
