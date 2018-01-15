package io.github.hotstu.downloadlistdemo;


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
