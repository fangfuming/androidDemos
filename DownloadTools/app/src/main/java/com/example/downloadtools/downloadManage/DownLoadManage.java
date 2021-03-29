package com.example.downloadtools.downloadManage;

import android.content.Context;
import android.content.Intent;

import com.example.Constants;

import org.xml.sax.DocumentHandler;

/**
 * create by fangfuming
 * on 2021/3/26 0026
 * description: 控制器  下载管理
 */
public class DownLoadManage {


    private static DownLoadManage mInstance;
    private final Context context;

    public static synchronized DownLoadManage getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DownLoadManage(context);
        }
        return mInstance;
    }

    private DownLoadManage(Context context){
        this.context = context;
    }


    public void add(DownLoadEntity entity){
        Intent intent  = new Intent(context,DownLoadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTITY,entity);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_ADD);
        context.startService(intent);
    }
    public void pause(DownLoadEntity entity){
        Intent intent  = new Intent(context,DownLoadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTITY,entity);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_PAUSE);
        context.startService(intent);
    }
    public void resume(DownLoadEntity entity){
        Intent intent  = new Intent(context,DownLoadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTITY,entity);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_RESUME);
        context.startService(intent);
    }
    public void cancel(DownLoadEntity entity){
        Intent intent  = new Intent(context,DownLoadService.class);
        intent.putExtra(Constants.KEY_DOWNLOAD_ENTITY,entity);
        intent.putExtra(Constants.KEY_DOWNLOAD_ACTION,Constants.KEY_DOWNLOAD_ACTION_CANCEL);
        context.startService(intent);
    }

    public void addObserver(DownloadDataWatcher watcher){
        DownloadDataChanger.getInstance().addObserver(watcher);
    }

    public void removeObserver(DownloadDataWatcher watcher){
        DownloadDataChanger.getInstance().deleteObserver(watcher);
    }

}
