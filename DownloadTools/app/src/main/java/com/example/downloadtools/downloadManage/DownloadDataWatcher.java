package com.example.downloadtools.downloadManage;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * create by fangfuming
 * on 2021/3/26 0026
 * description: 观察者
 */
public abstract class DownloadDataWatcher implements Observer {

    @Override
    public void update(Observable o, Object entity) {
        if (entity instanceof DownLoadEntity) {
            notifyUpdate((DownLoadEntity)entity);
            Log.d("fgg",((DownLoadEntity)entity).mStatus.toString()+((DownLoadEntity)entity).currentLength+"/"+((DownLoadEntity)entity).totalLength+"");
        }
    }

    public abstract void notifyUpdate(DownLoadEntity entity);
}
