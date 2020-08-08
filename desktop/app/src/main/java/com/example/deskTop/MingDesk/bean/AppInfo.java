package com.example.deskTop.MingDesk.bean;


import android.graphics.drawable.Drawable;

/**
 * create by fangfuming
 * on 2020/8/7 0007
 * description:
 */
public class AppInfo {
    private String packageName; //包名
    private Drawable icon;       //图标
    private String Name;        //应用标签

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
