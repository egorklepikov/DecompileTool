package easytool;

import java.util.Objects;

public class ResourceResolver {
    public String resolve(String command) {
        StringBuilder resultCommand = new StringBuilder();
        String[] attrs = command.split(" ");
        ClassLoader classLoader = ResourceResolver.class.getClassLoader();
        switch (attrs[0]) {
            case "adb" : {
                resultCommand.append(Objects.requireNonNull(classLoader.getResource("adb")).getPath());
                break;
            }
            case "apktool.bat" : {
                resultCommand.append(Objects.requireNonNull(classLoader.getResource("apktool.bat")).getPath());
                break;
            }
            case "apktool.jar" : {
                resultCommand.append(Objects.requireNonNull(classLoader.getResource("apktool.jar")).getPath());
                break;
            }
            case "apktool-linux" : {
                resultCommand.append(Objects.requireNonNull(classLoader.getResource("apktool-linux")).getPath());
                break;
            }
            case "apktool-osx" : {
                resultCommand.append(Objects.requireNonNull(classLoader.getResource("apktool-osx")).getPath());
                break;
            }
            case "debug.keystore" : {
                resultCommand.append(Objects.requireNonNull(classLoader.getResource("debug.keystore")).getPath());
                break;
            }
            case "dx" : {
                resultCommand.append(Objects.requireNonNull(classLoader.getResource("dx")).getPath());
                break;
            }
            case "jarsigner" : {
                //TODO something is wrong here
                resultCommand.append("jarsigner");
                break;
            }
            default: {
                resultCommand.append("unknown resource");
            }
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
