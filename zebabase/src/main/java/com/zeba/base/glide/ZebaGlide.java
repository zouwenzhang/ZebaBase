package com.zeba.base.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ZebaGlide {
    public static void load(Context context,ImageView view, String url){
        Glide.with(context).load(url).into(view);
    }
    public static void load(Context context,int def,ImageView view, String url){
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.error(def);
        requestOptions.fallback(def);
        requestOptions.placeholder(def);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).into(view);
    }

    public static void loadRound(Context context,ImageView view, String url,int round){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(round));
        Glide.with(context).load(url).apply(options).into(view);
    }

    public static void loadRound(Context context,int def,ImageView view, String url,int round){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(def)
                .fallback(def)
                .error(def)
                .transform(new GlideRoundTransform(round));
        Glide.with(context).load(url).apply(options).into(view);
    }

    public static void loadCircle(Context context,ImageView view, String url){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideCircleTransform());
        Glide.with(context).load(url).apply(options).into(view);
    }

    public static void loadCircle(Context context,int def,ImageView view, String url){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(def)
                .fallback(def)
                .error(def)
                .transform(new GlideCircleTransform());
        Glide.with(context).load(url).apply(options).into(view);
    }
}
