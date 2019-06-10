package com.xx.style.weiget.view.radarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xx.style.R;
import com.xx.style.utils.PixeUtils;


import static android.graphics.Paint.Style.STROKE;

/**
 * Created by XX on 2018/9/28.
 *
 */

public class RadarView extends View {
    //雷达图半径
    private float radius;
    //中心点X坐标
    private float radarX;
    //中心点Y坐标
    private float radarY;
    //雷达图多边形的层数
    private int radarCount = 4;
    //画笔
    private Paint radarPaint;
    //数据的数值，控制覆盖面积
    private float[] data = new float[]{42.8f, 48.3f, 25.6f, 38.7f, 18.3f};
    //数据的属性
    private String[] dataDesc = new String[]{"Rating评分", "战斗评分", "支援评分", "吃鸡率评分", "生存评分"};
    //最大值
    private float maxValue;
    //雷达图覆盖区域颜色
    private int valueColor;
    //雷达图多边形线条颜色
    private int lineColor;
    //文字与雷达图的间距
    private int radarTextMargin;
    //雷达数值文字大小
    private float radarValueSize;
    //雷达属性文字大小
    private float radarDescSize;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        radarPaint = new Paint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView);

        maxValue = typedArray.getFloat(R.styleable.RadarView_maxValue, 100f);
        valueColor = typedArray.getColor(R.styleable.RadarView_valueColor, getResources().getColor(R.color.radarValueColor));
        lineColor = typedArray.getColor(R.styleable.RadarView_lineColor, getResources().getColor(R.color.gray_line));
        radarTextMargin = typedArray.getColor(R.styleable.RadarView_radarTextMargin, PixeUtils.dip2px(5));
        radarValueSize = typedArray.getDimension(R.styleable.RadarView_radarValueSize, PixeUtils.dip2px(15));
        radarDescSize = typedArray.getDimension(R.styleable.RadarView_radarDescSize, PixeUtils.dip2px(8));
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //设置雷达的半径为整个控件的一半的一半
        radius = Math.min(w, h) / 2 * 0.5f;

        //中心点X坐标
        radarX = w / 2;
        //中心点Y坐标
        radarY = h / 2;

        postInvalidate();

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制雷达图多边形
        drawPolygon(canvas);

        //绘制连接线
        drawLines(canvas);

        //绘制覆盖区域
        drawValue(canvas);

        drawTextDesc(canvas);

        drawTextValue(canvas);

    }

    /**
     * 绘制属性的textView
     *
     * @param canvas 画笔
     */
    private void drawTextDesc(Canvas canvas) {
        radarPaint.setColor(Color.WHITE);
        radarPaint.setTextSize(radarDescSize);
        radarPaint.setStyle(Paint.Style.STROKE);

        int x;
        int y;
        float textDescWidth;
        for (int i = 0; i < data.length; i++) {
            x = getPoint(i).x;
            y = getPoint(i).y;

            //属性的文字宽度
            textDescWidth = radarPaint.measureText(String.valueOf(dataDesc[i]));
            //值的文字宽度
            if (i == 1) {
                //计算索引为0位置的x轴坐标， x为多边形当前夹角的坐标，将其减去文字宽度，是为了保持居中显示
                //然后向又位移1/3个半径是让该文字不遮挡雷达图
                //同时同索引位的值的文字也会向右位移1/3个半径，保证居中显示
                x = (int) (x - textDescWidth / 2 + radius / 3);

            } else if (i == 2) {
                //让文字坐标向左位移文字宽度的一半，保持居中显示
                x -= textDescWidth / 2;
                y = (int) (y + radarTextMargin + radarDescSize);
            } else if (i == 3) {
                x -= textDescWidth / 2;
                y = (int) (y + radarTextMargin + radarDescSize);
            } else if (i == 4) {
                //计算索引为3位置的x轴坐标， x为多边形当前夹角的坐标，将其减去文字宽度，是为了保持居中显示
                //然后向又位移1/3个半径是让该文字不遮挡雷达图
                //同时同索引位的值的文字也会向右位移1/3个半径，保证居中显示
                x = (int) (x - textDescWidth / 2 - radius / 3);
            } else if (i == 0) {
                x -= textDescWidth / 2;
                y = (int) (y - radarTextMargin - radarValueSize);
            }

            canvas.drawText(String.valueOf(dataDesc[i]), x, y, radarPaint);
        }
    }

    /**
     * 绘制数值的textView
     *
     * @param canvas 画笔
     */
    private void drawTextValue(Canvas canvas) {
        radarPaint.setColor(Color.WHITE);

        radarPaint.setTextSize(radarValueSize);
        radarPaint.setStyle(Paint.Style.STROKE);

        int x;
        int y;
        float textValueWidth;

        for (int i = 0; i < data.length; i++) {
            x = getPoint(i).x;
            y = getPoint(i).y;

            //值的文字宽度
            textValueWidth = radarPaint.measureText(String.valueOf(data[i]));

            if (i == 1) {
                //计算索引为0位置的x轴坐标， x为多边形当前夹角的坐标，将其减去文字宽度，是为了保持居中显示
                //然后向又位移1/3个半径是让该文字不遮挡雷达图
                //同时同索引位的属性的文字也会向左位移1/3个半径，保证居中显示
                x = (int) (x - textValueWidth / 2 + radius / 3);
                y += radarValueSize;
            } else if (i == 2) {
                //让文字坐标向左位移文字宽度的一半，保持居中显示
                x -= textValueWidth / 2;
                y = (int) (y + radarTextMargin + radarValueSize + radarDescSize);

            } else if (i == 3) {
                x -= textValueWidth / 2;
                y = (int) (y + radarValueSize + radarTextMargin + radarDescSize);

            } else if (i == 4) {
                //计算索引为3位置的x轴坐标， x为多边形当前夹角的坐标，将其减去文字宽度，是为了保持居中显示
                //然后向又位移1/3个半径是让该文字不遮挡雷达图
                //同时同索引位的属性的文字也会向左位移1/3个半径，保证居中显示
                x = (int) (x - textValueWidth / 2 - radius / 3);
                y += radarValueSize;

            } else if (i == 0) {
                x -= textValueWidth / 2;
                y -= radarTextMargin;
            }

            canvas.drawText(String.valueOf(data[i]), x, y, radarPaint);
        }
    }


    /**
     * 绘制雷达图多边形
     */
    private void drawPolygon(Canvas canvas) {
        radarPaint.setColor(lineColor);
        //设置只绘制边框
        radarPaint.setStyle(STROKE);

        radarPaint.setAntiAlias(true);

        //设置只绘制边框
        radarPaint.setStyle(STROKE);

        Path path = new Path();


        for (int i = radarCount; i > 0; i--) {     //根据radarCount的数量绘制相同个数的多边形
            for (int j = 0; j < data.length; j++) {
                if (j == 0) {
                    path.moveTo(getPoint(j, i * 1f / radarCount).x, getPoint(j, i * 1f / radarCount).y);
                } else {
                    path.lineTo(getPoint(j, i * 1f / radarCount).x, getPoint(j, i * 1f / radarCount).y);
                }
            }
            //闭合路径
            path.close();
        }

        canvas.drawPath(path, radarPaint);
    }

    /**
     * 获取雷达图上各个点的坐标
     *
     * @param position 坐标位置（右上角为0，顺时针递增）
     * @return 坐标
     */
    private Point getPoint(int position) {
        return getPoint(position, 1);
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     *
     * @param position 坐标位置
     * @param percent  覆盖区的的百分比
     * @return 坐标
     */
    private Point getPoint(int position, float percent) {

        //计算弧度，将2pi均分成数据大小份
        float radarRadius = (float) (Math.PI * 2 / data.length);

        int x = 0;
        int y = 0;

        float currentRadius;

        //获取当前索引的弧度
        currentRadius = radarRadius * position;

        //根据当前弧度的角度范围来计算x，y的坐标
        if (currentRadius >= 0 && currentRadius <= 90) {
            x = (int) (radarX + radius * Math.sin(currentRadius) * percent);
            y = (int) (radarY - radius * Math.cos(currentRadius) * percent);
        } else if (currentRadius > 90 && currentRadius <= 180) {
            x = (int) (radarX + radius * Math.cos(currentRadius) * percent);
            y = (int) (radarY + radius * Math.sin(currentRadius) * percent);
        } else if (currentRadius > 180 && currentRadius <= 270) {
            x = (int) (radarX - radius * Math.sin(currentRadius) * percent);
            y = (int) (radarY + radius * Math.cos(currentRadius) * percent);
        } else if (currentRadius > 270 && currentRadius <= 360) {
            x = (int) (radarX - radius * Math.sin(currentRadius) * percent);
            y = (int) (radarY - radius * Math.cos(currentRadius) * percent);
        }

        return new Point(x, y);
    }

    /**
     * 绘制连接线
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < data.length; i++) {
            path.reset();
            path.moveTo(radarX, radarY);
            path.lineTo(getPoint(i).x, getPoint(i).y);
            canvas.drawPath(path, radarPaint);
        }
    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas 画布
     */
    private void drawValue(Canvas canvas) {
        radarPaint.setColor(valueColor);

        radarPaint.setAntiAlias(true);
        radarPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        Path path = new Path();

        for (int i = 0; i < data.length; i++) {
            float percent = data[i] / maxValue;

            int x = getPoint(i, percent).x;
            int y = getPoint(i, percent).y;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        //一定要关闭path，不然无法形成回路
        path.close();
        canvas.drawPath(path, radarPaint);
    }


    /**
     * 设置数值,最小长度为3
     *
     * @param data 数据
     */
    public void setData(float[] data) {
        this.data = data;
        postInvalidate();
    }

    /**
     * 设置属性数据
     *
     * @param dataDesc 数据
     */
    public void setDataDesc(String[] dataDesc) {
        this.dataDesc = dataDesc;
        postInvalidate();
    }
}
