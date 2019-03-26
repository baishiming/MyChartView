package com.xietong.mychartview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
    private int showMax = 4;

    //绘制柱形图的坐标起点
    private int startX;
    private int startY;

    private Paint mPaintLline;
    private Paint mPaintText;
    private Paint mPaintBar;


    private int mTextSize = 12;//dp

    private List<ChartViewBean> yAxisList = new ArrayList<>();

    private int keduWidth = 20; //坐标轴上横向标识线宽度

    private int itemHeight = 0; //每个item 的高度(通过计算获取)

    private int itemSpace = 23;//柱状条之间的间距
    private int itemWidth = 11;//柱状条的宽度
    private int itemCount = 2;//2018,2910

    private int topSpace = 10;
    //刻度递增的值
    private int valueSpace = 0;//通过计算获取

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


        ChartViewBean bean = new ChartViewBean();
        bean.setKey("专家种类");
        bean.setIsRise(1);
        bean.setRate(35.5);
        bean.setLastYear(35);
        bean.setThisYear(40);
        bean.setUnit("个");
        bean.setMax(50);

        ChartViewBean bean1 = new ChartViewBean();
        bean1.setKey("种植面积");
        bean1.setIsRise(-1);
        bean1.setRate(45);
        bean1.setLastYear(60);
        bean1.setThisYear(35);
        bean1.setUnit("万亩");
        bean1.setMax(70);
        yAxisList.add(bean);
        yAxisList.add(bean1);


        valueSpace = 5;
        startX = dip2px(mContext,mXTextWidth);
        itemHeight = itemSpace + itemWidth * itemCount;
        // itemHeight * count + itemSpace + top 间距 10dp
        startY = dip2px(mContext, itemHeight) * (yAxisList.size())
                + dip2px(mContext,itemSpace)
                + dip2px(mContext,topSpace);

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

        int topY = startY
                - (yAxisList.size()) * dip2px(mContext, itemHeight)
                - dip2px(mContext,itemSpace);
        //从下往上绘制Y 轴
        canvas.drawLine(startX, startY,
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
        int yEnd = startY ;
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

        //绘制文字 环比
        int compareX = startX + mXScaleWidth * mXScaleCount;
        Rect compareTextRect = new Rect();
        String compareString = "环比";
        mPaintText.getTextBounds(compareString, 0,
                compareString.length(),
                compareTextRect);
        //计算文字的 x 坐标 y坐标
        int compareY = topY + compareTextRect.height();

        Log.e("bsm","compareY=="+compareY);
        canvas.drawText(compareString,
                compareX,
                compareY,
                mPaintText);



        //绘制Y轴的文字
        for (int i = 0; i < yAxisList.size(); i++) {
            //绘制Y轴的文字
            Rect textRect = new Rect();
            mPaintText.getTextBounds(yAxisList.get(i).getKey(), 0,
                    yAxisList.get(i).getKey().length(),
                    textRect);
            //计算文字的 x 坐标 y坐标
            int textY = startY - (i + 1) * dip2px(mContext, itemHeight) + textRect.height();

            Log.e("bsm","textY=="+textY);
            canvas.drawText(yAxisList.get(i).getKey(),
                    (startX - textRect.width() - dip2px(mContext,keduTextSpace)),
                    textY,
                    mPaintText);

            //绘制柱状条 从下想上绘制
            //确定第一个柱第一个点的y坐标(左上点)的位置(起点y坐标-间距个数-柱的高度*个数)
            int firstY1 = startY - dip2px(mContext,itemSpace) * (i + 1 ) - ((i+1)*2 - 1) * dip2px(mContext,itemWidth);
            int secondY1 = firstY1 - dip2px(mContext,itemWidth);

            //数据最多不能超过x轴的第四个刻度, 计算 比例
            //x柱图到第四个刻度的距离
            int showLength = xLength/(mXScaleCount+1) * showMax;

            float right1 = (float) (startX + showLength * (yAxisList.get(i).getThisYear()*1.0/yAxisList.get(i).getMax()));
            float right2 = (float) (startX + showLength * (yAxisList.get(i).getLastYear()*1.0/yAxisList.get(i).getMax()));


            Log.e("bsm","startY=="+startY);
            Log.e("bsm","firstY1=="+firstY1);
            Log.e("bsm","secondY1=="+secondY1);
            Log.e("bsm","itemHeight"+dip2px(mContext, itemHeight)+"");

            //第一个柱第二个点的y坐标(右下点)的位置
            mPaintBar.setColor(colors[0]);
            int firstY2 = firstY1 + dip2px(mContext,itemWidth);
            canvas.drawRect(startX, firstY1,
                    right1, firstY2,
                    mPaintBar);

            //第二个柱的第一个点 是第一个柱第二个点
            mPaintBar.setColor(colors[1]);
            int senodY2 = secondY1 +  dip2px(mContext,itemWidth);
            canvas.drawRect(startX, secondY1,
                    right2, senodY2,
                    mPaintBar);

            //绘制(thisYear)Y轴后面的文字
            Rect textRectAxis = new Rect();
            String thisYearString = yAxisList.get(i).getThisYear() + yAxisList.get(i).getUnit();
            mPaintText.getTextBounds(thisYearString, 0,
                    thisYearString.length(),
                    textRectAxis);
            //计算文字的 x 坐标 y坐标
            float textAxisX = (float) (startX + showLength * (yAxisList.get(i).getThisYear()*1.0/yAxisList.get(i).getMax()));

            Log.e("bsm","textY=="+textY);
            canvas.drawText(thisYearString,
                    textAxisX,
                    firstY2,
                    mPaintText);


            //绘制Y轴后面的文字
            Rect textRectAxis2 = new Rect();
            String lastYearString = yAxisList.get(i).getLastYear() + yAxisList.get(i).getUnit();
            mPaintText.getTextBounds(lastYearString, 0,
                    lastYearString.length(),
                    textRectAxis2);
            //计算文字的 x 坐标 y坐标
            float textAxisX2 = (float) (startX + showLength * (yAxisList.get(i).getLastYear()*1.0/yAxisList.get(i).getMax()));

            Log.e("bsm","textY=="+textY);
            canvas.drawText(lastYearString,
                    textAxisX2,
                    senodY2,
                    mPaintText);

            //画图片 画比例
            Bitmap bitmap = null;
            //判断图片
            if(yAxisList.get(i).getIsRise()>0){
                bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_compare_up);
            }else if(yAxisList.get(i).getIsRise()<0) {
                bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_compare_down);
            }else {
                bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_compare_unchange);
            }

            canvas.drawBitmap(bitmap,compareX,secondY1,mPaintLline);
            //画图片 后面的比例 文字
            Rect bitmapText = new Rect();
            String compareRateString = yAxisList.get(i).getRate() + "%";
            mPaintText.getTextBounds(compareRateString, 0,
                    compareRateString.length(),
                    bitmapText);
            //计算文字的 x 坐标 y坐标
            float bitmapTextX = compareX + bitmap.getWidth();

            Log.e("bsm","textY=="+textY);
            canvas.drawText(compareRateString,
                    bitmapTextX,
                    senodY2,
                    mPaintText);
        }

    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int index = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                Log.e("bsm","ACTION_MOVE x=="+x+",y=="+y);
                break;
            case MotionEvent.ACTION_DOWN:
                Log.e("bsm","ACTION_DOWN x=="+x+",y=="+y);

                for (int i = 0; i < yAxisList.size(); i++) {
                    if(y < (startY - ((dip2px(mContext, itemHeight)*i + dip2px(mContext,topSpace))))
                            && y > (startY - ((dip2px(mContext, itemHeight) * (i+1) + dip2px(mContext,topSpace))))){
                        index = i;
                        break;
                    }

                }
                if(index!=-1){
                    Toast.makeText(mContext,"index=="+index,Toast.LENGTH_SHORT).show();
                    index = -1;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e("bsm","ACTION_UP x=="+x+",y=="+y);
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
