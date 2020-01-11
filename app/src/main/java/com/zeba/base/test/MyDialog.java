package com.zeba.base.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;

import com.zeba.base.BaseDialog;

public class MyDialog extends BaseDialog {

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.dialog_my;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        setShowGravity(Gravity.CENTER);
    }
}
