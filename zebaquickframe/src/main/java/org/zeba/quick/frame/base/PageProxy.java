package org.zeba.quick.frame.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PageProxy {
    private FrameActivity activity;

    public PageProxy(FrameActivity activity){
        this.activity=activity;
    }

    public void attachBaseContext(Context context){
        activity.attachBaseContext(context);
    }

    public void onCreate(Bundle savedInstanceState) {
        activity.onCreate(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        activity.onSaveInstanceState(outState);
    }

    public void onResume() {
        activity.onResume();
    }

    public void onPause() {
        activity.onPause();
    }

    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        activity.onActivityResult(requestCode,resultCode,data);
    }

    public void onDestroy() {
        activity.onDestroy();
    }

    public int getContentViewId(){
        return activity.getContentViewId();
    }

    public String getContentViewName(){
        return activity.getContentViewName();
    }

    public void clear(){
        activity=null;
    }
}
