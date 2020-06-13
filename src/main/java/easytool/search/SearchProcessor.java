package easytool.search;

import easytool.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SearchProcessor {
  private final ConcurrentHashMap<String, List<String>> appodealSources;

  private final JavaChecker javaChecker;
  private int codeCounter;
  private int classCounter;

  public SearchProcessor() {
    appodealSources = new ConcurrentHashMap<>();
    javaChecker = new JavaChecker();
    codeCounter = 0;
    classCounter = 0;
  }

  public boolean searchAppodeal() {
    long startTime = System.currentTimeMillis();
    File[] sources = new File(Utils.getInstance().getSourcesPath()).listFiles();
    if (sources != null) {
      checkSources(sources);
    } else {
      return false;
    }
    long stopTime = System.currentTimeMillis();
    System.out.println("Затраченное время: " + (stopTime - startTime));
    System.out.println("Количество классов: " + classCounter);
    System.out.println("Количество строк кода: " + codeCounter / AppodealUtils.getInstance().getAppodealMethods().size());
    return true;
  }

  private void checkSources(File[] sources) {
    String classFormat = Utils.getInstance().getClassFormat();
    for (File source : sources) {
      if (source.isDirectory()) {
        File[] files = source.listFiles();
        if (files != null) {
          if (files.length > 0) {
            checkSources(files);
          }
        }
      } else if (source.getName().endsWith(classFormat)) {
        classCounter++;
        javaChecker.checkJavaFile(source);
      }
    }
  }

  public ConcurrentHashMap<String, List<String>> getAppodealSources() {
    return appodealSources;
  }

  private class JavaChecker {
    public JavaChecker() {

    }

    public void checkJavaFile(File javaFile) {
      String path = javaFile.getAbsolutePath();
      if (validatePackage(path)) {
        List<String> appodealMethods = new ArrayList<>();
        for (String appodealMethod : AppodealUtils.getInstance().getAppodealMethods()) {
          if (findMethod(appodealMethod, path)) {
            appodealMethods.add(appodealMethod);
          }
        }
        if (!appodealMethods.isEmpty()) {
          appodealSources.put(path, appodealMethods);
        }
      }
    }

    private boolean validatePackage(String path) {
      final String[] excludedPackages = Utils.getInstance().getExcludedFiles();
      for (String exPackage : excludedPackages) {
        if (path.contains(exPackage)) {
          return false;
        }
      }
      return true;
    }

    private boolean findMethod(String method, String fileName) {
      BufferedReader sourceReader;
      try {
        sourceReader = new BufferedReader(new FileReader(fileName));
        String codeLine;
        while ((codeLine = sourceReader.readLine()) != null) {
          codeCounter++;
          if (codeLine.contains(method)) {
            sourceReader.close();
            return true;
          }
        }
        sourceReader.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      return false;
    }
  }
}
