package easytool;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String command) {
    super("Unable to resolve command: " + command);
  }
}
