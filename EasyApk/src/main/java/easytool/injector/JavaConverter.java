package easytool.injector;

import easytool.CmdProcessor;

import java.io.File;

public class JavaConverter extends CmdProcessor {
    private String classGetter;
    private String jarGetter;
    private String dexGetter;
    private String smaliGetter;

    public JavaConverter() {
        classGetter = "javac -d ../Tool -cp ../EasyApk/src/main/java/easytool/injector/classes ../EasyApk/src/main/java/easytool/injector/code/AppodealCode.java";
        jarGetter = "jar cvf AppodealCode.jar easytool/injector/code/AppodealCode.class";
        dexGetter = "dx --dex --output AppodealCode.dex AppodealCode.jar";

        //TODO проресерчить когда нужно юзать --use-locals --debug-info false
        smaliGetter = "java -jar lib/baksmali.jar d --use-locals --debug-info false -o dexout AppodealCode.dex ";
    }

    private boolean generateClassFile() {
        return processCmdCommand(classGetter);
    }

    private boolean generateJarFile() {
        return processCmdCommand(jarGetter);
    }

    private boolean generateDexFile() {
        return processCmdCommand(dexGetter);
    }

    private boolean generateSmaliFile() {
        return processCmdCommand(smaliGetter);
    }

    private boolean processAppodealCode() {
        if (generateClassFile()) {
            if (generateJarFile()) {
                if (generateDexFile()) {
                    if (generateSmaliFile()) {
                        return true;
                    } else {
                        System.out.println("Произошли проблемы во время получения smali кода");
                    }
                } else {
                    System.out.println("Произошли проблемы во время генерации .dex файла для Appodeal кода");
                }
            } else {
                System.out.println("Произошли проблемы во время генерации .jar файла для Appodeal кода");
            }
        } else {
            System.out.println("Произошли проблемы во время генерации .class файла для Appodeal кода");
        }

        return false;
    }

    public File getAppodealSmaliCode() {
        if (processAppodealCode()) {
            File dexout = new File("dexout/easytool/injector/code/AppodealCode.smali");
            if (dexout.exists()) {
                return dexout;
            } else return null;
        } else {
            return null;
        }
    }
}
