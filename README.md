# MyChart

![](https://upload-images.jianshu.io/upload_images/7019098-caf1524f3d731f59.png)

![](https://upload-images.jianshu.io/upload_images/7019098-9b0000bda24ed307.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/420/format/webp)
### 1.添加依赖
````java
allprojects{
  respositories{
    jcenter()
    maven{url "https://jitpack.io"}
  }
}

compile "com.github.Mils-Liu:MyChart:v1.1.4";
````
### 2.饼图
在xml里添加布局
````java
<com.mils.mychart.chart.PieChartView
        android:id="@+id/pc"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
````
添加数据与数据名称
````java
float[] values = new float[]{5,2,7,25,40,20,30,10,10,4,10,4};
String[] datas = new String[]{"语文","数学","英语","文综","理综","数学","英语","文综","理综","理综","文综","理综"};
pcView.setValues(values);
pcView.setDatas(datas);
````
设置文字与数值字体大小，默认为10dp
````java
pcView.setPercentSize(10f);
pcView.setTextSize(10f);
````
### 3.柱状图
在xml里添加布局
````java
<com.mils.mychart.chart.BarGraphView
        android:id="@+id/bg"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
````
添加数据与数据名称
````java
float[] values = new float[]{5,2,7,25,40,20,30,10,10,4,10,4};
String[] datas = new String[]{"语文","数学","英语","文综","理综","数学","英语","文综","理综","理综","文综","理综"};
bgView.setValues(values);
bgView.setData(datas);
````
添加y轴坐标单位与名称
````java
bgView.setYaxis("分","分数");        
````
设置柱状图颜色
````java
bgView.setChartColor(ChartColor.ORANGEYELLOW);
````
### END
简书地址：https://www.jianshu.com/p/07b6a6dd1e13
