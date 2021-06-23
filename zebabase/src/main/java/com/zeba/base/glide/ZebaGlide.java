package com.zeba.base.glide;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.util.HashMap;
import java.util.Map;

public class ZebaGlide {
    private static String RefererDefault=null;

    public static void setRefererDefault(String refererDefault) {
        RefererDefault = refererDefault;
    }

    private static GlideUrl getGlideUrl(String url){
        GlideUrl gliderUrl = new GlideUrl(url,new Headers(){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headerMap=new HashMap<>();
                if(RefererDefault!=null){
                    headerMap.put("Referer",RefererDefault);
                }
                return headerMap;
            }
        });
        return gliderUrl;
    }

    public static void clearView(Context context, ImageView view){
        Glide.with(context).clear(view);
    }

    public static void load(Context context,ImageView view, String url){
        if(!checkWithContext(context)){
            return;
        }

        Glide.with(context).load(getGlideUrl(url)).into(view);
    }

    public static void loadImg(ImageView view, String url){
        loadImg(view.getContext(),view,url);
    }

    public static void loadImg(Context context,ImageView view, String url){
        if(!checkWithContext(context)){
            return;
        }
        Glide.with(context).load(getGlideUrl(url)).into(new MyGlideImageViewTarget(view));
    }

    public static void loadImg(Drawable def,ImageView view, String url){
        loadImg(view.getContext(),def,view,url);
    }

    public static void loadImg(Context context,Drawable def,ImageView view, String url){
        if(!checkWithContext(context)){
            return;
        }
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.error(def);
        requestOptions.fallback(def);
        requestOptions.placeholder(def);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(getGlideUrl(url)).into(new MyGlideImageViewTarget(view));
    }

    public static void loadImg(int def,ImageView view, String url){
        loadImg(view.getContext(),def,view,url);
    }

    public static void loadImg(Context context,int def,ImageView view, String url){
        if(!checkWithContext(context)){
            return;
        }
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.error(def);
        requestOptions.fallback(def);
        requestOptions.placeholder(def);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(getGlideUrl(url)).into(new MyGlideImageViewTarget(view));
    }

    public static void load(Context context,int def,ImageView view, String url){
        if(!checkWithContext(context)){
            return;
        }
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.error(def);
        requestOptions.fallback(def);
        requestOptions.placeholder(def);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(getGlideUrl(url)).into(view);
    }

    public static void loadRound(Context context,ImageView view, String url,int round){
        if(!checkWithContext(context)){
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(round));
        Glide.with(context).load(getGlideUrl(url)).apply(options).into(view);
    }

    public static void loadRound(Context context,int def,ImageView view, String url,int round){
        if(!checkWithContext(context)){
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(def)
                .fallback(def)
                .error(def)
                .transform(new GlideRoundTransform(round));
        Glide.with(context).load(getGlideUrl(url)).apply(options).into(view);
    }

    public static void loadCircle(Context context,ImageView view, String url){
        if(!checkWithContext(context)){
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideCircleTransform());
        Glide.with(context).load(getGlideUrl(url)).apply(options).into(view);
    }

    public static void loadCircle(Context context,int def,ImageView view, String url){
        if(!checkWithContext(context)){
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(def)
                .fallback(def)
                .error(def)
                .transform(new GlideCircleTransform());
        Glide.with(context).load(getGlideUrl(url)).apply(options).into(view);
    }

    private static boolean checkWithContext(Context context){
        if(context instanceof Activity){
            Activity activity=(Activity) context;
            if(activity.isDestroyed()){
                return false;
            }
        }
        return true;
    }
}
