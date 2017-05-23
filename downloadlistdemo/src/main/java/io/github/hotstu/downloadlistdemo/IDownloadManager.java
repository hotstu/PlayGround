package io.github.hotstu.downloadlistdemo;

/**
 * Created by songwd on 2017/5/22.
 */

public interface IDownloadManager {

    int getDownloadInfoListCount();

    DownloadInfo getDownloadInfo();

    void addNewDownload();

    void resumeDownload();

    void removeDownload();

    void stopDownload();

    void stopAllDownload();

    void backupDownloadInfoList();
}
