package com.xx.style.manager;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.xx.style.R;
import com.xx.style.listener.MusicPlayerListener;
import com.xx.style.utils.LogUtils;
import com.xx.style.utils.PixeUtils;

/**
 * Created by XX on 2018/9/3.
 *
 */

public class PopupWindowManager {

    private volatile static PopupWindowManager mInstance;
    private static MusicPlayerListener mMusicPlayerListener;
    private PopupWindow mPopWindow;

    public static PopupWindowManager getInstance(MusicPlayerListener musicPlayerListener) {
        mMusicPlayerListener = musicPlayerListener;
        if (null == mInstance) {
            synchronized (PopupWindowManager.class) {
                if (null == mInstance) {
                    mInstance = new PopupWindowManager();
                }
            }
        }
        return mInstance;
    }

    public void showMusicPlayerPop(Activity activity, View rootView, boolean playing) {

        LogUtils.i("playing:" + playing);

        View view = View.inflate(activity, R.layout.popwindow_music, null);
        mPopWindow = new PopupWindow(view, PixeUtils.dip2px(240), PixeUtils.dip2px(50));
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopWindow = null;
            }
        });

        RelativeLayout rlNextOne = view.findViewById(R.id.rl_next_one);
        RelativeLayout rlLastOne = view.findViewById(R.id.rl_last_one);
        RelativeLayout rlStop = view.findViewById(R.id.rl_stop);

        ImageView ivStart = view.findViewById(R.id.iv_start);

        if (playing) {
            ivStart.setImageResource(R.mipmap.ic_stop);
        } else {
            ivStart.setImageResource(R.mipmap.ic_play);
        }

        rlNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicPlayerListener.next();
                mPopWindow.dismiss();
            }
        });

        rlLastOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicPlayerListener.last();
                mPopWindow.dismiss();
            }
        });

        rlStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicPlayerListener.pause();
                mPopWindow.dismiss();
            }
        });

        mPopWindow.setFocusable(true);

        mPopWindow.setOutsideTouchable(true);

        mPopWindow.showAsDropDown(rootView, 0, PixeUtils.dip2px(10));
    }
}
