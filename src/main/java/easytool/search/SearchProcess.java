package easytool.search;

import easytool.cmd.Command;

import java.util.List;
import java.util.Map;

public class SearchProcess implements Command {
  private final SearchProcessor searchProcessor;

  public SearchProcess(SearchProcessor searchProcessor) {
    this.searchProcessor = searchProcessor;
  }

  @Override
  public boolean execute() {
    return searchProcessor.searchAppodeal();
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Запущен процесс поиска Appodeal SDK в проекте");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("Процесс поиска Appodeal SDK завершен. Отладочная информация: ");
    Map<String, List<String>> sources = searchProcessor.getAppodealSources();
    if (sources == null) {
      System.out.println("sources == null");
    } else if (!sources.isEmpty()) {
      printSources(sources);
    } else {
      System.out.println("Ничего не найдено");
    }
  }

  private void printSources(Map<String, List<String>> sources) {
    for (Map.Entry<String, List<String>> entry : sources.entrySet()) {
      System.out.println("Класс: " + entry.getKey());
      System.out.println("Методы:");
      for (String method : entry.getValue()) {
        System.out.print("- " + method + "\n");
      }
      System.out.println("\n");
      System.out.println("--------------------------------");
    }
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("В процессе поиска Appodeal SDK в проекте произошли ошибки");
    System.out.println("----------------------------------------------------");
  }
}
