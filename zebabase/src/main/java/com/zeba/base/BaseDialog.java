package com.zeba.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public abstract class BaseDialog extends Dialog {
    private int gravity=Gravity.CENTER;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.ZebaBaseDialog);
//        if (getWindow() != null) {
//            getWindow().setBackgroundDrawable(null);
//            getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
//        }
        init();
    }

    protected abstract int getContentViewId();

    protected void initView(View view){}
    protected void initData(){};
    protected void initListener(){};

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(getContentViewId(), null);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(layoutParams);
        setContentView(view);
        initView(view);
        initData();
        initListener();
        getWindow().setGravity(gravity);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();
        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

//    private void setMaxHeight() {
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        WindowManager.LayoutParams p = getWindow().getAttributes();
//        Point point = new Point();
//        display.getSize(point);
//        p.width = point.x;
//        p.gravity = this.gravity;
//        getWindow().setAttributes(p);
//    }

    public void setShowGravity(int gravity){
        this.gravity=gravity;
        if(gravity==Gravity.BOTTOM){
            if(getWindow()!=null){
                getWindow().setWindowAnimations(R.style.ZebaBaseDialogBottomAnim);
            }
        }else if(gravity==Gravity.CENTER){
            if(getWindow()!=null){
                getWindow().setWindowAnimations(R.style.ZebaBaseDialogCenterAnim);
            }
        }
    }
}
