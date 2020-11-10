package org.zeba.quick.frame.manager;

import android.content.Intent;
import android.os.Bundle;

import org.zeba.quick.frame.callback.PageListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityLife {

    private List<PageListener> callBacks=new ArrayList<>();

    public void addCallBack(PageListener callBack){
        if(callBack!=null){
            callBacks.add(callBack);
        }
    }

    public void onCreate(Bundle savedInstanceState){
        for(PageListener callBack:callBacks){
            callBack.onCreate(savedInstanceState);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        for(PageListener callBack:callBacks){
            callBack.onSaveInstanceState(outState);
        }
    }

    public void onResume() {
        for(PageListener callBack:callBacks){
            callBack.onResume();
        }
    }

    public void onPause() {
        for(PageListener callBack:callBacks){
            callBack.onPause();
        }
    }

    public void onDestroy() {
        for(PageListener callBack:callBacks){
            callBack.onDestroy();
        }
        callBacks.clear();
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        for(PageListener callBack:callBacks){
            callBack.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for(PageListener callBack:callBacks){
            callBack.onActivityResult(requestCode,resultCode,data);
        }
    }

    public String getResText(String name) {
        String text=name;
        for(PageListener callBack:callBacks){
            text=callBack.getResText(name);
        }
        return text;
    }
}
