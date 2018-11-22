package com.mils.mychart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mils.mychart.color.ChartColor;

/**
 * Created by Administrator on 2018/9/24.
 */

public class BarGraphView extends View{
    float margin = 0;/*整体柱状图边距*/
    float spacing = 0;/*柱子之间的边距*/
    float startXposition = 0;/*第一个柱状图左下角x初始值*/
    float startYposition = 0;/*第一个柱状图左下角y初始值*/
    int width = 0;/*View的总宽度*/
    int height = 0;/*View的总高度*/
    float bgWidth = 0;/*柱子的宽度*/
    float maxHeight = 0;/*最高的柱子的高度*/
    Path path;
    Paint paint;
    Paint textPaint;
    String[] datas;
    float[] values;/*传入的数值*/
    float max = 0;/*最大数值*/

    public BarGraphView(Context context){
        super(context);
    }
    public BarGraphView(Context context, AttributeSet attrs){
        super(context, attrs,0);
    }

    public BarGraphView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs,defStyleAttr);
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public void setData(String[] datas){
        this.datas = datas;
    }

    private void init(){
        /*获取最大数值*/
        for (float value : values) {
            if(value >= max){
                max = value;
            }
        }
        width = getWidth();
        height = getHeight();
        margin = Math.min(width,height)*0.05f;
        spacing = (width-2*margin)*0.3f/(values.length+1);
        bgWidth = (width-2*margin)*0.7f/(values.length);
        maxHeight = height-5*margin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (values.length>0 && values.length==datas.length){
            init();
            /*绘制xy轴*/
            path = new Path();
            paint = new Paint();
            textPaint = new Paint();
            textPaint.setTextSize(margin);
            textPaint.setTextAlign(Paint.Align.CENTER);
            paint.setStyle(Paint.Style.STROKE);
            path.moveTo(margin,margin);
            path.rLineTo(0,height-4*margin);
            path.rLineTo(width-2*margin,0);
            canvas.drawPath(path,paint);

            /*绘制柱子和文字*/
            startXposition = margin+spacing;
            startYposition = margin+height-4*margin;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ChartColor.LAKEPURPLE);
            /*柱子的左上角与右下角*/
            float left = 0;
            float top = 0;
            float right = 0;
            float bottom = 0;
            int i = 0;
            for (float value : values) {
                left = startXposition;
                top = startYposition - value/max * maxHeight;
                right = startXposition + bgWidth;
                bottom = startYposition;
                canvas.drawRect(left,top,right,bottom,paint);
                canvas.drawText(String.valueOf(value),startXposition+bgWidth/2,top-margin/2,textPaint);
                canvas.drawText(datas[i],startXposition+bgWidth/2,startYposition+margin*3/2,textPaint);
                startXposition = startXposition + spacing + bgWidth;
                i++;
            }
        }
    }
}
