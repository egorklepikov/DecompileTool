package easytool.manifest;

public class WriteExternalStorage implements ManifestCommand {
    private ManifestProcessor manifestProcessor;

    public WriteExternalStorage(ManifestProcessor manifestProcessor) {
        this.manifestProcessor = manifestProcessor;
    }

    @Override
    public boolean execute() {
        return manifestProcessor.addWriteExternalStorage();
    }

    @Override
    public void initialize() {
        manifestProcessor.initializeAndroidManifest();
    }
}
