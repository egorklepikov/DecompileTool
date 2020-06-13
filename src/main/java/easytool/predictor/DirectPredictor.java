package easytool.predictor;

import easytool.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectPredictor implements Predictor {
  private BuildType buildType;
  private final List<String> problemList;
  private boolean isMultidexEnabled;
  private File[] apkFiles;
  private final List<String> notificationList;
  public DirectPredictor() {
    problemList = new ArrayList<>();
    notificationList = new ArrayList<>();
    isMultidexEnabled = false;
  }

  private void collectInformation() {
    apkFiles = new File(Utils.getInstance().getApkName()).listFiles();
    buildType = getBuildType();
    isMultidexEnabled = checkMultidex();
  }

  //TODO поискать другие способы определения
  private boolean checkMultidex() {
    int smaliNumbers = 0;
    for (File file : apkFiles) {
      if (file.getName().contains("smali")) {
        smaliNumbers++;
      }
    }
    return smaliNumbers > 1;
  }

  private BuildType getBuildType() {
    boolean isDexFolderExist = findDexFolder();
    if (isDexFolderExist) {
      return BuildType.MAXDEX;
    } else {
      return BuildType.NODEX;
    }
  }

  private boolean findDexFolder() {
    for (File file : apkFiles) {
      if (file.isDirectory()) {
        if (checkFolder(file)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkFolder(File folder) {
    File[] files = folder.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.getName().equals("dex")) {
          saveDexFiles(file);
          return true;
        } else if (file.isDirectory()) {
          checkFolder(file);
        }
      }
    }
    return false;
  }

  private void saveDexFiles(File dexFolder) {
    File[] dexFiles = dexFolder.listFiles();
    if (dexFiles != null) {
      if (dexFiles.length == 0) {
        problemList.add("dex папка пустая");
      } else {
        notificationList.add("Список dex файлов: ");
        for (File dex : dexFiles) {
          notificationList.add(dex.getName());
        }
      }
    }
  }

  @Override
  public boolean predict() {
    collectInformation();

    return isBuildTypeCorrect();
  }

  public List<String> getProblemList() {
    return problemList;
  }

  public List<String> getNotificationList() {
    return notificationList;
  }

  private boolean isBuildTypeCorrect() {
    if (buildType != null) {
      if ((buildType == BuildType.NODEX && isMultidexEnabled) || (buildType == BuildType.MAXDEX && !isMultidexEnabled)) {
        notificationList.add("Используемая сборка: " + buildType + " Мультидекс: " + isMultidexEnabled);
        return true;
      } else {
        problemList.add("Используется некорректная сборка. Тип сборки: " + buildType + " Мультидекс: " + isMultidexEnabled);
        return false;
      }
    } else {
      problemList.add("Не удалось определить тип сборки");
      return false;
    }
  }

  private enum BuildType {
    MAXDEX,
    NODEX
  }
}
