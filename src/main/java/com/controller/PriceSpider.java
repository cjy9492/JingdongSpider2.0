package com.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hspcadmin on 2017/1/17.
 */
public class PriceSpider {
    public void price(){
        String sql = null;
        JavaToMysql db1 = null;
        String sql1 = null;
        JavaToMysql db11 = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        sql = "SELECT * FROM `shopping` WHERE `flag`='0'AND `delflag`='0'";
        db1 = new JavaToMysql(sql);//创建DBHelper对象
        List<Shop> shopList1 = new ArrayList<Shop>();
        try {
            ResultSet res= db1.pst.executeQuery(sql);
            while(res.next()){//判断是否还有下一行
                String[] b = Commodity.shop(res.getString("shopid"));
                    sql1 = "INSERT INTO `shoppic`( `shopid`,`email`,`price`,`day`) VALUES ('"+res.getString("shopid")+"','"+res.getString("emailaddress")+"','"+b[1]+"','"+df.format(new Date())+"')";
                    db11 = new JavaToMysql(sql1);//创建DBHelper对象
                    db11.pst.execute(sql1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            db1.close();
            if(db11!=null){
                db11.close();
            }

        }
    }
}
