package com.xx.style.view.view;

/**
 * Created by 52862 on 2018/8/13.
 */

public interface IBaseView<T> {
    void onSuccess(T data);

    void onError(String error);
}
