package org.zeba.quick.frame.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * @author zcs
 * @date 2017/12/14 0014
 */

public class SystemUtil {

    /**
     * @param context 上下文
     * @return APP版本号（VersionName）
     */
    public static String getAPPVersion(@NonNull Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAPPVersionCode(@NonNull Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * @param context      上下文（此处限定为 applicationContext）
     * @param metaDataName metaData 渠道 KEY
     * @return 打包渠道名
     */
    public static String getPackageChannelFromMetaData(@NonNull Application context, String metaDataName) {
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (info != null && info.metaData != null) {
                String channel = info.metaData.getString(metaDataName);
                return channel == null ? "" : channel;
            } else {
                return "default";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "default";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "default";
        }
    }

//    /**
//     * 生成设备唯一标识：IMEI、AndroidId、macAddress 三者拼接再 MD5
//     *
//     * @return
//     */
//    public static String generateUniqueDeviceId(Context context) {
//        String imei = "";
//        String androidId = "";
//        String macAddress = "";
//
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
//        if (telephonyManager != null) {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//                imei = telephonyManager.getDeviceId();
//            }
//        }
//        ContentResolver contentResolver = context.getContentResolver();
//        if (contentResolver != null) {
//            androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
//        }
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        if (wifiManager != null) {
//            macAddress = wifiManager.getConnectionInfo().getMacAddress();
//        }
//
//        StringBuilder longIdBuilder = new StringBuilder();
//        if (imei != null) {
//            longIdBuilder.append(imei);
//        }
//        if (androidId != null) {
//            longIdBuilder.append(androidId);
//        }
//        if (macAddress != null) {
//            longIdBuilder.append(macAddress);
//        }
//        return EncryptUtils.encryptMD5ToString(longIdBuilder.toString());
//    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取地区
     *
     * @return
     */
    public static String getArea() {
        return Locale.getDefault().getCountry();
    }

    /**
     * 获取语言
     *
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }


}
