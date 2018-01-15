package io.github.hotstu.downloadlistdemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class DownloadInfo {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String url;
    @Generated(hash = 178960380)
    public DownloadInfo(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
    @Generated(hash = 327086747)
    public DownloadInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
