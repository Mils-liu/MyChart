package com.mils.mychart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.mils.mychart.color.ChartColor;
import com.mils.mychart.util.DensityUtil;

/**
 * Created by Administrator on 2018/9/24.
 */

public class BarGraphView extends View{
    float leftOffset = 0;
    float marginLeft = DensityUtil.dip2px(getContext(),10);
    float marginBottom = DensityUtil.dip2px(getContext(),10);
    float textSize = DensityUtil.dip2px(getContext(),10);
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
    String unit = "";/*单位*/
    String Yname = "";

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

    public void setYaxis(String unit, String Yname){
        this.unit = unit;
        this.Yname = Yname;
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
        spacing = (width-2*marginLeft)*0.3f/(values.length+1);
        bgWidth = (width-2*marginLeft)*0.7f/(values.length);
        maxHeight = height-5*marginBottom;
        leftOffset = (unit!=""&&Yname!="") ? DensityUtil.dip2px(getContext(),10):0;
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
            textPaint.setTextSize(textSize);
            textPaint.setTextAlign(Paint.Align.CENTER);
            paint.setStyle(Paint.Style.STROKE);
            path.moveTo(marginLeft+leftOffset,marginBottom);
            path.rLineTo(0,height-3*marginBottom);
            canvas.drawPath(path,paint);
            if(unit!=""&&Yname!=null){
                canvas.drawTextOnPath(Yname+"/"+unit,path,-(height-10*marginBottom)/2,textSize*3/2,textPaint);
            }
            path.rLineTo(width-2*marginLeft,0);
            canvas.drawPath(path,paint);

            /*绘制柱子和文字*/
            startXposition = marginLeft+spacing+leftOffset;
            startYposition = marginBottom+height-3*marginBottom;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ChartColor.VIOLETRED);

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
                canvas.drawText(String.valueOf(value),startXposition+bgWidth/2,top-marginBottom/2,textPaint);
                canvas.drawText(datas[i],startXposition+bgWidth/2,startYposition+marginBottom*3/2,textPaint);
                startXposition = startXposition + spacing + bgWidth;
                i++;
            }
        }
    }
}
