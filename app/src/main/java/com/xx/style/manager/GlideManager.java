package com.xx.style.manager;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xx.style.base.BaseApplication;
import com.xx.style.base.GlideApp;
import com.xx.style.weiget.transformation.GlideCircleTransform;
import com.xx.style.weiget.transformation.GlideRoundTransform;


/**
 * Created by XX on 2018/7/3.
 *
 */

public class GlideManager {
    public static void loadImageWithPlaceholder(Object url, ImageView imageView) {
        GlideApp
                .with(BaseApplication.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    public static void loadHeadWithPlaceholder(Object url, ImageView imageView) {
        GlideApp
                .with(BaseApplication.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public static void loadImgCenter(Object url, ImageView imageView) {
        GlideApp
                .with(BaseApplication.getContext())
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public static void loadImage(Object url, ImageView imageView) {
        GlideApp
                .with(BaseApplication.getContext())
                .load(url)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     * @param url
     * @param imageView
     */
    public static void loadRoundImg(Object url, ImageView imageView) {
        Glide
                .with(BaseApplication.getContext())
                .load(url)
                .apply(new RequestOptions().transform(new GlideRoundTransform(BaseApplication.getContext())).centerCrop())
                .into(imageView);
    }

    /**
     * 加载圆形图片
     * @param url
     * @param imageView
     */
    public static void loadCircleImg(Object url, ImageView imageView) {
        GlideApp
                .with(BaseApplication.getContext())
                .load(url)
                .transform(new GlideCircleTransform(BaseApplication.getContext()))
                .centerCrop()
                .into(imageView);
    }
}
