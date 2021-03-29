package com.example.downloadtools;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.downloadtools.downloadManage.DownLoadEntity;
import com.example.downloadtools.downloadManage.DownLoadManage;
import com.example.downloadtools.downloadManage.DownloadDataWatcher;

import java.util.List;

/**
 * create by fangfuming
 * on 2021/3/29 0029
 * description:
 */
public class ListActivity  extends AppCompatActivity {

    DownloadDataWatcher mDownloadDataWatcher;

    DownLoadManage mDownloadManager;

    private TextView pauseOrContinueAllTv;
    private RecyclerView recyclerView;
    private DownLoadListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        mDownloadManager = DownLoadManage.getInstance(this);

        pauseOrContinueAllTv = findViewById(R.id.pauseOrContinueAllTv);
        recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new DownLoadListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDownloadManager.addObserver(mDownloadDataWatcher);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDownloadManager.removeObserver(mDownloadDataWatcher);
    }

    private void initEvent() {

        mDownloadDataWatcher = new DownloadDataWatcher(){
            @Override
            public void notifyUpdate(DownLoadEntity entity) {
                List<DownLoadEntity> data = mAdapter.getData();
                int i = data.indexOf(entity);
                if (i!= -1) {
                    data.remove(i);
                    data.add(i,entity);
                    mAdapter.setData(data);
                }
            }
        };

        mAdapter.setListener(new DownLoadListAdapter.Listener() {
            @Override
            public void onItemClick(DownLoadEntity entity) {
                //点击了下载按钮
                if (entity.mStatus == DownLoadEntity.DownloadStatus.none) {
                    mDownloadManager.add(entity);
                }else if(entity.mStatus == DownLoadEntity.DownloadStatus.downloading){
                    mDownloadManager.pause(entity);
                }else if(entity.mStatus == DownLoadEntity.DownloadStatus.paused){
                    mDownloadManager.resume(entity);
                }
            }
        });

    }
}
