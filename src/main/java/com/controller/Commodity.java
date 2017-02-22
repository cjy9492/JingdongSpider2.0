package com.controller;

import org.json.JSONObject;

/**
 * Created by hspcadmin on 2016/8/11.
 */
public class Commodity {
    public static String  price(String result){
            String name=null;
        if("skuids input error".equals(result)){
            return "error";
        }
        else {
            result=result.replace("[","");
            result=result.replace("]","");
            JSONObject studentJSONObject = new JSONObject(result);
           /* System.out.println(studentJSONObject.toString());*/
         /*   String[] obj=result.split(",");
        String[] obj1=obj[1].split("\"");*/
            name = studentJSONObject.getString("p");
        /*System.out.println(obj1[3]);*/
        return name;}
    }
    public static String title(String result){
        String[] obj=result.split("<title>");
        String[] obj1=obj[1].split("</title>");
       /* System.out.print(obj1[0]);*/
        return obj1[0];
    }
    public static String[] shop(String result){
        String url1 = "http://item.jd.com/"+result+".html";
        String url = "http://p.3.cn/prices/get?skuid=J_"+result;
        String content = Spider.SendGet(url);
        String title=Spider.SendGet(url1);
        String a[]=new String[64];
        a[0]=title(title);
        /*a[0]="暂时无法显示";*/
        a[1]=price(content);
       /* a[1]="199.00";*/
        return a;
    }

}
