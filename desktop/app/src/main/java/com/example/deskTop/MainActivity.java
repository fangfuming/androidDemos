package com.example.deskTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import com.example.deskTop.MingDesk.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //https://blog.csdn.net/weixin_41549915/article/details/81633354

    RecyclerView recyclerview;
    private HomeIconAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        List<AppInfo> appList = getAppList();
        mAdapter.setData(appList);
    }

    private void initView() {
            recyclerview = findViewById(R.id.recyclerview);
            recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this,4));
            mAdapter = new HomeIconAdapter();
            recyclerview.setAdapter(mAdapter);
    }



    private  List<AppInfo> getAppList(){
        List<AppInfo> list = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfos) {
            String packName = resolveInfo.activityInfo.packageName;
            //如果是自己 继续
            if(packName.equals(getPackageName())){
                continue;
            }
            AppInfo appInfo = new AppInfo();
            appInfo.setIcon(resolveInfo.activityInfo.applicationInfo.loadIcon(packageManager));
            appInfo.setPackageName(resolveInfo.activityInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setName(resolveInfo.activityInfo.packageName);
            list.add(appInfo);
        }
        return list;
    }
}
