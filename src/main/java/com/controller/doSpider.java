package com.controller;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hspcadmin on 2017/1/12.
 */

public class doSpider {
    public void Spider() {
        String sql = null;
        JavaToMysql db1 = null;
        String sql1 = null;
        JavaToMysql db11 = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        sql = "SELECT * FROM `shopping` WHERE `flag`='0'AND `delflag`='0'";
        db1 = new JavaToMysql(sql);//创建DBHelper对象
        List<Shop> shopList1 = new ArrayList<Shop>();
        try {
            ResultSet res= db1.pst.executeQuery(sql);
            while(res.next()){//判断是否还有下一行
                Shop ss = new Shop();
                String[] b = Commodity.shop(res.getString("shopid"));
                ss.setAddday(res.getString("adddate"));
                ss.setMyprice( res.getString("myprice"));
                ss.setId( res.getString("shopid"));
                ss.setEmail( res.getString("emailaddress"));
                ss.setName(res.getString("name"));
                ss.setType(res.getString("emailflag"));
                ss.setPrice(b[1]);
                shopList1.add(ss);
                if(!res.getString("nowprice").equals(b[1])){
                    sql1 = "UPDATE   `shopping` SET `nowprice`='"+b[1]+"' WHERE  `delflag`='0'AND `shopid`="+"'"+res.getString("shopid")+"'"+"AND `emailaddress`="+"'"+res.getString("emailaddress")+"'";
                    db11 = new JavaToMysql(sql1);//创建DBHelper对象
                    db11.pst.execute(sql1);
                }
                String[] obj = res.getString("lowprice").split("\\.");
                String[] obj1 = b[1].split("\\.");
                if(Integer.parseInt(obj1[0])<Integer.parseInt(obj[0])){
                    sql1 = "UPDATE   `shopping` SET `lowprice`='"+b[1]+"' WHERE  `delflag`='0'AND `shopid`="+"'"+res.getString("shopid")+"'"+"AND `emailaddress`="+"'"+res.getString("emailaddress")+"'";
                    db11 = new JavaToMysql(sql1);//创建DBHelper对象
                    db11.pst.execute(sql1);
                }

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
        for (int i = 0; i <shopList1.size() ; i++) {
            String[] obj = shopList1.get(i).getPrice().split("\\.");
            if(Integer.parseInt(obj[0])<Integer.parseInt(shopList1.get(i).getMyprice())){
                String sql2 = null;
                JavaToMysql db12 = null;
                try {
                    if ("0".equals(shopList1.get(i).getType())){
                        String time = df.format(new Date());
                        new Mail().sendmail(shopList1.get(i),time);
                        sql2 = "UPDATE   `shopping` SET `flag`='1' ,`emailflag`='1',`senddate`='"+time+"' WHERE  `delflag`='0'AND `shopid`="+"'"+shopList1.get(i).getId()+"'"+"AND `emailaddress`="+"'"+shopList1.get(i).getEmail()+"'";
                        db12 = new JavaToMysql(sql2);//创建DBHelper对象
                        db12.pst.execute(sql2);

                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    if(db12!=null){
                        db12.close();
                    }
                }
            }
        }

    }
}
