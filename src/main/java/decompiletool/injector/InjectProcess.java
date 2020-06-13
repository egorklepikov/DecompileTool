package decompiletool.injector;

public class InjectProcess implements InjectCommand {
  private final InjectProcessor injectProcessor;

  public InjectProcess(InjectProcessor injectProcessor) {
    this.injectProcessor = injectProcessor;
  }

  @Override
  public boolean execute() {
    return injectProcessor.injectMethods();
  }

  @Override
  public void startNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Code injection process started.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void endNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("Code injection process finished.");
    System.out.println("----------------------------------------------------");
  }

  @Override
  public void errorNotification() {
    System.out.println("----------------------------------------------------");
    System.out.println("An error occurred during injection process. Check the output to find out the reason.");
    System.out.println("----------------------------------------------------");
  }
}
