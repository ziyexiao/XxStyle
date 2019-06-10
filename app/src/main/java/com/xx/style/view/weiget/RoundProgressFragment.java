package com.xx.style.view.weiget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.xw.repo.BubbleSeekBar;
import com.xx.roundprogressbar.PixeUtils;
import com.xx.roundprogressbar.RoundProgressBar;
import com.xx.style.MainActivity;
import com.xx.style.R;
import com.xx.style.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by XX on 2018/9/3.
 */
public class RoundProgressFragment extends BaseFragment implements BubbleSeekBar.OnProgressChangedListener {
    @BindView(R.id.rpb)
    RoundProgressBar mRpb;
    @BindView(R.id.cb_arg_color)
    CheckBox mCbArgColor;
    @BindView(R.id.bsb_current)
    BubbleSeekBar mBsbCurrent;
    @BindView(R.id.bsb_max)
    BubbleSeekBar mBsbMax;
    @BindView(R.id.bsb_duration)
    BubbleSeekBar mBsbDuration;
    @BindView(R.id.bsb_circleThickness)
    BubbleSeekBar mBsbCircleThickness;

    private static final int CURRENT = 100;


    @Override
    protected void initView() {

        //设置修改进度后的动画时长，默认时长为1000
        //如果想要初始化也奏效，则需要在设置当前进度和最大进度之前
        mRpb.setAnimationDuration(1000);
        //设置当前进度
        mRpb.setCurrentProgress(CURRENT);
        //设置最大进度
        mRpb.setMaxProgress(100);

    }

    @Override
    protected void initData() {
        mBsbCurrent.setOnProgressChangedListener(this);
        mBsbDuration.setOnProgressChangedListener(this);
        mBsbMax.setOnProgressChangedListener(this);
        mBsbCircleThickness.setOnProgressChangedListener(this);

        mCbArgColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRpb.setProgressArgbColor(true);
                } else {
                    mRpb.setProgressArgbColor(false);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_round_progress;
    }


    @Override
    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

    }

    @Override
    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        switch (bubbleSeekBar.getId()) {
            case R.id.bsb_current:
                mRpb.setCurrentProgress(progressFloat);
                break;
        }
    }

    @Override
    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
        switch (bubbleSeekBar.getId()) {

            case R.id.bsb_max:
                mRpb.setMaxProgress(progress);
                break;
            case R.id.bsb_duration:
                mRpb.setAnimationDuration((long) (progressFloat * 1000));
                break;
            case R.id.bsb_circleThickness:
                mRpb.setCircleThickness(PixeUtils.dip2px(mActivity, progress));
                break;
        }
    }
}
