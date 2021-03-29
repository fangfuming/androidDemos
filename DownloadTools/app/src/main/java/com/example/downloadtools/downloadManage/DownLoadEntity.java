package com.example.downloadtools.downloadManage;

import java.io.Serializable;
import java.util.Objects;

/**
 * create by fangfuming
 * on 2021/3/26 0026
 * description:
 */
public class DownLoadEntity implements Serializable {


    public String id;
    public String url;
    public String name;
    public DownloadStatus mStatus = DownloadStatus.none;

    public int currentLength;
    public int totalLength;

    public DownLoadEntity() {
    }

    public DownLoadEntity(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public enum DownloadStatus{
        none,waiting,downloading, paused, resumed, canceled,completed
    }


    @Override
    public boolean equals(Object o) {
        return o.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
