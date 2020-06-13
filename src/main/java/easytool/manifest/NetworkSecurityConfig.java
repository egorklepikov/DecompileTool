package easytool.manifest;

public class NetworkSecurityConfig implements ManifestCommand {
    private ManifestProcessor manifestProcessor;

    public NetworkSecurityConfig(ManifestProcessor manifestProcessor) {
        this.manifestProcessor = manifestProcessor;
    }

    @Override
    public boolean execute() {
        return manifestProcessor.addNetworkSecurityConfig();
    }

    @Override
    public void initialize() {
        manifestProcessor.initializeAndroidManifest();
    }
}
