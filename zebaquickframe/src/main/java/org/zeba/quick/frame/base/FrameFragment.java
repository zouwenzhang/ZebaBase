package org.zeba.quick.frame.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeba.base.IntentBuilder;
import com.zeba.presenter.ZebaPresenter;

import org.zeba.quick.frame.callback.ViewClickCallBack;

public abstract class FrameFragment extends Fragment implements FrameControlView {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=loadContentView();
        if(mView==null){
            mView= View.inflate(getContext(),getContentViewId(),null);
        }
        ZebaPresenter.inject(this);
        initView();
        initData();
        initListener();
        return mView;
    }

    protected int getContentViewId(){
        return 0;
    }
    protected View loadContentView(){
        return null;
    }
    protected String getContentViewName(){return null;}

    public View getContentView(){
        return mView;
    }

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

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

    public IntentBuilder newIntent(Class cls){
        return new IntentBuilder().setClass(getActivity(),cls);
    }

    public IntentBuilder newIntent(String className){
        return new IntentBuilder().setClass(getActivity(),className);
    }

    @Override
    public void toast(String text){
        if(getActivity()!=null&&getActivity() instanceof FrameControlView){
            FrameControlView bcv=(FrameControlView) getActivity();
            bcv.toast(text);
        }
    }

    @Override
    public void toast(int id){
        if(getActivity()!=null&&getActivity() instanceof FrameControlView){
            FrameControlView bcv=(FrameControlView) getActivity();
            bcv.toast(id);
        }
    }

    @Override
    public void showLoading() {
        if(getActivity()!=null&&getActivity() instanceof FrameControlView){
            FrameControlView bcv=(FrameControlView) getActivity();
            bcv.showLoading();
        }
    }

    @Override
    public void dismissLoading() {
        if(getActivity()!=null&&getActivity() instanceof FrameControlView){
            FrameControlView bcv=(FrameControlView) getActivity();
            bcv.dismissLoading();
        }
    }

    @Override
    public Context getMyContext() {
        return getActivity();
    }
}
