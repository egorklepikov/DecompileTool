package decompiletool.manifest;

public class NetworkSecurityConfig implements ManifestCommand {
  private final ManifestProcessor manifestProcessor;

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
