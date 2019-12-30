package easytool;

public class ArgsParser {
    private String[] args;

    public ArgsParser(String[] args) {
        this.args = args;
    }

    public boolean isCodeSearcherRequired() {
        return checkArgs("-code");
    }

    public boolean isInjectorRequired() {
        return checkArgs("-inject");
    }

    public boolean isDecompileOnly() {
        return checkArgs("-do");
    }

    public boolean isInstallRequired() {
        return checkArgs("-wi");
    }

    public boolean isPredictRequired() {
        return checkArgs("-predict");
    }

    private boolean checkArgs(String targetCommand) {
        if (args.length == 0) {
            return false;
        }

        for (String arg : args) {
            if (arg.equals(targetCommand)) {
                return true;
            }
        }

        return false;
    }
}
