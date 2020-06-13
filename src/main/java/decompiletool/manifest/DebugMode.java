package decompiletool.manifest;

public class DebugMode implements ManifestCommand {
  private final ManifestProcessor manifestProcessor;

  public DebugMode(ManifestProcessor manifestProcessor) {
    this.manifestProcessor = manifestProcessor;
  }

  @Override
  public boolean execute() {
    return manifestProcessor.addDebugMode();
  }

  @Override
  public void initialize() {
    manifestProcessor.initializeAndroidManifest();
  }
}
