package com.xx.style.view.learning;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.xx.style.R;
import com.xx.style.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by XX on 2018/8/10.
 * 博客详情
 */

public class LearningWebActivity extends BaseActivity {
    @BindView(R.id.pb_boke_details)
    ProgressBar mPbBokeDetails;
    @BindView(R.id.wb_boke_details)
    WebView mWbBokeDetails;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    private String mUrl;

    @Override
    protected Toolbar getToolBar() {
        return mToolBar;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_learning_web;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWbBokeDetails.canGoBack()) {
                    mWbBokeDetails.goBack();
                } else {
                    finish();
                }
            }
        });

        mToolBar.setTitle(title);
    }

    @Override
    protected void initData() {
        WebSettings webSettings = mWbBokeDetails.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWbBokeDetails.setVerticalScrollBarEnabled(false);
        mWbBokeDetails.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress != 100) {
                    mPbBokeDetails.setVisibility(View.VISIBLE);

                    mPbBokeDetails.setProgress(newProgress);
                }
            }
        });

        mWbBokeDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPbBokeDetails.setVisibility(View.GONE);
            }
        });


        mWbBokeDetails.loadUrl(mUrl);
    }

    @Override
    public void onBackPressed() {
        if (mWbBokeDetails.canGoBack()) {
            mWbBokeDetails.goBack();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mWbBokeDetails.destroy();
        super.onDestroy();
    }

}
