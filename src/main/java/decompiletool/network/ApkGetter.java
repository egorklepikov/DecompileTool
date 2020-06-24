package decompiletool.network;

import decompiletool.Utils;

import java.io.File;
import java.util.Objects;

public class ApkGetter {
    private final String[] apkInfo;

    public ApkGetter(String[] args) {
        apkInfo = args;
    }

    public boolean getApk() {
        if (isApkLoadedFromHardDisk()) {
            Utils.getInstance().setApkName(apkInfo[0]);
            Utils.getInstance().setXmlFolderPath("/res/xml");
            Utils.getInstance().setAndroidManifestPath("/AndroidManifest.xml");
            return true;
        }
        File apkFile = loadApk();
        Utils.getInstance().setApkName(Objects.requireNonNull(apkFile).getAbsolutePath());
        Utils.getInstance().setXmlFolderPath("/res/xml");
        Utils.getInstance().setAndroidManifestPath("/AndroidManifest.xml");
        return apkFile.exists();
    }

    private File loadApk() {
        return null;
    }

    private boolean isApkLoadedFromHardDisk() {
        return apkInfo.length == 2;
    }
}
