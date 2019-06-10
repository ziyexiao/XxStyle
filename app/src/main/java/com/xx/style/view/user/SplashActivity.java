package com.xx.style.view.user;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.xx.style.MainActivity;
import com.xx.style.R;
import com.xx.style.base.BaseActivity;
import com.xx.style.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by XX on 2018/7/31.
 * 欢迎页
 */

public class SplashActivity extends BaseActivity {

    protected Timer mTimer;

    private long DELAYTIME = 1000;
    private long DUMPTIME = 1;
    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 0;


    @Override
    public void setStatusBarColor() {

    }

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setSwipeBackEnable(false);
    }

    @Override
    protected void initData() {
        mTimer = new Timer();

        mTimer.schedule(new splashTimeTask(), 0, DELAYTIME);

        if (disContainPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || disContainPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);

            mTimer.cancel();
        }
    }

    private boolean disContainPermission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    /**
     * 计时
     */
    class splashTimeTask extends TimerTask {

        @Override
        public void run() {
            if (DUMPTIME > 0) {
                DUMPTIME--;
            } else {
                mTimer.cancel();
                dumpActivity(MainActivity.class);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dumpActivity(MainActivity.class);
                finish();
            } else {
                dumpActivity(MainActivity.class);
                finish();
                ToastUtils.showToast(SplashActivity.this, "Permission Denied");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
