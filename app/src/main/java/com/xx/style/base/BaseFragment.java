package com.xx.style.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.style.utils.SpUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by XX on 2018/9/3.
 * Fragment的基类
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;     //基类上下文

    private View mRootView;

    private Unbinder unbinder;
    protected SpUtils mSpUtils;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, mRootView);

        mSpUtils = SpUtils.getInstance();

        initData();

        initView();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * @return 子布局
     */
    public abstract int getLayoutId();


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }
}
