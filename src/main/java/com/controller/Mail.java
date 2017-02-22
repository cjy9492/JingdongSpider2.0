package com.controller;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Properties;


public class Mail {


        public void sendmail(Shop ss,String name)throws MessagingException, GeneralSecurityException{
            Properties props = new Properties();

            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.qq.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getInstance(props);

            Message msg = new MimeMessage(session);
            try {
                msg.setSubject(MimeUtility.encodeText("降价通知",MimeUtility.mimeCharset("gb2312"), null));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            StringBuilder builder = new StringBuilder();
            builder.append("商品："+ss.getName());
            builder.append("<br>url:" + "http://item.jd.com/"+ss.getId()+".html");
            builder.append("<br>已经降价！");
            builder.append("<br>目前价格："+ss.getPrice());
            builder.append("<br>预期价格："+ss.getMyprice());
            builder.append("<br>添加商品时间："+ss.getAddday());
            builder.append("<br>目标价格达成时间："+name);
            builder.append("<br>花费时间："+Dataout.getDistanceTime(ss.getAddday(),name));

            msg.setContent(builder.toString(), "text/html;charset=UTF-8");
            msg.setFrom(new InternetAddress("你的邮箱地址"));

            Transport transport = session.getTransport();
            transport.connect("smtp.qq.com", "你的邮箱地址", "你的邮箱密码");

            transport.sendMessage(msg, new Address[] { new InternetAddress(ss.getEmail()) });
            transport.close();
        }
    public void sendmail(List<Shop> a)throws MessagingException, GeneralSecurityException{
        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        //ssl服务器
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        try {
            msg.setSubject(MimeUtility.encodeText("程序运行情况通知",MimeUtility.mimeCharset("gb2312"), null));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("系统正在运行!");
        builder.append("<br>目前数据库商品情况如下：");
        for (int i = 0; i <a.size() ; i++) {
            builder.append("<br>状态："+a.get(i).getType());
            builder.append("<br>商品ID："+a.get(i).getId());
            builder.append("<br>商品名称："+a.get(i).getName());
            builder.append("<br>初始价格："+a.get(i).getInprice());
            builder.append("<br>当前价格："+a.get(i).getPrice());
            builder.append("<br>预期价格："+a.get(i).getMyprice());
            if(!"".equals(a.get(i).getInprice())&&!"".equals(a.get(i).getPrice())){
                int x=roll(a.get(i).getInprice());
                int y=roll(a.get(i).getPrice());
                builder.append("<br>价格变动百分比："+ percent(x,y)+"%");
            }
            builder.append("<br>邮箱："+a.get(i).getEmail());
        }
        msg.setContent(builder.toString(), "text/html;charset=UTF-8");
        msg.setFrom(new InternetAddress("你的邮箱地址"));

        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "你的邮箱地址", "你的邮箱密码");

        transport.sendMessage(msg, new Address[] { new InternetAddress("你的邮箱地址") });
        transport.close();
    }
    public int roll(String s){
        if(s.indexOf(".") > 0){
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return Integer.parseInt(s);
    }
    public String percent(int q,int w){
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) w / (float) q * 100-100);
        return result;
    }

}