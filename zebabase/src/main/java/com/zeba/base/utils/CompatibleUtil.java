package com.zeba.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 兼容工具类，用于兼容不同系统版本之间的方法
 */

public class CompatibleUtil {
    private static int mSysVC=0;
    public static int getSysVisionCode(Context context){
        if(mSysVC!=0){
            return mSysVC;
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            mSysVC = pi.versionCode;
        } catch (Exception e) {
        }
        return mSysVC;
    }
    public static int getColor(Context context,int id){
        if(getSysVisionCode(context)>=23){
//            try{
//                return context.getColor(id);
//            }catch (Exception e){
//
//            }

        }
        return context.getResources().getColor(id);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void setBackground(View v, Drawable drawable){
        try{
            if(android.os.Build.VERSION.SDK_INT>=16){
                v.setBackground(drawable);
            }else{
                v.setBackgroundDrawable(drawable);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
