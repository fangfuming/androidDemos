package com.example.downloadtools.downloadManage;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * create by fangfuming
 * on 2021/3/26 0026
 * description:
 */
public class DownLoadService extends Service {


    private HashMap<String,DownLoadTask> mTaskHashMap = new HashMap<>();
    private ExecutorService mExecutorService;
    private LinkedBlockingQueue<DownLoadEntity> mQueue = new LinkedBlockingQueue<>();


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            //判断队列里面还有没有内容
            DownLoadEntity entity = (DownLoadEntity) msg.obj;
            switch (entity.mStatus) {
                case paused:
                case completed:
                case canceled:
                    checkNext();
                    break;

            }
            DownloadDataChanger.getInstance().postStatus(entity);
            return true;
        }
    });

    private void checkNext() {
        DownLoadEntity poll = mQueue.poll();
        if (poll != null) {
            startDownload(poll);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newCachedThreadPool();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DownLoadEntity downLoadEntity = (DownLoadEntity) intent.getSerializableExtra(Constants.KEY_DOWNLOAD_ENTITY);
        int action = intent.getIntExtra(Constants.KEY_DOWNLOAD_ACTION, -1);
        doAction(action,downLoadEntity);
        return super.onStartCommand(intent, flags, startId);
    }

    private void doAction(int action, DownLoadEntity downLoadEntity) {
        switch (action){
            case Constants.KEY_DOWNLOAD_ACTION_ADD:
                addDownload(downLoadEntity);
                break;
            case Constants.KEY_DOWNLOAD_ACTION_PAUSE:
                pauseDownload(downLoadEntity);
                break;
            case Constants.KEY_DOWNLOAD_ACTION_RESUME:
                resumeDownload(downLoadEntity);
                break;
            case Constants.KEY_DOWNLOAD_ACTION_CANCEL:
                cancelDownload(downLoadEntity);
                break;
        }
    }

    private void cancelDownload(DownLoadEntity downLoadEntity) {
        DownLoadTask downLoadTask = mTaskHashMap.remove(downLoadEntity.id);
        if (downLoadTask != null) {
            downLoadTask.cancel();
        }else {
            mQueue.remove(downLoadEntity);
        }
    }

    private void resumeDownload(DownLoadEntity downLoadEntity) {
        addDownload(downLoadEntity);
    }

    private void pauseDownload(DownLoadEntity downLoadEntity) {
        DownLoadTask downLoadTask = mTaskHashMap.remove(downLoadEntity.id);
        if (downLoadTask != null) {
            downLoadTask.pause();
        }else {
            mQueue.remove(downLoadEntity);
        }
    }

    private void addDownload(DownLoadEntity downLoadEntity){
        if (mTaskHashMap.size()>=3) {
            //加入队列中
            mQueue.offer(downLoadEntity);
            downLoadEntity.mStatus = DownLoadEntity.DownloadStatus.waiting;
            DownloadDataChanger.getInstance().postStatus(downLoadEntity);
        }else {
            startDownload(downLoadEntity);
        }
    }

    private void startDownload(DownLoadEntity downLoadEntity) {
        DownLoadTask downLoadTask = new DownLoadTask(downLoadEntity,mHandler);
        mTaskHashMap.put(downLoadEntity.id,downLoadTask);
        mExecutorService.execute(downLoadTask);
    }


}
