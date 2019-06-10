package com.xx.style.base;

import android.app.Application;
import android.content.Context;

import com.xx.style.utils.SpUtils;

/**
 * Created by XX on 2018/7/31.
 *
 */

public class BaseApplication extends Application {

    public static Context getContext() {
        return mContext;
    }

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        SpUtils.getInstance().init(this);

        SpUtils.getInstance().putString("loving", "1993321");
    }
}
