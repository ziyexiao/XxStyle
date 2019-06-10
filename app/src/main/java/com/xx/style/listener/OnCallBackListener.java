package com.xx.style.listener;

/**
 * Created by 52862 on 2018/8/14.
 */

public interface OnCallBackListener<T> {
    void onSuccess(T data);
    void onFail(String msg);
    void onError(String error);
}
