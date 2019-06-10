package com.xx.style.view.loving;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.xx.style.R;
import com.xx.style.adapter.LovingAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.bean.LovingBean;
import com.xx.style.listener.MusicPlayerListener;
import com.xx.style.manager.GlideManager;
import com.xx.style.manager.PopupWindowManager;
import com.xx.style.utils.DialogUtils;
import com.xx.style.utils.FileUtils;
import com.xx.style.utils.LogUtils;
import com.xx.style.utils.PixeUtils;
import com.xx.style.weiget.decoration.LovingItemDecoration;
import com.xx.style.weiget.view.ColorChangeTextView;
import com.xx.style.weiget.view.PullZoomView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by XX on 2018/8/11.
 *
 */

public class LovingActivity extends BaseActivity implements MusicPlayerListener {
    @BindView(R.id.rv_loving)
    RecyclerView mRvLoving;
    @BindView(R.id.pzv_loving)
    PullZoomView mPzvLoving;
    @BindView(R.id.cctv_zoom)
    ColorChangeTextView cctvZoom;
    /*  @BindView(R.id.top_view)
      View mTopView;*/
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;
    @BindView(R.id.iv_loving_bg)
    ImageView mIvLovingBg;
    @BindView(R.id.iv_music)
    ImageView mIvMusic;
    private float duration = PixeUtils.dip2px(260);//在50-80dp之间去改变头部的透明度

    private int startY = PixeUtils.dip2px(80);       //开始渐变的Y坐标


    private int animY = PixeUtils.dip2px(320);       //开始渐变的Y坐标
    private ArgbEvaluator evaluator = new ArgbEvaluator();
    private MediaPlayer mPlayer;
    private ObjectAnimator mObjectAnimator;

    private boolean playing;        //是否在播放

    private boolean canShowPop;       //是否可以弹出popupwindow

    private int soundCount = 0;

    private String[] sounds = {
            "https://app.xintianhong888.com/media/1.mp3",
            "https://app.xintianhong888.com/media/2.mp3",
            "https://app.xintianhong888.com/media/3.mp3",
            "https://app.xintianhong888.com/media/4.mp3"
    };

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loving;
    }

    @Override
    protected void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                playSound(true);    //播放音乐
            }
        }).start();


        startAnimation();   //开始转动动画

        GlideManager.loadImgCenter("http://b305.photo.store.qq.com/psb?/V14Ej48r0Axdf2/lyvLgsY1cm.iMzH4lo8OlIaFLbKWf6nMLy*8Q8627uE!/b/dDEBAAAAAAAA&bo=VQhABoAPoAsRGRE!&rf=viewer_4", mIvLovingBg);

        //设置layoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvLoving.setLayoutManager(linearLayoutManager);

        LovingItemDecoration lovingItemDecoration = new LovingItemDecoration(PixeUtils.dip2px(8));
        mRvLoving.addItemDecoration(lovingItemDecoration);

        //解决滑动冲突
        mRvLoving.setNestedScrollingEnabled(false);

        mTvTitle.setText("LOVE");

        mIvLovingBg.setAlpha(0f);

        //设置pullZoomView属性
        mPzvLoving.setIsParallax(true);
        mPzvLoving.setIsZoomEnable(true);
        mPzvLoving.setSensitive(2.5f);

        cctvZoom.setColor(0xFFA50CEC, 0xFFFFFFFF, 0xFFA50CEC);
        cctvZoom.setDelayTime(150);

        //获取presenter实例
        mPzvLoving.setOnScrollListener(new PullZoomView.OnScrollListener() {
            @Override
            public void onScroll(int l, int scrollY, int oldl, int oldScrollY) {
                if (scrollY <= startY) {
                    mRlBg.setBackgroundColor(getResources().getColor(R.color.transMainColor));
                    mTvTitle.setTextColor(getResources().getColor(R.color.transZeroWhite));
//                    mRlMyTopHead.setAlpha(1f - (scrollY/duration) * 1f);
                } else if (scrollY > duration) {
                    mRlBg.setBackgroundColor(getResources().getColor(R.color.mainColor));
                    mTvTitle.setTextColor(getResources().getColor(R.color.white));
//                    mRlMyTopHead.setAlpha(0f);/

                } else {
                    mRlBg.setBackgroundColor((Integer) evaluator.evaluate((scrollY - startY) / (duration - startY), getResources().getColor(R.color.transMainColor), getResources().getColor(R.color.transNineMainColor)));
                    mTvTitle.setTextColor((Integer) evaluator.evaluate((scrollY - startY) / (duration - startY), getResources().getColor(R.color.transZeroWhite), getResources().getColor(R.color.white)));
//                    mRlMyTopHead.setAlpha(1f - (scrollY/duration) * 1f);
                }
            }

            @Override
            public void onHeaderScroll(int currentY, int maxY) {
                //根据当前的Y坐标与最大Y坐标算出渐变比例
                float alpha = currentY * 1f / maxY * 1f;

                //给背景设置渐变动画
                mIvLovingBg.setAlpha(alpha);

                //给文字设置渐变动画
                cctvZoom.setAlpha(1f - alpha);

                mRlBg.setAlpha(alpha);
            }

            @Override
            public void onContentScroll(int l, int scrollY, int oldl, int oldScrollY) {
            }
        });

        mPzvLoving.setOnPullZoomListener(new PullZoomView.OnPullZoomListener() {
            @Override
            public void onPullZoom(int originHeight, int currentHeight) {

                if (currentHeight <= animY) {
                    //animY为渐变动画的临界值
                    //根据当前高度与原始高度的差值与最大高度差值之比得出渐变比例
                    float alpha = (currentHeight * 1f - originHeight * 1f) / (animY * 1f - originHeight * 1f);

                    cctvZoom.setAlpha(1f - alpha);

                    mIvLovingBg.setAlpha(alpha);
                } else {
                    mIvLovingBg.setAlpha(1f);

                    cctvZoom.setAlpha(0f);
                }
            }

            @Override
            public void onZoomFinish() {
                //在拉伸完成后恢复原状
                mIvLovingBg.setAlpha(0f);
                cctvZoom.setAlpha(1f);
            }
        });
    }

    /**
     * 开启属性动画
     */
    private void startAnimation() {
        if (mObjectAnimator == null) {
            mObjectAnimator = ObjectAnimator.ofFloat(mIvMusic, "rotation", 0f, 360f);
            mObjectAnimator.setDuration(5000);
            mObjectAnimator.setRepeatCount(Animation.INFINITE);

            LinearInterpolator linearInterpolator = new LinearInterpolator();

            mObjectAnimator.setInterpolator(linearInterpolator);
        }

        mObjectAnimator.start();

    }

    //播放BGM
    private void playSound(final boolean seek) {

        //在播放前，将MediaPlayer重置
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        } else {
            mPlayer = new MediaPlayer();
        }

        if (!seek) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogUtils.getInstance().showLoginDialog(LovingActivity.this, "正在切换...");  //放置在prepare之前
                }
            });
        }

