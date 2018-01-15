package io.github.hotstu.downloadlistdemo;

import android.content.Context;


public class DownloadManager implements IDownloadManager{
    public DownloadManager(Context ctx) {

    }

    @Override
    public int getDownloadInfoListCount() {
        return 0;
    }

    @Override
    public DownloadInfo getDownloadInfo() {
        return null;
    }

    @Override
    public void addNewDownload() {

    }

    @Override
    public void resumeDownload() {

    }

    @Override
    public void removeDownload() {

    }

    @Override
    public void stopDownload() {

    }

    @Override
    public void stopAllDownload() {

    }

    @Override
    public void backupDownloadInfoList() {

    }
}
