package com.controller;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hspcadmin on 2017/1/16.
 */
public class TimeSpider {
    public void timeSpider(){
        List<Shop> shopList1 = new ArrayList<Shop>();
        String sql = null;
        JavaToMysql db1 = null;
        sql = "SELECT * FROM `shopping` WHERE `delflag`='0'";
        db1 = new JavaToMysql(sql);//创建DBHelper对象
        try {
            ResultSet res= db1.pst.executeQuery(sql);
            while(res.next()){//判断是否还有下一行
                Shop ss = new Shop();

                if( "0".equals(res.getString("flag"))){
                    ss.setType("运行中");
                }else {
                    ss.setType("已停止");
                }
                ss.setName( res.getString("name"));
                ss.setMyprice( res.getString("myprice"));
                ss.setId( res.getString("shopid"));
                ss.setEmail( res.getString("emailaddress"));
                ss.setPrice(res.getString("nowprice"));
                ss.setInprice(res.getString("price"));
                shopList1.add(ss);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            db1.close();
        }
        try {
            new Mail().sendmail(shopList1);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
