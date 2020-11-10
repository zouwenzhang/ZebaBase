package org.zeba.quick.frame.base;

import android.content.Context;

public interface FrameControlView {
    void toast(String msg);
    void toast(int resId);
    void showLoading();
    void dismissLoading();
    Context getMyContext();
}
