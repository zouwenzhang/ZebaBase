package com.zeba.base.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;

public class MyGlideImageViewTarget extends ImageViewTarget<Drawable> {
    private ImageView imageView;
    private Request request;
    public MyGlideImageViewTarget(ImageView view) {
        super(view);
        imageView=view;
    }

    @Override
    protected void setResource(@Nullable Drawable resource) {
        if(imageView!=null){
            imageView.setImageDrawable(resource);
        }
    }

    @Override
    public void setRequest(@Nullable Request request) {
        this.request=request;
    }

    @Nullable
    @Override
    public Request getRequest() {
        return this.request;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.imageView=null;
        this.request=null;
    }
}
