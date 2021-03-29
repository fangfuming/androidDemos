package com.example.downloadtools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.downloadtools.downloadManage.DownLoadEntity;
import com.example.downloadtools.downloadManage.DownLoadManage;
import com.example.downloadtools.downloadManage.DownloadDataWatcher;

public class MainActivity extends AppCompatActivity{


    DownloadDataWatcher mDownloadDataWatcher;
    Button pauseOrContinueBtn;

    private Button startBtn;
    private Button cancelBtn;
    private DownLoadManage mDownLoadManage;
    private DownLoadEntity mEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.startBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        pauseOrContinueBtn = findViewById(R.id.pauseOrContinueBtn);
        mDownLoadManage = DownLoadManage.getInstance(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEntity == null) {
                    mEntity = new DownLoadEntity();
                    mEntity.id = "1";
                    mEntity.url = "http://api.stay4it.com/uploads/test.jpg";
                    mEntity.name = "test.jpg";
                }
                mDownLoadManage.add(mEntity);
            }
        });

        pauseOrContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEntity == null) {
                    mEntity = new DownLoadEntity();
                    mEntity.id = "1";
                    mEntity.url = "http://api.stay4it.com/uploads/test.jpg";
                    mEntity.name = "test.jpg";
                }


                if (mEntity.mStatus == DownLoadEntity.DownloadStatus.downloading) {
                    mDownLoadManage.pause(mEntity);
                }else if(mEntity.mStatus == DownLoadEntity.DownloadStatus.paused){
                    mDownLoadManage.resume(mEntity);
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownLoadManage.cancel(mEntity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDownLoadManage.addObserver(mDownloadDataWatcher);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDownLoadManage.removeObserver(mDownloadDataWatcher);
    }

    private void initView() {

        mDownloadDataWatcher = new DownloadDataWatcher() {
            @Override
            public void notifyUpdate(DownLoadEntity entity) {
                mEntity = entity;
                if (entity.mStatus == DownLoadEntity.DownloadStatus.canceled) {
                    mEntity = null;
                }
            }
        };


    }

}