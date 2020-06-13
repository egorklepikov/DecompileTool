package easytool;

import java.util.Objects;

public class ResourceResolver {
    public String resolve(String command) {
        StringBuilder resultCommand = new StringBuilder();
        String[] attrs = command.split(" ");
        ClassLoader classLoader = ResourceResolver.class.getClassLoader();

        resultCommand.append(Objects.requireNonNull(classLoader.getResource(attrs[0])).getPath());
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
