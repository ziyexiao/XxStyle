package com.xx.style.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by xiaoxiao on 2018/4/27.
 */

public class SpUtils {
    private volatile static SpUtils mInstance;

    private Context mContext;
    private SharedPreferences mPref;

    private SpUtils() {
    }

    public static SpUtils getInstance() {
        if (null == mInstance) {
            synchronized (SpUtils.class) {
                if (null == mInstance) {
                    mInstance = new SpUtils();
                }
            }
        }
        return mInstance;
    }

    //在AppBase里面初始化
    public void init(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        if (mPref == null) {
            mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        }
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        return mPref.getBoolean(key, def);
    }

    public String getString(String key) {
        return mPref.getString(key, "");
    }

    public String getString(String key, String def) {
        return mPref.getString(key, def);
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }

    public long getLong(String key, int defInt) {
        return mPref.getLong(key, defInt);
    }

    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public int getInt(String key, int defInt) {
        return mPref.getInt(key, defInt);
    }

    public boolean contains(String key) {
        return mPref.contains(key);
    }


    public void remove(String key) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
    }
}
