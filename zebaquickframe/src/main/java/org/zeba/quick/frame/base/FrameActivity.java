package org.zeba.quick.frame.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.zeba.base.IntentBuilder;
import com.zeba.base.activity.FrameActivityIml;
import com.zeba.base.activity.ZebaAM;
import com.zeba.permission.ZebaPermission;
import com.zeba.presenter.ZebaPresenter;

import org.zeba.quick.frame.callback.PageListener;
import org.zeba.quick.frame.callback.ViewClickCallBack;
import org.zeba.quick.frame.manager.ActivityLife;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author 56238
 */
public abstract class FrameActivity extends AppCompatActivity implements FrameActivityIml, FrameControlView {
    private boolean isShow=false;
    private boolean isProxyMode=false;
    private ActivityLife activityLife=new ActivityLife();
    private WeakReference<FrameActivity> refActivity=new WeakReference<>(this);
    private PageProxy myProxy =new PageProxy(this);
    private Map<String, Map<String,String>> textMap;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(!isProxyMode){
            super.onCreate(savedInstanceState);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            ZebaAM.get().push(that());
        }
        initCreateView();
        initStatusBar();
        activityLife.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(!isProxyMode){
            super.onSaveInstanceState(outState);
        }
        activityLife.onSaveInstanceState(outState);
    }

    protected void initCreateView(){
        View view=loadContentView();
        if(view!=null){
            that().setContentView(view);
        }else if(getContentViewId()!=0){
            that().setContentView(getContentViewId());
        }
        ZebaPresenter.inject(this);
        initView();
        initData();
        initListener();
    }

    protected void initStatusBar(){
        setStateBarLightColor();
    }

    public void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = that().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            that().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void setStateBarLightColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = that().getWindow().getDecorView();
            if(decorView != null){
                that().getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
                that().getWindow().getDecorView().setSystemUiVisibility
                        (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    protected void onResume() {
        if(!isProxyMode){
            super.onResume();
        }
        isShow=true;
        activityLife.onResume();
    }

    @Override
    protected void onPause() {
        if(!isProxyMode){
            super.onPause();
        }
        isShow=false;
        activityLife.onPause();
    }

    @Override
    public boolean isShow(){
        return isShow;
    }

    protected int getContentViewId(){
        return 0;
    }

    protected View loadContentView(){
        return null;
    }

    protected String getContentViewName(){return null;}

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }

    public void addClick(View v, View.OnClickListener listener){
        if(v!=null&&listener!=null){
            v.setOnClickListener(listener);
        }
    }

    public void addClick(View v, final ViewClickCallBack listener){
        if(v!=null&&listener!=null){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick();
                }
            });
        }
    }

    public <T> T getParams(String name,Object dValue){
        if(dValue==null||dValue instanceof String){
            return (T) that().getIntent().getStringExtra(name);
        }
        if(dValue instanceof Integer){
            return (T)(Integer.valueOf(that().getIntent().getIntExtra(name,(Integer) dValue)));
        }
        if(dValue instanceof Float){
            return (T)(Float.valueOf(that().getIntent().getFloatExtra(name,(Float) dValue)));
        }
        if(dValue instanceof Double){
            return (T)(Double.valueOf(that().getIntent().getDoubleExtra(name,(Double) dValue)));
        }
        if(dValue instanceof Long){
            return (T)(Long.valueOf(that().getIntent().getLongExtra(name,(Long)dValue)));
        }
        if(dValue instanceof Short){
            return (T)(Short.valueOf(that().getIntent().getShortExtra(name,(Short)dValue)));
        }
        return (T)dValue;
    }

    public IntentBuilder newIntent(Class cls){
        return new IntentBuilder().setClass(that(),cls);
    }

    public IntentBuilder newIntent(String className){
        return new IntentBuilder().setClass(that(),className);
    }

    public String getResText(int id){
        return that().getResources().getString(id);
    }

    public String getResText(String name){
        if(isProxyMode){
            return that().getResText(name);
        }
        if(textMap!=null&&textMap.containsKey(name)){
            String code=getTextCountryCode();
            if(textMap.get(name).containsKey(code)){
                return textMap.get(name).get(code);
            }else{
                return textMap.get(name).get("def");
            }
        }
        return name;
    }

    public void setResultData(int resultCode){
        that().setResult(resultCode);
    }

    public void setResultData(int resultCode,Intent data){
        that().setResult(resultCode,data);
    }

    public void setTextMap(Map<String,Map<String,String>> map){
        textMap=map;
    }

    public Map<String,Map<String,String>> getTextMap(){
        return textMap;
    }

    public String getTextCountryCode(){
        return "def";
    }

    @Override
    public void toast(int msg){
        toast(getString(msg));
    }

    @Override
    public void toast(String msg){
        Toast.makeText(that(),msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getMyContext() {
        return that();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public void back() {
        if(!isProxyMode){
            ZebaAM.get().pop(this);
        }
        that().finish();
    }

    @Override
    protected void onDestroy() {
        if(!isProxyMode){
            super.onDestroy();
            ZebaAM.get().pop(this);
            IntentBuilder.onDestroy(this);
        }
        activityLife.onDestroy();
        myProxy.clear();
        myProxy =null;
        if(textMap!=null){
            textMap.clear();
            textMap=null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(!isProxyMode){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        ZebaPermission.onRequestPermissionsResult(that(),requestCode,permissions,grantResults);
        activityLife.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!isProxyMode){
            super.onActivityResult(requestCode, resultCode, data);
        }
        IntentBuilder.onActivityResult(that(),requestCode,resultCode,data);
        activityLife.onActivityResult(requestCode,resultCode,data);
    }

    public void addLifeListener(PageListener callBack){
        activityLife.addCallBack(callBack);
    }

    public void setParentActivity(FrameActivity activity){
        refActivity=new WeakReference<>(activity);
        isProxyMode=true;
    }

    public PageProxy getMyProxy(){
        return myProxy;
    }

    public FrameActivity that(){
        return refActivity.get();
    }

    public void loadPageError(Exception e){

    }

    public void loadPageFinish(){

    }

    public void showWebPageLoading(){

    }

    public void hideWebPageLoading(){

    }

}
