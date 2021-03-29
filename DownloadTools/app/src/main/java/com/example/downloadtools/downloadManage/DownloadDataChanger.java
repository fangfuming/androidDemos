package com.example.downloadtools.downloadManage;

import java.util.Observable;

/**
 * create by fangfuming
 * on 2021/3/26 0026
 * description: 下载数据发生变化 被观察者
 */
public class DownloadDataChanger extends Observable {


    public static DownloadDataChanger mInstance;

    private DownloadDataChanger(){

    }

    public static synchronized DownloadDataChanger getInstance(){
        if (mInstance == null) {
            mInstance = new DownloadDataChanger();
        }
        return mInstance;
    }

    public void postStatus(DownLoadEntity entity){
        setChanged();
        notifyObservers(entity);
    }
}
