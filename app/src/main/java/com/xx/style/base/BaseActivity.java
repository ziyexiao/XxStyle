package com.xx.style.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.xx.style.R;
import com.xx.style.utils.LogUtils;
import com.xx.style.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by XX on 2018/7/31.
 * activity的基类
 */

public abstract class BaseActivity extends SwipeBackActivity {
    FrameLayout mFlActivityContainer;
    private Unbinder mUnbinder;
    public SpUtils mSpUtils;
    protected ImmersionBar mImmersionBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSpUtils = SpUtils.getInstance();

        mFlActivityContainer = (FrameLayout) findViewById(R.id.fl_activity_container);

        init();
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor() {
        Toolbar toolBar = getToolBar();
        if (toolBar == null) {
            mImmersionBar.init();
        } else {
            mImmersionBar.titleBar(toolBar).init();
        }
    }

    protected abstract Toolbar getToolBar();

    private void init() {
        mFlActivityContainer.addView(View.inflate(this, getLayoutId(), null));

        //必须在inflate后，不然会空指针
        mUnbinder = ButterKnife.bind(this);

        mImmersionBar = ImmersionBar.with(this);

        this.setStatusBarColor();

        this.initView();

        this.initData();
    }

    /**
     * 获取布局
     * @return  布局的id
     */
    public abstract int getLayoutId();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    public void dumpActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态

        mUnbinder.unbind();
    }
}
