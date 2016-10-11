package com.example.weiyu.audiobarchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/10/11.
 */
public class AudioBarChart extends View {

    private int mRectCount;   //音频矩形的数量
    private int mRectHeight;  //音频矩形的高
    private int mRectWidth;   //音频矩形的宽
    private Paint mRectPaint; //音频矩形的画笔
    private int topColor,downColor;  //渐变颜色的两种
    private int offset;        //偏移量
    private int mSpeed;        //频率速度
    private int mWidth;

    public AudioBarChart(Context context) {
        super(context);
    }

    public AudioBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPaint(context,attrs);
    }



    public AudioBarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPaint(context,attrs);
    }

    private void setPaint(Context context, AttributeSet attrs) {
        //将属性存储到TypeArray中
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.AudioBarChart);
        mRectPaint = new Paint();
        //添加画笔的基础颜色
        mRectPaint.setColor(ta.getColor(R.styleable.AudioBarChart_topColor,
                ContextCompat.getColor(context,R.color.top_color)));
        //添加矩形渐变色的上面部分、下面部分
        topColor = ta.getColor(R.styleable.AudioBarChart_topColor,
                ContextCompat.getColor(context,R.color.top_color));
        downColor = ta.getColor(R.styleable.AudioBarChart_downColor,
                ContextCompat.getColor(context,R.color.down_color));
        //矩形的数量
        mRectCount = ta.getInt(R.styleable.AudioBarChart_count,10);
        //重绘的时间间隔，也就是变化速度
        mSpeed = ta.getInt(R.styleable.AudioBarChart_speed,300);
        //每个矩形的间隔
        offset = ta.getInt(R.styleable.AudioBarChart_offset,5);

        //回收TypeArray
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //渐变效果
        LinearGradient mLinearGradient;
        //画布的宽
        //获取画布的宽
        mWidth = getWidth();
        //获取矩形的最大高度
        mRectHeight = getHeight();
        //获取单个矩形的宽度（减去的部分为到右边界到的间距
        mRectWidth = (int)(mWidth *0.6/mRectCount);
        //实例化一个线性渐变
        mLinearGradient = new LinearGradient(
                0,
                0,
                mRectWidth,
                mRectHeight,
                topColor,
                downColor,
                Shader.TileMode.CLAMP
        );
        //给画笔添加渐变属性
        mRectPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        double mRandom;
        float currentHeight;
        for(int i = 0;i<mRectCount;i++){
            mRandom = Math.random();
            currentHeight = (float) (mRectHeight*mRandom);
            canvas.drawRect(
                    (float)(mWidth*0.4/2 + mRectWidth*i + offset),
                    currentHeight,
                    (float)(mWidth*0.4/2 + mRectWidth*(i+1)),
                    mRectHeight,
                    mRectPaint);
        }
        //view的延迟重绘
        postInvalidateDelayed(mSpeed);
    }

}
