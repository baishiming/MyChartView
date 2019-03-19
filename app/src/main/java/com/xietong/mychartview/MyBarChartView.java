package com.xietong.mychartview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author : baishiming
 * Time : 2019/3/18
 * e_mail :
 * function:
 * 1. 首先画 y轴.
 *    先确定坐标原点 x\y轴起点
 *
 */
public class MyBarChartView extends View {

    private Context mContext;

    //y轴 文字宽度
    private int mXTextWidth = 77;//dp 需要转换
    //x轴的长度
    private int mXLength = 246;//dp 需要转换
    //x轴 刻度 个数
    private int mXScaleCount = 5;
    //x轴 刻度 高度
    private int mXScaleHeith = 10;//dp 需要转换

    private int keduTextSpace = 10;//刻度与文字之间的间距

    //绘制柱形图的坐标起点
    private int startX;
    private int startY;

    private Paint mPaintLline;
    private Paint mPaintText;
    private Paint mPaintBar;


    private int mTextSize = 12;//dp

    private List<String> yAxisList = new ArrayList<>();
    private List<Integer> xAxisList = new ArrayList<>();
    private List<Integer> xAxisList2 = new ArrayList<>();

    private int keduWidth = 20; //坐标轴上横向标识线宽度
    private int keduSpace = 46; //每个刻度之间的间距 dp

    //刻度递增的值
    private int valueSpace = 40;

    //柱状条对应的颜色数组
    private int[] colors;

    public MyBarChartView(Context context) {
        this(context,null);
    }

    public MyBarChartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyBarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        colors = new int[]{ContextCompat.getColor(context, R.color.color_07f2ab),
                ContextCompat.getColor(context, R.color.color_79d4d8),
                ContextCompat.getColor(context, R.color.color_4388bc),
                ContextCompat.getColor(context, R.color.color_07f2ab),
                ContextCompat.getColor(context, R.color.color_4388bc)};

        yAxisList = Arrays.asList("1月份", "2月份", "3月份", "4月份", "5月份");
        xAxisList = Arrays.asList(10,10,15,25,30);
        xAxisList2 = Arrays.asList(19,25,15,10,20);
        valueSpace = 5;
        startX = dip2px(mContext,mXTextWidth);
        // + top 间距 10dp
        startY = dip2px(mContext,keduSpace) * (yAxisList.size() - 1) + dip2px(mContext,10);


        //绘制柱状图的画笔
        mPaintBar = new Paint();
        mPaintBar.setStyle(Paint.Style.FILL);
        mPaintBar.setStrokeWidth(4);
        //设置边缘特殊效果
        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(
                1, BlurMaskFilter.Blur.INNER);
        mPaintBar.setMaskFilter(PaintBGBlur);

        //绘制直线的画笔
        mPaintLline = new Paint();
        mPaintLline.setColor(ContextCompat.getColor(context, R.color.color_274782));
        mPaintLline.setAntiAlias(true);
        mPaintLline.setStrokeWidth(2);

        //绘制文字的画笔
        mPaintText = new Paint();
        mPaintText.setTextSize(dip2px(mContext,mTextSize));
        mPaintText.setColor(ContextCompat.getColor(context, R.color.color_a9c6d6));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int topY = startY - (yAxisList.size() - 1) * dip2px(mContext,keduSpace);
        //从下往上绘制Y 轴
        canvas.drawLine(startX, startY + dip2px(mContext,keduWidth),
                startX, topY,
                mPaintLline);

        //x 轴箭头 两个斜线
        canvas.drawLine(startX, topY,
                startX - dip2px(mContext,5),
                topY + dip2px(mContext,6), mPaintLline);
        canvas.drawLine(startX, topY,
                startX + dip2px(mContext,5),
                topY + dip2px(mContext,6), mPaintLline);



        int xLength = dip2px(mContext,mXLength);
        //从左往右上绘制X 轴
        int xEnd = xLength + dip2px(mContext,mXTextWidth);
        int yEnd = startY +  dip2px(mContext,keduWidth);
        canvas.drawLine(startX, yEnd,
                xEnd, yEnd,
                mPaintLline);

        //x 轴箭头 两个斜线
        canvas.drawLine(xEnd, yEnd,
                xEnd - dip2px(mContext,5),
                yEnd - dip2px(mContext,6), mPaintLline);
        canvas.drawLine(xEnd, yEnd,
                xEnd - dip2px(mContext,5),
                yEnd + dip2px(mContext,6), mPaintLline);


        int mXScaleWidth = xLength/(mXScaleCount + 1 );
        //绘制 X轴上的刻度
        for (int i = 0; i < mXScaleCount; i++) {
            int x = startX + mXScaleWidth*(i+1);
            canvas.drawLine(x, yEnd,
                    x, yEnd - dip2px(mContext,mXScaleHeith),
                    mPaintLline);
        }

        //绘制Y轴的文字
        for (int i = 0; i < yAxisList.size(); i++) {
            //绘制Y轴的文字
            Rect textRect = new Rect();
            mPaintText.getTextBounds(yAxisList.get(i), 0,
                    yAxisList.get(i).length(),
                    textRect);
            canvas.drawText(yAxisList.get(i),
                    (startX - dip2px(mContext,keduWidth)) - textRect.width() - dip2px(mContext,keduTextSpace),
                    startY - (i + 1) * dip2px(mContext,keduSpace) + dip2px(mContext,keduSpace),
                    mPaintText);

            //绘制柱状条

            int initY1 = startY - dip2px(mContext,itemSpace) * (i + 1 ) - i * dip2px(mContext,itemWidth);
            int initY2 = startY - dip2px(mContext,itemSpace) * (i + 2 ) - i * dip2px(mContext,itemWidth);

            float right1 = (float) (xEnd - (xAxisList.get(i) * (xLength/(mXScaleCount) * 1.0 / valueSpace)));
            float right2 = (float) (xEnd - (xAxisList2.get(i) * (xLength/(mXScaleCount) * 1.0 / valueSpace)));

            mPaintBar.setColor(colors[0]);
            int x1 = initY1 - dip2px(mContext,itemWidth);
            canvas.drawRect(startX, x1,
                    right1, initY1,
                    mPaintBar);
            Log.e("bsm",startX + "--" + x1 + "-right1-"+right1 + "--" + initY1);
            mPaintBar.setColor(colors[1]);
            canvas.drawRect(startX, initY2,
                    right2, x1,
                    mPaintBar);
            Log.e("bsm",startX + "--" + initY2 + "-right2-"+right2 + "--" + x1);
        }


    }


    private int itemSpace = 23;//柱状条之间的间距
    private int itemWidth = 11;//柱状条的宽度


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
