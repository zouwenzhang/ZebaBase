package org.zeba.quick.frame.callback;

import android.content.Intent;
import android.os.Bundle;

public abstract class PageListener {

    public void onCreate(Bundle savedInstanceState){}
    public void onSaveInstanceState(Bundle outState) {}
    public void onResume() {}
    public void onPause() {}
    public void onDestroy() {}
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {}
    public void onActivityResult(int requestCode, int resultCode,Intent data) {}
    public String getResText(String name){
        return name;
    }
}
