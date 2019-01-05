package com.zeba.base.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.zeba.base.utils.BitmapTools;

import java.io.File;
import java.util.WeakHashMap;

public class ZebaPhoto {
    public static final int ChoosePhotoCode=32;
    public static final int TakePhotoCode=33;
    private static WeakHashMap<Activity,String> pathMap;
    private static WeakHashMap<Activity,PhotoResult> callMap;
    private static WeakHashMap<Activity,String> getPathMap(){
        if(pathMap==null){
            pathMap=new WeakHashMap<>();
        }
        return pathMap;
    }
    private static WeakHashMap<Activity,PhotoResult> getCallMap(){
        if(callMap==null){
            callMap=new WeakHashMap<>();
        }
        return callMap;
    }
    public static void choose(Activity activity,PhotoResult result){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, ChoosePhotoCode);
        getCallMap().put(activity,result);
    }

    public static void take(Activity activity,PhotoResult result){
//        File dir= activity.getExternalFilesDir("images");
//        File file=new File(dir,System.currentTimeMillis()+".jpg");
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//        activity.startActivityForResult(intent, TakePhotoCode);
//        getCallMap().put(activity,result);
//        getPathMap().put(activity,file.getAbsolutePath());
    }

    public static void onActivityResult(Activity activity,int requestCode, int resultCode, Intent data) {
        PhotoResult result=getCallMap().get(activity);
        if(result==null){
            return;
        }
        String filePath="";
        if (requestCode == ChoosePhotoCode && resultCode == Activity.RESULT_OK) {
            filePath = BitmapTools.getRealFilePath(activity, data.getData());
        }else if (requestCode == TakePhotoCode && resultCode == Activity.RESULT_OK) {
            filePath=getPathMap().get(activity);
            getPathMap().remove(activity);
        }
        result.result(resultCode,filePath);
        getCallMap().remove(activity);
    }
}
