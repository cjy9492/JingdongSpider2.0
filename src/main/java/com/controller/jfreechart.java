package com.controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.TextAnchor;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by hspcadmin on 2017/1/17.
 */
public class jfreechart {

    public static String genLineChart(String shopid,String email,HttpSession session)throws Exception{
        // A网站的访问量统计
        TimeSeries timeSeries=new TimeSeries("price-date");
        // 添加数据  如果你是从数据库中获取数据，你就写个循环塞值就行了。
        String sql = null;
        JavaToMysql db1 = null;
        sql = "SELECT * FROM `shoppic` WHERE `shopid`='"+shopid+"'AND `email`="+"'"+email+"'";
        db1 = new JavaToMysql(sql);//创建DBHelper对象
        ResultSet res= db1.pst.executeQuery(sql);
        while(res.next()) {//判断是否还有下一行
            /*int[] date = datechance(res.getString("day"));
            timeSeries.add(new Day(date[2],date[1],date[0]), Double.parseDouble(res.getString("price")));*/
        }

        // 定义时间序列的集合
        TimeSeriesCollection lineDataset=new TimeSeriesCollection();
        lineDataset.addSeries(timeSeries);

        JFreeChart chart= ChartFactory.createTimeSeriesChart("", "", "price", lineDataset, true, true, true);

        //设置主标题
        /*chart.setTitle(new TextTitle("商品价格趋势图", new Font("黑体", Font.ITALIC, 15)));*/
        //设置子标题
      /*  TextTitle subtitle = new TextTitle("2016年度", new Font("黑体", Font.BOLD, 12));*/
        Font font=new Font("微软雅黑",Font.BOLD,18);//测试是可以的
        Font font2=new Font("微软雅黑",Font.BOLD,15);
        chart.getTitle().setFont(font);
       /* chart.addSubtitle(subtitle);*/
        chart.setAntiAlias(true);

        //设置时间轴的范围。
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis dateaxis = (DateAxis)plot.getDomainAxis();
        dateaxis.setDateFormatOverride(new java.text.SimpleDateFormat("DDay"));
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY,1));
        //设置取现XY中文
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(font2);
        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(font2);
        domainAxis.setTickLabelFont(font2);//设置x轴坐标上的字体
        chart.getLegend().setItemFont(font2);//X轴方块字体设置
        //设置曲线是否显示数据点
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer)plot.getRenderer();
        xylinerenderer.setBaseShapesVisible(true);

        //设置曲线显示各数据点的值
        XYItemRenderer xyitem = plot.getRenderer();
        xyitem.setBaseItemLabelsVisible(true);
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12));
        plot.setRenderer(xyitem);

        //最后返回组成的折线图数值
        String fileName= ServletUtilities.saveChartAsPNG(chart, 600, 370, session);

        return fileName;
    }
    public static String datechance(String date){
       String a=date.substring(8,10);
        return a;
    }
    public static ArrayList genLine(String shopid, String email, String day) throws Exception {
        String sql = null;
        JavaToMysql db1 = null;
        ArrayList monthLine = new ArrayList();
        if("error".equals(day)){
            java.util.Date currentTime = new java.util.Date();
            int month = currentTime.getMonth();
            day= String.valueOf(month);
        }
        if(Integer.parseInt(day)<10)
        {
            day= "2017-0"+day+"%";
        }else {
            day= "2017-"+day+"%";
        }
        sql = "SELECT * FROM `shoppic` WHERE `shopid`='"+shopid+"'AND `email`="+"'"+email+"'AND `day` LIKE '"+day+"'";
        db1 = new JavaToMysql(sql);//创建DBHelper对象
        ResultSet res= db1.pst.executeQuery(sql);
        while(res.next()) {//判断是否还有下一行
            monthLine.add(datechance(res.getString("day")));
        }
        return monthLine;
    }

}
