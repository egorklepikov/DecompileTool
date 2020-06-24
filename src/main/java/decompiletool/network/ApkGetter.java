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
            Utils.getInstance().setInitialApkData(apkInfo[0]);
            return true;
        }

        File apkFile = loadApk();
        Utils.getInstance().setInitialApkData(Objects.requireNonNull(apkFile).getAbsolutePath());
        return apkFile.exists();
    }

    private File loadApk() {
        return null;
    }

    private boolean isApkLoadedFromHardDisk() {
        return apkInfo.length == 2;
    }
}
