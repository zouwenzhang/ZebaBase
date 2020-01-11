package com.zeba.base.test;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zeba.base.photo.PhotoResult;
import com.zeba.base.photo.ZebaPhoto;
import com.zeba.base.test.viewbind.MainActivityViews;

public class MainActivity extends AppCompatActivity {
    private MainActivityViews views;
    private MyDialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//@BindView
        views=new MainActivityViews(this);
        views.btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZebaPhoto.choose(MainActivity.this, new PhotoResult() {
                    @Override
                    public void result(int code, String filePath) {
                        if(Activity.RESULT_OK==code){
                            views.tvText.setText(filePath);
                        }
                    }
                });
            }
        });
        views.btTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZebaPhoto.take(MainActivity.this, new PhotoResult() {
                    @Override
                    public void result(int code, String filePath) {
                        if(Activity.RESULT_OK==code){
                            views.tvText.setText(filePath);
                        }
                    }
                });
            }
        });
        myDialog=new MyDialog(this);
        findViewById(R.id.bt_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ZebaPhoto.onActivityResult(this,requestCode,resultCode,data);
    }
}
