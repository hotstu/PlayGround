package io.github.hotstu.downloadlistdemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by songwd on 2017/5/22.
 */

public class DownloadService extends Service {
    private static DownloadManager DOWNLOAD_MANAGER = null;

    public static DownloadManager getDownloadManager(Context ctx) {
        if (!isServiceRunning(ctx)) {
            Intent downloadSvr = new Intent("download.service.action");
            ctx.startService(downloadSvr);
        }
        if (DownloadService.DOWNLOAD_MANAGER == null) {
            DownloadService.DOWNLOAD_MANAGER = new DownloadManager(ctx);
        }
        return DOWNLOAD_MANAGER;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("DownloadService", "onDestroy: ");
        if (DOWNLOAD_MANAGER != null) {
            DOWNLOAD_MANAGER.stopAllDownload();
            DOWNLOAD_MANAGER.backupDownloadInfoList();
        }
        super.onDestroy();
    }

    public static boolean isServiceRunning(Context context) {
        boolean isRunning = false;

        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(Integer.MAX_VALUE);

        if (serviceList == null || serviceList.size() == 0) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(DownloadService.class.getName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
