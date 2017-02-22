package com.controller;

import java.text.NumberFormat;

/**
 * Created by hspcadmin on 2017/1/17.
 */
public class test {
    public static void main(String[] args) {
     /*   String s = "111.00";
        String x = "111.00";
        int q=roll(s);
        int w=roll(x);
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) w / (float) q * 100-100);
        System.out.println("num1和num2的百分比为:" + result + "%");*/
       String a ="100.00";
        int b=90;
        if(Integer.parseInt(a)>b){
            System.out.println(111);
        }
    }
    public static int roll(String s){
        if(s.indexOf(".") > 0){
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return Integer.parseInt(s);
    }
    public static int[] datechance(String date){
        String[] a= date.split("-");
        int[]  b=new int[3];
        for (int i = 0; i <a.length ; i++) {
            b[i]=Integer.parseInt(a[i]);
        }
        return  b;
    }
}