//        playLocalSound();       //播放本地音乐

        playInternetSound();       //播放线上音乐

        if (seek) {
            //移动到当前位置播放
            //在prepare后
            mPlayer.seekTo(mSpUtils.getInt("mp3Position"));
        }

        playing = true;

        mPlayer.start();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //当当前歌曲的是最后一首时， 将当前的音乐索引设置为0
                if (soundCount == sounds.length - 1) {
                    soundCount = 0;
                } else {     //不是最后一首时，将当前歌曲的索引加1即可
                    soundCount++;
                }
                //存储当前播放的歌曲索引
                mSpUtils.putInt("soundCount", soundCount);
                playSound(false);
            }
        });

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                DialogUtils.getInstance().dismissDialog();      //准备完成之后隐藏加载中的dialog
            }
        });

    }

    /**
     * 播放线上音乐
     */
    private void playInternetSound() {
        try {
            soundCount = mSpUtils.getInt("soundCount");    //获取播放曲目

            mPlayer.setDataSource(this, Uri.parse(sounds[soundCount]));


            mPlayer.prepare();

        } catch (IOException e) {
            LogUtils.i(e.toString());

            e.printStackTrace();
        }
    }

    private void playLocalSound() {
        AssetManager assetManager;
        try {
            assetManager = getAssets();

            //获取assets文件夹下的音频文件
            AssetFileDescriptor fileDescriptor = assetManager.openFd("lose_yourself.mp3");

            //配置MeDiaPlayer
            mPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());

            mPlayer.prepare();

        } catch (IOException e) {
            LogUtils.i(e.toString());
            e.printStackTrace();
        }


    }

    @Override
    protected void initData() {
        //获取数据
        String json = FileUtils.getJson("loving.json", this);

        LogUtils.i("json:" + json);

        final LovingBean listBean = new Gson().fromJson(json, LovingBean.class);

        //设置adapter
        LovingAdapter lovingAdapter = new LovingAdapter(R.layout.item_loving, listBean.data);
        mRvLoving.setAdapter(lovingAdapter);

        lovingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(LovingActivity.this, LovingCameraActivity.class);
                intent.putExtra("jsonName", listBean.data.get(position).jsonName);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_no_anim, R.anim.activity_top_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null && mPlayer.isPlaying()) {
            savePlayTime();
            mPlayer.release();
            mPlayer = null;

            mObjectAnimator.cancel();
            mObjectAnimator = null;
        }
    }


    @OnClick({R.id.iv_music, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_music:
                LogUtils.i("");

                //弹出popwindow
                PopupWindowManager.getInstance(this).showMusicPlayerPop(this, mIvMusic, playing);

                break;

        }
    }

    /**
     * 根据播放状态来控制播放或暂停
     */
    private void changePlayByStatus() {

        if (playing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mObjectAnimator.pause();
            }
            //暂停
            if (mPlayer != null) {
                mPlayer.stop();

                playing = false;    //设置当前状态为暂停

                //存储播放的位置
                savePlayTime();
            }
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mObjectAnimator.resume();
            }
            try {

                assert mPlayer != null;
                mPlayer.prepare();
                mPlayer.seekTo(mSpUtils.getInt("mp3Position"));
                mPlayer.start();

                playing = true;    //设置当前状态为播放

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void savePlayTime() {
        int currentPosition = mPlayer.getCurrentPosition();
        mSpUtils.putInt("mp3Position", currentPosition);    //保存播放位置
        mSpUtils.putInt("soundCount", soundCount);      //保存播放首数的索引
    }

    @Override
    public void next() {
        //当当前歌曲的是最后一首时， 将当前的音乐索引设置为0
        if (soundCount == sounds.length - 1) {
            soundCount = 0;
        } else {    //若不是最后一首，则将索引加1即可
            soundCount++;
        }

        mSpUtils.putInt("soundCount", soundCount);

        new Thread(new Runnable() {
            @Override
            public void run() {
                playSound(false);
            }
        }).start();
    }

    @Override
    public void last() {
        if (soundCount == 0) {
            soundCount = sounds.length - 1;
        } else {
            soundCount--;
        }

        mSpUtils.putInt("soundCount", soundCount);

        new Thread(new Runnable() {
            @Override
            public void run() {
                playSound(false);
            }
        }).start();
    }

    @Override
    public void pause() {

        changePlayByStatus();

    }
}
