package com.xx.style.base;

import android.content.Context;

import com.xx.style.listener.OnCallBackListener;
import com.xx.style.utils.LogUtils;
import com.xx.style.utils.ToastUtils;
import com.xx.style.view.view.IBaseView;

/**
 * Created by 52862 on 2018/8/13.
 */

public abstract class BasePresenter<GV extends IBaseView> implements OnCallBackListener{
    public Context mContext;
    public GV mView;

    public BasePresenter(Context context, GV view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void onSuccess(Object data) {
        if (mView != null) {
            loadSuccess(data);
        }
    }

    protected abstract void loadSuccess(Object data);

    @Override
    public void onFail(String msg) {
        if (mView != null) {
            mView.onError(msg);
        }
        ToastUtils.showToast(mContext, msg);
    }


    @Override
    public void onError(String error) {
        if (mView != null) {
            mView.onError(error);
        }

        LogUtils.i(error);
    }

    public void onDetach() {
        mView = null;
    }
}
