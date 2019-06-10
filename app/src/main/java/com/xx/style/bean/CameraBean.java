package com.xx.style.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XX on 2018/8/13.
 */

public class CameraBean {

    public List<DataBean> data;

    public static class DataBean implements Serializable {

        public String url;
        public String poetry;
    }
}
