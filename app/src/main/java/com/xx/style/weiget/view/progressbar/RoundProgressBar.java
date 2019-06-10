package com.xx.style.weiget.view.progressbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import com.xx.style.R;
import com.xx.style.utils.PixeUtils;

import java.math.BigDecimal;

import static android.graphics.Paint.Style.STROKE;

public class RoundProgressBar extends View {

    private Paint paint;
    //最外层圆环的颜色
    private int outerFirstCircleColor;
    //圆环进度的颜色
    private int progressCircleColor;
    //最外层圆环的厚度，即大小
    private float outerFirstCircleThickness;
    //最上面字体的颜色
    private int topTextColor;
    //第二行字体的颜色
    private int secondTextColor;
    //第三行字体的颜色
    private int thirdTextColor;
    //RoundProgress开始的颜色，决定RoundProgress的渐变区间
    private int progressStartColor;
    //RoundProgress结束的颜色
    private int ProgressEndColor;
    //进度的最大值，默认是1000
    private double maxProgress = 1000.0;
    //当前的进度，默认0
    private double currentProgress = 0.0;
    //第一行的字
    private String topText = "";
    //第三行字
    private String thirdText = "";
    //最上面的字的大小
    private float topTextSize;
    //第二行字的大小
    private float secondTextSize;
    //第三行字的大小
    private float thirdTextSize;
    //最外层圆的半径
    private int outerFirstCircleRadius;

    private int center;

    private int textPaintStroke;
    private ArgbEvaluator mArgbEvaluator;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();

        mArgbEvaluator = new ArgbEvaluator();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        outerFirstCircleColor = typedArray.getColor(R.styleable.RoundProgressBar_outerFirstCircleColor, context.getResources().getColor(R.color.progress_gray));

        progressCircleColor = typedArray.getColor(R.styleable.RoundProgressBar_progressCircleColor, context.getResources().getColor(R.color.mainColor));

        progressStartColor = typedArray.getColor(R.styleable.RoundProgressBar_progressStartColor, getResources().getColor(R.color.progress_start));
        ProgressEndColor = typedArray.getColor(R.styleable.RoundProgressBar_progressEndColor, getResources().getColor(R.color.progress_end));

        outerFirstCircleThickness = typedArray.getDimension(R.styleable.RoundProgressBar_outerFirstCircleThickness, PixeUtils.dip2px(12));


        topTextColor = typedArray.getColor(R.styleable.RoundProgressBar_topTextColor, Color.BLACK);
        secondTextColor = typedArray.getColor(R.styleable.RoundProgressBar_secondTextColor, context.getResources().getColor(R.color.mainColor));
        thirdTextColor = typedArray.getColor(R.styleable.RoundProgressBar_thirdTextColor, context.getResources().getColor(R.color.mainColor));

        maxProgress = typedArray.getInt(R.styleable.RoundProgressBar_maxProgress, 100);

        topText = typedArray.getString(R.styleable.RoundProgressBar_topText);
        thirdText = typedArray.getString(R.styleable.RoundProgressBar_thirdText);

        topTextSize = typedArray.getDimension(R.styleable.RoundProgressBar_topTextSize, 24);
        secondTextSize = typedArray.getDimension(R.styleable.RoundProgressBar_secondTextSize, 24);
        thirdTextSize = typedArray.getDimension(R.styleable.RoundProgressBar_thirdTextSize, 24);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //第1步：画出最外层的圆环
        drawOuterFirstCircle(canvas);

        //第2步，画出圆弧
        drawArc(canvas);

        //第3步，画出三行文字
        drawText(canvas);

