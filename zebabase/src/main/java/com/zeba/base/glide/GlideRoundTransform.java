package com.zeba.base.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideRoundTransform extends BitmapTransformation {
    public GlideRoundTransform(float round){
        this.round=round;
    }
    private float round;
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        int x=0;
        if(result.getHeight()>outHeight){
            x=(result.getHeight()-outHeight)/2;
        }
        int y=0;
        if(result.getWidth()>outWidth){
            y=(result.getWidth()-outWidth)/2;
        }
        RectF rectF=new RectF();
        rectF.left=y;
        rectF.top=x;
        rectF.right=outWidth+y;
        rectF.bottom=outHeight+x;
        canvas.drawRoundRect(rectF,round,round,paint);
        return result;

    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
