package com.example.deskTop;

import android.content.Context;

/**
 * create by fangfuming
 * on 2020/7/13 0013
 * description:
 */
public class SizeUtil {

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; //该属性为文字的缩放
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int dp2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale + 0.5f);
    }

    public static int px2dp(Context context,float pxValue) {
        //获取屏幕密度比
        final float scale = context.getResources().getDisplayMetrics().density;    //density = densityDpi/160
        return (int) (pxValue / scale + 0.5f);
    }
}
