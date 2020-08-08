package com.example.deskTop;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deskTop.MingDesk.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * create by fangfuming
 * on 2020/8/6 0006
 * description:
 */
public class HomeIconAdapter extends RecyclerView.Adapter<HomeIconAdapter.InnerHolder> {

    private List<AppInfo> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_icon, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        AppInfo appInfo = mData.get(position);
        Drawable icon = appInfo.getIcon();
        String name = appInfo.getName();
        holder.name.setText(name);
        holder.icon.setBackground(icon);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<AppInfo> appList) {
        mData.clear();
        mData.addAll(appList);
        notifyDataSetChanged();
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
        }
    }
}
