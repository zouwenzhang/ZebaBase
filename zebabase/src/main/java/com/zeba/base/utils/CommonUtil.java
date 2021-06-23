package com.zeba.base.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

public class CommonUtil {

    /**判断App是否在前台运行**/
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName)
                && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }

    /*隐藏软键盘*/
    public static void hideSoftKeybord(Activity activity) {
        if (null == activity) {
            return;
        }
        try {
            final View v = activity.getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    /*弹出软键盘*/
    public static void showSoftInput(Context activity, View view) {
        if (null == activity) {
            return;
        }
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, 0);
            }
        } catch (Exception e) {

        }
    }

    /*延时弹出软键盘*/
    public static void showSoftInputDelayed(final Context activity,
                                            final View view) {
        if (null == activity) {
            return;
        }
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, 0);
                }
            }
        }, 300);
    }

    /*隐藏软键盘*/
    public static void hideSoftInput(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*拨打电话（到拨号界面）*/
    public static void callPhoneUI(Context context, String phone) {
        // ACTION_CALL如果是call，则不跳到拨号界面，直接呼叫
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "无法自动拨号，电话号码：" + phone, Toast.LENGTH_LONG)
                    .show();
        }
    }

    /*直接拨打电话*/
    public static void callPhone(Context context, String phone) {
        // ACTION_CALL如果是call，则不跳到拨号界面，直接呼叫
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "无法自动拨号，请开启拨打电话权限，电话号码：" + phone, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "无法自动拨号，电话号码：" + phone, Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**获取本地版本信息 [0] 返回版本名称 [1] 版本号*/
    public static String[] getCurrentVersion(Context context) {
        String[] name = new String[2];
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            name[0] = info.versionName;
            name[1] = String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return name;
    }

    /**打开默认浏览器*/
    public static void openBrowser(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            // 指定相应的浏览器访问
            // intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
            // uc浏览器"："com.uc.browser", "com.uc.browser.ActivityUpdate“
            // opera："com.opera.mini.android", "com.opera.mini.android.Browser"
            // qq浏览器："com.tencent.mtt", "com.tencent.mtt.MainActivity"
            intent.setData(content_url);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    /*获取手机imei号*/
    public static String getIMEI(Context context) {
        String miei = "";
        try {
            TelephonyManager telephonemanage = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            miei = telephonemanage.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return miei;
    }

    /**
     * 判断某个应用是否安装
     */
    public static boolean isAPkInstalled(Context context,String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