        //第4步，画出白色小圆
        drawSmallCircle(canvas);
    }

    private void drawSmallCircle(Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(outerFirstCircleThickness / 2 + 1);

        float hudu;
        if (currentProgress < maxProgress) {   //当当前进度大于最大进度时，将当前进度设置成最大进度（目的是让圆环最大只能360度）
            hudu = (float) ((2 * Math.PI / 360) * (currentProgress / maxProgress) * 360);   // 将弧度转化成角度
        } else {
           hudu = (float) ((2 * Math.PI / 360) *  360);   // 将弧度转化成角度
        }

        float X = 0;
        float Y = 0;
        if (hudu >= 0 && hudu <= 90) {
            X = (float) (center + Math.sin(hudu) * outerFirstCircleRadius);   //计算小圆的圆心
            Y = (float) (center - Math.cos(hudu) * outerFirstCircleRadius);
        } else if (hudu > 90 && hudu <= 180) {
            X = (float) (center + Math.cos(hudu) * outerFirstCircleRadius);
            Y = (float) (center + Math.sin(hudu) * outerFirstCircleRadius);
        } else if (hudu > 180 && hudu <= 270) {
            X = (float) (center - Math.sin(hudu) * outerFirstCircleRadius);
            Y = (float) (center + Math.cos(hudu) * outerFirstCircleRadius);
        } else if (hudu > 270 && hudu <= 360) {
            X = (float) (center - Math.sin(hudu) * outerFirstCircleRadius);
            Y = (float) (center - Math.cos(hudu) * outerFirstCircleRadius);
        }


        canvas.drawCircle(X, Y, outerFirstCircleThickness / 4, paint);
    }

    /**
     * 绘制最外层的圆
     *
     * @param canvas    画笔
     */
    private void drawOuterFirstCircle(Canvas canvas) {
        //设置圆的颜色
        paint.setColor(outerFirstCircleColor);

        //设置只绘制边框
        paint.setStyle(STROKE);
        //设置圆的宽度
        paint.setStrokeWidth(outerFirstCircleThickness);
        //设置只绘制边框
        paint.setStyle(STROKE);
        //画出圆
        canvas.drawCircle(center, center, outerFirstCircleRadius, paint);
    }


    /**
     * 画出文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        paint.setStrokeWidth(textPaintStroke);
        paint.setStyle(Paint.Style.FILL);
        //画出第一行文本
        paint.setTextSize(topTextSize);
        paint.setColor(topTextColor);
        float textWidth1 = paint.measureText(topText);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        canvas.drawText(topText, center - textWidth1 / 2, center - outerFirstCircleRadius / 4 - topTextSize / 2, paint); //画出还可摄入

        //画出第二行文本
        paint.setTextSize(secondTextSize);
        paint.setColor(secondTextColor);
        float textWidth2 = paint.measureText(String.valueOf(currentProgress));
        canvas.drawText(String.valueOf(currentProgress), center - textWidth2 / 2, center + secondTextSize / 4, paint); //画出卡路里数值

        //画出第三行文本
        paint.setTextSize(thirdTextSize);
        paint.setColor(thirdTextColor);
        float textWidth3 = paint.measureText(thirdText);
        canvas.drawText(thirdText, center - textWidth3 / 2, center + outerFirstCircleRadius / 2 + thirdTextSize / 2, paint); //画出卡路里单位
    }

    /**
     * 画出圆弧
     *
     * @param canvas 画笔
     */
    private void drawArc(Canvas canvas) {

        paint.setStrokeWidth(outerFirstCircleColor);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        //设置圆弧宽度
        paint.setStrokeWidth(outerFirstCircleThickness + 1);

        RectF oval2 = new RectF(center - outerFirstCircleRadius, center - outerFirstCircleRadius, center + outerFirstCircleRadius, center + outerFirstCircleRadius);  //用于定义的圆弧的形状和大小的界限

        double progress;

        //这里画圆环的时候第二个参数为开始角度，0表示右边中线，90表示底部，-outerFirstCircleRadius
        if (currentProgress < maxProgress) {
            progress = currentProgress;
            drawArcByColor(canvas, oval2, progress);
        } else {
            progress = maxProgress;
            drawArcByColor(canvas, oval2, progress);
        }

    }

    /**
     * 根据颜色来画圆弧
     * @param canvas 画笔
     * @param oval2 圆弧
     * @param progress 进度
     */
    private void drawArcByColor(Canvas canvas, RectF oval2, double progress) {
        Integer color;
        for (int i = 0; i < progress / maxProgress * 360; i++) {
            //颜色渐变
            color = (Integer) mArgbEvaluator.evaluate(i / 360f, progressStartColor, ProgressEndColor);//颜色插值器（level 11以上才可以用）
            paint.setColor(color);

            topTextColor = color;
            secondTextColor = color;
            thirdTextColor = color;

            if (i < maxProgress * 360) {
                canvas.drawArc(oval2, (float) (-90 + i), 1.35f, false, paint);
            }
        }
    }


    /**
     * 当控件的宽高发生变化的时候调用的方法
     * 在这里得到控件的宽高,避免在onDraw的时候多次初始化
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取圆心的坐标，对于自身控件而言是1/2
        center = getMeasuredWidth() / 2;

        //原本是半径是等于中心点，但是由于设置画笔宽度的时候，这个宽度会根据当前的半径，往外部和内部各扩展1/2。
        //所以在设置半径是需要减去圆环宽度的一半。
        //这里没有减去圆环宽度一般是因为想让圆环距离本控件有左右间距，故意为之
        outerFirstCircleRadius = (int) (center - outerFirstCircleThickness);
        //文字不用太大
        textPaintStroke = 1;
    }


    /**
     * 设置进度的最大值
     *
     * @param maxProgress  最大进度
     */
    public void setMaxProgress(double maxProgress) {
        if (maxProgress < 0) {
            this.maxProgress = 0;
        }
        this.maxProgress = maxProgress;
    }


    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param currentProgress   当前进度
     */
    public void setCurrentProgress(double currentProgress) {
        setAnimation(this.currentProgress, currentProgress);

        if (currentProgress < 0) {
            this.currentProgress = 0;
        } else if (currentProgress > maxProgress) {
            this.currentProgress = maxProgress;
        } else if (currentProgress <= maxProgress) {
            this.currentProgress = currentProgress;
        }

    }

    /**
     * 为进度设置动画
     * ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
     * 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
     * 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
     * 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
     * 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
     *
     * @param start  初始值
     * @param end    结束值
     */
    private void setAnimation(double start, double end) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat((float) start, (float) end);
        progressAnimator.setDuration(1000);
        progressAnimator.setTarget(currentProgress);

        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里必须经过两次转换才可以
                double temp = (float) animation.getAnimatedValue();

                BigDecimal bd = new BigDecimal(temp);

                currentProgress = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

                postInvalidate();
            }
        });
        progressAnimator.start();
    }

    public int getSecondTextColor() {
        return secondTextColor;
    }

    public void setSecondTextColor(int secondTextColor) {
        this.secondTextColor = secondTextColor;
        invalidate();
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
        invalidate();
    }

    public int getProgressCircleColor() {
        return progressCircleColor;
    }

    public void setProgressCircleColor(int progressCircleColor) {
        this.progressCircleColor = progressCircleColor;
        invalidate();
    }

    /**
     * 控制RoundProgress颜色渐变
     * @param progressCircleColor  startColor
     */
    public void setProgressStartColor(int progressCircleColor) {
        this.progressStartColor = progressCircleColor;
        invalidate();
    }

    /**
     * 控制RoundProgress颜色渐变
     * @param progressCircleColor  endColor
     */
    public void setProgressEndColor(int progressCircleColor) {
        this.ProgressEndColor = progressCircleColor;
        invalidate();
    }
}
