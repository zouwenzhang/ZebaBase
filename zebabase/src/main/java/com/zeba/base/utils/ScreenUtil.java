package com.zeba.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ScreenUtil {
    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕高度
     * */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;
        return height;
    }

    /**
     * 获取屏幕宽度
     * */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        return width;
    }

    /**根据View截图*/
    public static Bitmap clipByView(View view) {
        view.buildDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap bmp =view.getDrawingCache();
        Bitmap bip=Bitmap.createBitmap(bmp);
        view.destroyDrawingCache();
        return bip;
    }

    /**根据activity截取当前屏幕*/
    public static Bitmap ScreenClipByActivity(Activity activity, boolean isclipstate) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm=new DisplayMetrics();
        // 获取屏幕宽和高
        display.getMetrics(dm);
        int widths = dm.widthPixels;
        int heights = dm.heightPixels;
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        Bitmap bmp =view.getDrawingCache();
        if(isclipstate){
            // 去掉状态栏
            bmp = Bitmap.createBitmap(bmp, 0,
                    statusBarHeights, widths, heights - statusBarHeights);
        }else{
            bmp = Bitmap.createBitmap(bmp, 0,
                    0, widths, heights);
        }

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }

    /**根据activity截取当前屏幕*/
    public static Bitmap getScreenStateBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        int statusBarHeights = getStatusBarHeight(activity);
        if(statusBarHeights==0){
            return null;
        }
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm=new DisplayMetrics();
        // 获取屏幕宽和高
        display.getMetrics(dm);
        int widths = dm.widthPixels;
        int heights = dm.heightPixels;
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        Bitmap bmp =view.getDrawingCache();
        // 去掉状态栏
        bmp = Bitmap.createBitmap(bmp, 0,
                0, widths, statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }

    /*获取屏幕状态栏高度*/
    public static int getStatusBarHeight(Context context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
