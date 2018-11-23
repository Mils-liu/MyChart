package com.mils.mychart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mils.mychart.color.ChartColor;
import com.mils.mychart.util.DensityUtil;

/**
 * Created by Administrator on 2018/9/20.
 */

public class PieChartView extends View{

    Paint paint;
    Path path;
    Paint textPaint;
    private String TAG = "PieChart";
    private float x,y;
    float sum = 0;/*所有数值的总和，用于计算百分比*/
    /*颜色*/
    int[] colors = {ChartColor.RED,ChartColor.ORANGERED,ChartColor.ORANGE,ChartColor.ORANGEYELLOW,ChartColor.YELLOW,ChartColor.YELLOWGREEN,ChartColor.GREEN,ChartColor.BLUEGREEN,ChartColor.BLUE,ChartColor.BLUEVIOLET,ChartColor.VIOLET,ChartColor.VIOLETRED};
    final int textColor = Color.GRAY;/*文字颜色*/
    float stripValue = DensityUtil.dip2px(getContext(),8);/*第二折线长度*/
    float[] values;/*传入的数值*/
    float maxValue = 0;/*最大值*/
    int maxIndex;/*最大值的下标*/
    float startAngle=0;/*开始的角度*/
    float endAngle=0;/*最后的角度*/
    float angle=0;/*划过的角度*/
    float xOffset=0;/*最大圆饼的x轴偏移*/
    float yOffset=0;/*最大圆饼的y轴偏移*/
    float text2strip = 0;
    final float PIE_PERCENT = 0.25f;
    final float STRIP2_PERCENT = 0.07f;
    final float OFFSET_PERCENT = 0.05f;
    final float TEXT_PERCENT = 0.1f;
    final float TEXT2STRIP_PERCENT = 0.01f;
    float offset=0;
    float textSize = 0;

    public void setDatas(String[] datas) {
        this.datas = datas;
    }

    String[] datas;

    public void setValues(float[] values) {
        this.values = values;
    }



    public PieChartView(Context context){
        super(context);
    }
    public PieChartView(Context context, AttributeSet attrs){
        super(context, attrs,0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs,defStyleAttr);
    }

    private void init(){
        Log.d(TAG,"init");
        sum = 0;
        startAngle = 0;
        endAngle = 0;
        /*获取数值总和，最大数值及其下标*/
        for (int i=0;i<values.length;i++) {
            sum+=values[i];
            if(values[i]>=maxValue){
                maxValue = values[i];
                maxIndex = i;
                Log.d(TAG,"maxValue:"+maxValue);
                Log.d(TAG,"maxIndex:"+maxIndex);
            }
        }
        Log.d(TAG,"sum:"+sum);
        textPaint = new Paint();
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);/*开启抗锯齿*/


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(values.length>0&&values.length==datas.length){
            init();
            int width = getWidth();
            int height = getHeight();
            int r = Math.min(width,height)/2;
            Log.d("PieChart","width:"+width);
            Log.d("PieChart","height:"+height);
            Log.d("PieChart","R:"+r);
            offset = r * OFFSET_PERCENT;
            textSize = r * TEXT_PERCENT;
            text2strip = r * (TEXT2STRIP_PERCENT+STRIP2_PERCENT);

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(textColor);
            paint.setStrokeWidth(DensityUtil.px2dip(getContext(),5));
            textPaint.setTextSize(textSize);
            int R = (int)(r- r*0.15f);

            /*绘制数值*/
            for (int i=0; i<values.length; i++){
                float percent = values[i]/sum;
                startAngle = endAngle;
                endAngle+=360 * percent;
                angle = endAngle - startAngle;
                Log.d(TAG,"startAngle:"+startAngle);
                Log.d(TAG,"endAngle:"+endAngle);
                Log.d(TAG,"angle:"+angle);
                /*判断是否在绘制最大值的文字*/
                xOffset = maxIndex==i?offset*(float)Math.cos(Math.toRadians(startAngle+angle/2)):0;
                yOffset = maxIndex==i?offset*(float)Math.sin(Math.toRadians(startAngle+angle/2)):0;
                Log.d(TAG,"xOffset:"+xOffset);
                Log.d(TAG,"yOffset:"+yOffset);
                x = (R)*(float)Math.cos(Math.toRadians(startAngle+angle/2));
                y = (R)*(float)Math.sin(Math.toRadians(startAngle+angle/2));
                path.moveTo(width/2+xOffset,height/2+yOffset);
                path.rLineTo(x,y);
                canvas.drawPath(path,paint);
                if(startAngle+angle/2>90&&startAngle+angle/2<270){
                    path.rLineTo(-stripValue,0);
                    textPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.format("%.2f", percent*100)+"%",width/2+x-text2strip+xOffset,height/2+y+yOffset,textPaint);
                }else {
                    path.rLineTo(stripValue,0);
                    textPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText((String.format("%.2f", percent*100))+"%",width/2+x+text2strip+xOffset,height/2+y+yOffset,textPaint);
                }
                canvas.drawPath(path,paint);
            }

            /*绘制饼图和文字*/
            startAngle=endAngle=angle=0;
            paint.setStyle(Paint.Style.FILL);
            RectF oval=new RectF();                     //RectF对象
            for (int i=0; i<values.length; i++){
                float percent = values[i]/sum;
                startAngle = endAngle;
                endAngle+=360 * percent;
                angle = endAngle - startAngle;
                Log.d(TAG,"OstartAngle:"+startAngle);
                Log.d(TAG,"OendAngle:"+endAngle);
                Log.d(TAG,"Oangle:"+angle);
                xOffset = maxIndex==i?offset*(float)Math.cos(Math.toRadians(startAngle+angle/2)):0;
                yOffset = maxIndex==i?offset*(float)Math.sin(Math.toRadians(startAngle+angle/2)):0;
                oval.left=width/2-r+r*PIE_PERCENT+xOffset;  //左边
                oval.top=height/2-r+r*PIE_PERCENT+yOffset;  //上边
                oval.right=width/2+r-r*PIE_PERCENT+xOffset; //右边
                oval.bottom=height/2+r-r*PIE_PERCENT+yOffset;
                Log.d(TAG+"C","i:"+i);
                Log.d(TAG+"C","colorIndex:"+i%5);
                /*防止最后一部分与第一部分的颜色重叠*/
                if (i==values.length-1&&i%ChartColor.COLORNUMMBER==0){
                    Log.d(TAG+"C","!!!");
                    paint.setColor(colors[i%ChartColor.COLORNUMMBER+1]);
                }else {
                    paint.setColor(colors[i%ChartColor.COLORNUMMBER]);
                }
                Log.d(TAG,"color:"+colors[i%ChartColor.COLORNUMMBER]);
                canvas.drawArc(oval,startAngle,angle,true,paint);
                x = (R)*(float)Math.cos(Math.toRadians(startAngle+angle/2));
                y = (R)*(float)Math.sin(Math.toRadians(startAngle+angle/2));
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(datas[i],width/2+x/2,height/2+y/2,textPaint);
            }
        }
    }
}

