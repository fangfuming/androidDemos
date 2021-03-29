package com.example.downloadtools.downloadManage;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * create by fangfuming
 * on 2021/3/26 0026
 * description:
 */
public class DownLoadTask implements Runnable {

    private final DownLoadEntity downLoadEntity;
    private final Handler mHandler;
    private boolean isPause;
    private boolean isCanceled;



    public DownLoadTask(DownLoadEntity downLoadEntity, Handler handler) {
        this.downLoadEntity = downLoadEntity;
        this.mHandler = handler;
    }

    public void pause(){
        isPause = true;
        Log.d("DownLoadTask","pause......");
    }

    public void cancel(){
        isCanceled = true;
        Log.d("DownLoadTask","cancel......");
    }

    public void start(){
        downLoadEntity.mStatus = DownLoadEntity.DownloadStatus.downloading;

        downLoadEntity.totalLength = 1024*20;

        for (int i = downLoadEntity.currentLength; i < downLoadEntity.totalLength;) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isPause || isCanceled) {
                downLoadEntity.mStatus = isPause? DownLoadEntity.DownloadStatus.paused :DownLoadEntity.DownloadStatus.canceled;
//                DownloadDataChanger.getInstance().postStatus(downLoadEntity);
                Message msg = mHandler.obtainMessage();
                msg.obj = downLoadEntity;
                mHandler.sendMessage(msg);
                return;
            }
            i+=1024;
            downLoadEntity.currentLength += 1024;
//            DownloadDataChanger.getInstance().postStatus(downLoadEntity);
            Message msg = mHandler.obtainMessage();
            msg.obj = downLoadEntity;
            mHandler.sendMessage(msg);
        }

        downLoadEntity.mStatus = DownLoadEntity.DownloadStatus.completed;
//        DownloadDataChanger.getInstance().postStatus(downLoadEntity);
        Message msg = mHandler.obtainMessage();
        msg.obj = downLoadEntity;
        mHandler.sendMessage(msg);

    }

    @Override
    public void run() {
        start();
    }

    //todo check if support range
}
