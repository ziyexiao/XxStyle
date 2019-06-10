package com.xx.style.weiget.view.rullerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.xx.style.R;


public final class RulerValuePicker extends FrameLayout implements ObservableHorizontalScrollView.ScrollChangedListener {

    /**
     * 刻度尺左边间距
     */
    @SuppressWarnings("NullableProblems")
    @NonNull
    private View mLeftSpacer;

    /**
     * 刻度尺右边间距
     */
    @SuppressWarnings("NullableProblems")
    @NonNull
    private View mRightSpacer;

    /**
     * 刻度尺
     */
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RulerView mRulerView;

    /**
     * {@link ObservableHorizontalScrollView},
     *  一个整合了左右间距和刻度尺的scrollView
     */
    @SuppressWarnings("NullableProblems")
    @NonNull
    private ObservableHorizontalScrollView mHorizontalScrollView;

    @Nullable
    private RulerValuePickerListener mListener;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private Paint mNotchPaint;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private Path mNotchPath;

    private int mNotchColor = Color.WHITE;

    /**
     * Public constructor.
     */
    public RulerValuePicker(@NonNull final Context context) {
        super(context);
        init(null);
    }

    /**
     * Public constructor.
     */
    public RulerValuePicker(@NonNull final Context context,
                            @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Public constructor.
     */
    public RulerValuePicker(@NonNull final Context context,
                            @Nullable final AttributeSet attrs,
                            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Public constructor.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RulerValuePicker(@NonNull final Context context,
                            @Nullable final AttributeSet attrs,
                            final int defStyleAttr,
                            final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(@Nullable AttributeSet attributeSet) {

        //Add all the children
        addChildViews();

        if (attributeSet != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attributeSet,
                    R.styleable.RulerValuePicker,
                    0,
                    0);

            try { //Parse params
                if (a.hasValue(R.styleable.RulerValuePicker_notch_color)) {
                    mNotchColor = a.getColor(R.styleable.RulerValuePicker_notch_color, Color.WHITE);
                }

                if (a.hasValue(R.styleable.RulerValuePicker_ruler_text_color)) {
                    setTextColor(a.getColor(R.styleable.RulerValuePicker_ruler_text_color, Color.WHITE));
                }

                if (a.hasValue(R.styleable.RulerValuePicker_ruler_text_size)) {
                    setTextSize((int) a.getDimension(R.styleable.RulerValuePicker_ruler_text_size, 14));
                }

                if (a.hasValue(R.styleable.RulerValuePicker_indicator_color)) {
                    setIndicatorColor(a.getColor(R.styleable.RulerValuePicker_indicator_color, Color.WHITE));
                }

                if (a.hasValue(R.styleable.RulerValuePicker_indicator_width)) {
                    setIndicatorWidth(a.getDimensionPixelSize(R.styleable.RulerValuePicker_indicator_width,
                            4));
                }

                if (a.hasValue(R.styleable.RulerValuePicker_indicator_interval)) {
                    setIndicatorIntervalDistance(a.getDimensionPixelSize(R.styleable.RulerValuePicker_indicator_interval,
                            4));
                }

                if (a.hasValue(R.styleable.RulerValuePicker_long_height_height_ratio)
                        || a.hasValue(R.styleable.RulerValuePicker_short_height_height_ratio)) {

                    setIndicatorHeight(a.getFraction(R.styleable.RulerValuePicker_long_height_height_ratio,
                            1, 1, 0.6f),
                            a.getFraction(R.styleable.RulerValuePicker_short_height_height_ratio,
                                    1, 1, 0.4f));
                }

                if (a.hasValue(R.styleable.RulerValuePicker_min_value) ||
                        a.hasValue(R.styleable.RulerValuePicker_max_value)) {
                    setMinMaxValue(a.getInteger(R.styleable.RulerValuePicker_min_value, 0),
                            a.getInteger(R.styleable.RulerValuePicker_max_value, 100));
                }
            } finally {
                a.recycle();
            }
        }

        //Prepare the notch color.
        mNotchPaint = new Paint();
        prepareNotchPaint();

        mNotchPath = new Path();
    }

    /**
     * 创建三角形游标的画笔
     */
    private void prepareNotchPaint() {
        mNotchPaint.setColor(mNotchColor);
        mNotchPaint.setStrokeWidth(5f);
        mNotchPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     *  对自定的ScrollView初始化
     *  将对应的左右间距和刻度尺给添加进来
     */
    private void addChildViews() {
        mHorizontalScrollView = new ObservableHorizontalScrollView(getContext(), this);
        mHorizontalScrollView.setHorizontalScrollBarEnabled(false); //Don't display the scrollbar

        //创建父布局
        final LinearLayout rulerContainer = new LinearLayout(getContext());

        //添加左间距
        mLeftSpacer = new View(getContext());
        rulerContainer.addView(mLeftSpacer);

        //添加刻度尺
        mRulerView = new RulerView(getContext());
        rulerContainer.addView(mRulerView);

        //添加右间距
        mRightSpacer = new View(getContext());
        rulerContainer.addView(mRightSpacer);

        //添加父布局LinearLayout到自定义ScrollView
        mHorizontalScrollView.removeAllViews();
        mHorizontalScrollView.addView(rulerContainer);

        //添加ScrollView到该自定义控件
        removeAllViews();
        addView(mHorizontalScrollView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画三角形游标
        canvas.drawPath(mNotchPath, mNotchPaint);
    }

    @Override
    protected void onLayout(boolean isChanged, int left, int top, int right, int bottom) {
        super.onLayout(isChanged, left, top, right, bottom);

        if (isChanged) {
            final int width = getWidth();

            //设置左间距的宽度
            final ViewGroup.LayoutParams leftParams = mLeftSpacer.getLayoutParams();
            leftParams.width = width / 2;
            mLeftSpacer.setLayoutParams(leftParams);

            //设置右间距的宽度
            final ViewGroup.LayoutParams rightParams = mRightSpacer.getLayoutParams();
            rightParams.width = width / 2;
            mRightSpacer.setLayoutParams(rightParams);

            //计算三角形游标的路径
            calculateNotchPath();

            invalidate();
        }
    }

    /**
     * 计算三角形游标的路径
     *
     * @see #mNotchPath
     */
    private void calculateNotchPath() {
        mNotchPath.reset();

        mNotchPath.moveTo(getWidth() / 2 - 22, 0);
        mNotchPath.lineTo(getWidth() / 2, 25);
        mNotchPath.lineTo(getWidth() / 2 + 22, 0);
    }

    /**
     * 滑动刻度尺来选择对应的值
     */
    public void selectValue(final int value) {
        mHorizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                //刻度尺滑动对应的值
                int valuesToScroll;

                //如果值大于刻度尺最大值或小于刻度尺最小值，就不滑动。否则就滑动到相应的值
                if (value < mRulerView.getMinValue()) {
                    valuesToScroll = 0;
                } else if (value > mRulerView.getMaxValue()) {
                    valuesToScroll = mRulerView.getMaxValue() - mRulerView.getMinValue();
                } else {
                    valuesToScroll = value - mRulerView.getMinValue();
                }

                mHorizontalScrollView.smoothScrollBy(
                        valuesToScroll * mRulerView.getIndicatorIntervalWidth(), 0);
            }
        });
    }

    /**
     * @return 获取到刻度尺当前选择的值
     */
    public int getCurrentValue() {
        int absoluteValue = mHorizontalScrollView.getScrollX() / mRulerView.getIndicatorIntervalWidth();
        int value = mRulerView.getMinValue() + absoluteValue;

        if (value > mRulerView.getMaxValue()) {
            return mRulerView.getMaxValue();
        } else if (value < mRulerView.getMinValue()) {
            return mRulerView.getMinValue();
        } else {
            return value;
        }
    }

    @Override
    public void onScrollChanged() {
        if (mListener != null) mListener.onIntermediateValueChange(getCurrentValue());
    }

    @Override
    public void onScrollStopped() {
        makeOffsetCorrection(mRulerView.getIndicatorIntervalWidth());
        if (mListener != null) {
            mListener.onValueChange(getCurrentValue());
        }
    }

    private void makeOffsetCorrection(final int indicatorInterval) {
        int offsetValue = mHorizontalScrollView.getScrollX() % indicatorInterval;
        if (offsetValue < indicatorInterval / 2) {
            mHorizontalScrollView.scrollBy(-offsetValue, 0);
        } else {
            mHorizontalScrollView.scrollBy(indicatorInterval - offsetValue, 0);
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.value = getCurrentValue();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        selectValue(ss.value);
    }

    //**********************************************************************************//
    //******************************** GETTERS/SETTERS *********************************//
    //**********************************************************************************//

    /**
     * @param notchColorRes Color resource of the notch to display. Default color os {@link Color#WHITE}.
     * @see #setNotchColor(int)
     * @see #getNotchColor()
     */
    public void setNotchColorRes(@ColorRes final int notchColorRes) {
        setNotchColor(ContextCompat.getColor(getContext(), notchColorRes));
    }

    /**
     * @return Integer color of the notch. Default color os {@link Color#WHITE}.
     * @see #setNotchColor(int)
     * @see #setNotchColorRes(int)
     */
    @ColorInt
    public int getNotchColor() {
        return mNotchColor;
    }

    /**
     * 设置三角形游标的颜色
     */
    public void setNotchColor(@ColorInt final int notchColor) {
        mNotchColor = notchColor;
        prepareNotchPaint();
        invalidate();
    }

    /**
     * 获取文本颜色
     */
    @CheckResult
    @ColorInt
    public int getTextColor() {
        return mRulerView.getTextColor();
    }

    /**
     * 设置文本颜色
     */
    public void setTextColor(@ColorInt final int color) {
        mRulerView.setTextColor(color);
    }


    public void setTextColorRes(@ColorRes final int color) {
        setTextColor(ContextCompat.getColor(getContext(), color));
    }

    /**
     * 获取文本字体大小
     */
    @CheckResult
    public float getTextSize() {
        return mRulerView.getTextSize();
    }

    /**
     * 设置文本字体大小
     */
    public void setTextSize(final int dimensionDp) {
        mRulerView.setTextSize(dimensionDp);
    }

    public void setTextSizeRes(@DimenRes final int dimension) {
        setTextSize((int) getContext().getResources().getDimension(dimension));
    }

    /**
     * @return Color integer value of the indicator color.
     * @see #setIndicatorColor(int)
     * @see #setIndicatorColorRes(int)
     * @see RulerView#mIndicatorColor
     */
    @CheckResult
    @ColorInt
    public int getIndicatorColor() {
        return mRulerView.getIndicatorColor();
    }

    /**
     * 设置刻度尺刻度颜色
     */
    public void setIndicatorColor(@ColorInt final int color) {
        mRulerView.setIndicatorColor(color);
    }

    public void setIndicatorColorRes(@ColorRes final int color) {
        setIndicatorColor(ContextCompat.getColor(getContext(), color));
    }

    /**
     * 获取刻度尺刻度之间的宽度
     */
    @CheckResult
    public float getIndicatorWidth() {
        return mRulerView.getIndicatorWidth();
    }

    /**
     * 设置刻度尺刻度之间的宽度
     */
    public void setIndicatorWidth(final int widthPx) {
        mRulerView.setIndicatorWidth(widthPx);
    }

    public void setIndicatorWidthRes(@DimenRes final int width) {
        setIndicatorWidth(getContext().getResources().getDimensionPixelSize(width));
    }

    /**
     * 获取刻度尺最小的值
     */
    @CheckResult
    public int getMinValue() {
        return mRulerView.getMinValue();
    }

    /**
     * 获取刻度尺最大的值
     */
    @CheckResult
    public int getMaxValue() {
        return mRulerView.getMaxValue();
    }

    /**
     * 设置刻度尺最大和最小值
     */
    public void setMinMaxValue(final int minValue, final int maxValue) {
        mRulerView.setValueRange(minValue, maxValue);
        invalidate();
        selectValue(minValue);
    }

    /**
     * @return Get distance between two indicator in pixels.
     * @see #setIndicatorIntervalDistance(int)
     * @see RulerView#mIndicatorInterval
     */
    @CheckResult
    public int getIndicatorIntervalWidth() {
        return mRulerView.getIndicatorIntervalWidth();
    }

    /**
     * Set the spacing between two vertical lines/indicators. Default value is 14 pixels.
     *
     * @param indicatorIntervalPx Distance in pixels. This cannot be negative number or zero.
     * @see RulerView#mIndicatorInterval
     */
    public void setIndicatorIntervalDistance(final int indicatorIntervalPx) {
        mRulerView.setIndicatorIntervalDistance(indicatorIntervalPx);
    }

    /**
     * @return Ratio of long indicator height to the ruler height.
     * @see #setIndicatorHeight(float, float)
     * @see RulerView#mLongIndicatorHeightRatio
     */
    @CheckResult
    public float getLongIndicatorHeightRatio() {
        return mRulerView.getLongIndicatorHeightRatio();
    }

    /**
     * @return Ratio of short indicator height to the ruler height.
     * @see #setIndicatorHeight(float, float)
     * @see RulerView#mShortIndicatorHeight
     */
    @CheckResult
    public float getShortIndicatorHeightRatio() {
        return mRulerView.getShortIndicatorHeightRatio();
    }

    /**
     * 设置刻度尺刻度的高度
     * 包括长标和短标
     */
    public void setIndicatorHeight(final float longHeightRatio,
                                   final float shortHeightRatio) {
        mRulerView.setIndicatorHeight(longHeightRatio, shortHeightRatio);
    }

    /**
     * Set the {@link RulerValuePickerListener} to get callbacks when the value changes.
     *
     * @param listener {@link RulerValuePickerListener}
     */
    public void setValuePickerListener(@Nullable final RulerValuePickerListener listener) {
        mListener = listener;
    }

    /**
     * User interface state that is stored by RulerView for implementing
     * {@link View#onSaveInstanceState}.
     */
    public static class SavedState extends BaseSavedState {

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

        private int value = 0;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            value = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(value);
        }
    }
}
