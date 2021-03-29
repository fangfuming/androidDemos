package com.example.downloadtools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.downloadtools.downloadManage.DownLoadEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * create by fangfuming
 * on 2021/3/29 0029
 * description:
 */
public class DownLoadListAdapter extends RecyclerView.Adapter<DownLoadListAdapter.InnerHolder> {

    private List<DownLoadEntity> mData = new ArrayList<>();


    public  DownLoadListAdapter(){
        mData.add(new DownLoadEntity("1","http://api.stay4it.com/uploads/test.jpg","天下第一"));
        mData.add(new DownLoadEntity("2","http://api.stay4it.com/uploads/test.jpg","西游记"));
        mData.add(new DownLoadEntity("3","http://api.stay4it.com/uploads/test.jpg","笑傲江湖"));
        mData.add(new DownLoadEntity("4","http://api.stay4it.com/uploads/test.jpg","爱情公寓"));
        mData.add(new DownLoadEntity("5","http://api.stay4it.com/uploads/test.jpg","英雄联盟"));
        mData.add(new DownLoadEntity("6","http://api.stay4it.com/uploads/test.jpg","新三国全集"));
    }



    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_download_item, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        final DownLoadEntity entity = mData.get(position);
        holder.mTitleTv.setText(entity.name);

        if (entity.mStatus == DownLoadEntity.DownloadStatus.none) {
            holder.mStatusTv.setText("none");
        }else if(entity.mStatus == DownLoadEntity.DownloadStatus.downloading){
            holder.mStatusTv.setText("下载中");
        }else if(entity.mStatus == DownLoadEntity.DownloadStatus.waiting){
            holder.mStatusTv.setText("等待中");
        }else if(entity.mStatus == DownLoadEntity.DownloadStatus.paused){
            holder.mStatusTv.setText("暂停中");
        }else if(entity.mStatus == DownLoadEntity.DownloadStatus.completed){
            holder.mStatusTv.setText("下载完成");
        }

        holder.mLengthTv.setText(entity.currentLength+"/"+entity.totalLength);
        holder.mOperateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击了下载
                if (mListener != null) {
                    mListener.onItemClick(entity);
                }
            }
        });
    }

    private Listener mListener = null;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void setData(List<DownLoadEntity> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public List<DownLoadEntity> getData() {
        return mData;
    }

    public interface Listener{
        //点击了下载按钮
        void onItemClick(DownLoadEntity entity);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTv;
        private final TextView mStatusTv;
        private final TextView mLengthTv;
        private final TextView mOperateTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.titleTv);
            mStatusTv = itemView.findViewById(R.id.status);
            mLengthTv = itemView.findViewById(R.id.length);
            mOperateTv = itemView.findViewById(R.id.operateTv);
        }
    }
}
