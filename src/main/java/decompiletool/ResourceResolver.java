package decompiletool;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourceResolver {
  public String resolve(String command) {
    StringBuilder resultCommand = new StringBuilder();
    String[] attrs = command.split(" ");
    ClassLoader classLoader = ResourceResolver.class.getClassLoader();

    URL resourceURL = classLoader.getResource(attrs[0]);
    try {
      if (resourceURL != null) {
        resultCommand.append(Paths.get(resourceURL.toURI()).toAbsolutePath().toString());
      } else {
        throw new NullPointerException();
      }
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    resultCommand.append(" ");
    for (int index = 0; index < attrs.length; index++) {
      if (index == 0) {
        continue;
      }
      resultCommand.append(attrs[index]).append(" ");
    }
    return resultCommand.toString();
  }
}
