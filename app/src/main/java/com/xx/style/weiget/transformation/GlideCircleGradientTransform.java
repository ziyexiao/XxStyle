package com.xx.style.weiget.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by XX on 2018/8/15.
 *
 */

public class GlideCircleGradientTransform extends BitmapTransformation {

    public GlideCircleGradientTransform(Context context) {
        super(context);

    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Canvas canvas = new Canvas(toTransform);
        BitmapShader bitmapShader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        int min = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int radius = min / 2;
        RadialGradient radialGradient = new RadialGradient(toTransform.getWidth() / 2 , toTransform.getHeight() / 2, radius, Color.TRANSPARENT, Color.WHITE, Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(bitmapShader, radialGradient, PorterDuff.Mode.SRC_OVER);
        Paint paint = new Paint();
        paint.setShader(composeShader);
        canvas.drawRect(0, 0, toTransform.getWidth(), toTransform.getHeight(), paint);
        return toTransform;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
