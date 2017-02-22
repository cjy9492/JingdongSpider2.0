package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by hspcadmin on 2017/1/12.
 */
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");//设置日期格式
    String sql = null;
    JavaToMysql db1 = null;
    @RequestMapping("/add")
    public String add(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
         req.setCharacterEncoding("UTF-8");
         String shopid = req.getParameter("shopid");
         String prace = req.getParameter("prace");
         String email = req.getParameter("email");
        String sql1 = null;
        JavaToMysql db2 = null;
        if(!isemaill(email)||!isNumeric(shopid)||!isNumeric(prace)||email.equals("")||prace.equals("")||shopid.equals("")){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        if(isDouble(shopid,email)){
            req.setAttribute("name", "已经存在相同id,无须重复添加！");
            return "index";
        }
        if(!Commodity.price(Spider.SendGet("http://p.3.cn/prices/get?skuid=J_"+shopid)).equals("error")){
            String[] b = Commodity.shop(shopid);
            String time = df.format(new Date());
            sql = "INSERT INTO `shopping`( `shopid`,`myprice`,`emailaddress`,`name`,`price`,`emailflag`,`adddate`,`nowprice`,`lowprice`,`flag`,`delflag`) VALUES ('"+shopid+"','"+prace+"','"+email+"','"+b[0]+"','"+b[1]+"','"+"0"+"','"+time+"','"+b[1]+"','"+b[1]+"','"+"0"+"','"+"0"+"')";
            db1 = new JavaToMysql(sql);//创建DBHelper对象
            try {
                db1.pst.execute(sql);
                List<Shop> shopList1 = new ArrayList<Shop>();
                sql1 = "SELECT * FROM `shopping` WHERE `delflag`='0'AND `emailaddress`="+"'"+email+"'";
                db2 = new JavaToMysql(sql1);//创建DBHelper对象
                try {
                    ResultSet res= db2.pst.executeQuery(sql1);
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
                        ss.setLowprice(res.getString("lowprice"));
                        ss.setInprice(res.getString("price"));
                        ss.setEmailflag(res.getString("emailflag"));
                        shopList1.add(ss);

                    }
                    req.setAttribute("myprace", shopList1);
                    req.setAttribute("name", "添加成功！");
                    return "index";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    db1.close();db2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            req.setAttribute("name", "添加失败！");
            return "index";
        }
        else {
            req.setAttribute("name", "当前不是有效的id！");
            return "index";
        }

    }
    @RequestMapping("/email")
    public String mail(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        if(!isemaill(email)||email.equals("")){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        List<Shop> shopList1 = new ArrayList<Shop>();
        sql = "SELECT * FROM `shopping` WHERE `delflag`='0'AND `emailaddress`="+"'"+email+"'";
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
                ss.setLowprice(res.getString("lowprice"));
                ss.setInprice(res.getString("price"));
                ss.setEmailflag(res.getString("emailflag"));
                shopList1.add(ss);

            }
            req.setAttribute("name", "查询成功！");
            req.setAttribute("myprace", shopList1);
            return "index";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            db1.close();
        }
        req.setAttribute("name", "没找到对应的数据！");
        return "index";
    }
    @RequestMapping("/del")
    public String del(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String shopid = req.getParameter("shopid");
        String email = req.getParameter("email");
        String sql1 = null;
        JavaToMysql db2 = null;
        if(!isemaill(email)||!isNumeric(shopid)||email.equals("")||shopid.equals("")){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        if(isDouble(shopid,email)){
            String sql = null;
            JavaToMysql db1 = null;
            sql = "UPDATE   `shopping` SET `delflag`='1' WHERE `delflag`='0'AND `shopid`="+"'"+shopid+"'"+"AND `emailaddress`="+"'"+email+"'";
            db1 = new JavaToMysql(sql);//创建DBHelper对象
            try {
                db1.pst.execute(sql);
                List<Shop> shopList1 = new ArrayList<Shop>();
                sql1 = "SELECT * FROM `shopping` WHERE `delflag`='0'AND `emailaddress`="+"'"+email+"'";
                db2 = new JavaToMysql(sql1);//创建DBHelper对象
                try {
                    ResultSet res= db2.pst.executeQuery(sql1);
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
                        ss.setLowprice(res.getString("lowprice"));
                        ss.setInprice(res.getString("price"));
                        ss.setEmailflag(res.getString("emailflag"));
                        shopList1.add(ss);

                    }
                    req.setAttribute("myprace", shopList1);
                    req.setAttribute("name", "删除成功！");
                    return "index";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    db1.close();db2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                db1.close();
            }
        }
        req.setAttribute("name", "删除失败！");
        return "index";
    }
    @RequestMapping("/open")
    public String open(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String shopid = req.getParameter("shopid");
        String email = req.getParameter("email");
        if(!isemaill(email)||!isNumeric(shopid)||email.equals("")||shopid.equals("")){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        if(isDouble(shopid,email)){
            String sql = null;
            JavaToMysql db1 = null;
            String sql1 = null;
            JavaToMysql db2 = null;
            sql = "UPDATE   `shopping` SET `flag`='0',`emailflag`='0'  WHERE  `delflag`='0'AND `shopid`="+"'"+shopid+"'"+"AND `emailaddress`="+"'"+email+"'";
            db1 = new JavaToMysql(sql);//创建DBHelper对象
            try {
                db1.pst.execute(sql);
                List<Shop> shopList1 = new ArrayList<Shop>();
                sql1 = "SELECT * FROM `shopping` WHERE `delflag`='0'AND `emailaddress`="+"'"+email+"'";
                db2 = new JavaToMysql(sql1);//创建DBHelper对象
                try {
                    ResultSet res= db2.pst.executeQuery(sql1);
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
                        ss.setLowprice(res.getString("lowprice"));
                        ss.setInprice(res.getString("price"));
                        ss.setEmailflag(res.getString("emailflag"));
                        shopList1.add(ss);

                    }
                    req.setAttribute("myprace", shopList1);
                req.setAttribute("name", "开始成功！");
                return "index";
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                db1.close();db2.close();
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }}
            req.setAttribute("name", "开始失败！");
        return "index";
    }
    @RequestMapping("/close")
    public String close(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String shopid = req.getParameter("shopid");
        String email = req.getParameter("email");
        if(email.equals("")||shopid.equals("")||(!isemaill(email)||!isNumeric(shopid))){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        if(isDouble(shopid,email)){
            String sql = null;
            JavaToMysql db1 = null;
            String sql1 = null;
            JavaToMysql db2 = null;
            sql = "UPDATE   `shopping` SET `flag`='1' WHERE  `delflag`='0'AND `shopid`="+"'"+shopid+"'"+"AND `emailaddress`="+"'"+email+"'";
            db1 = new JavaToMysql(sql);//创建DBHelper对象
            try {
                db1.pst.execute(sql);
                List<Shop> shopList1 = new ArrayList<Shop>();
                sql1 = "SELECT * FROM `shopping` WHERE  `delflag`='0' AND `emailaddress`="+"'"+email+"'";
                db2 = new JavaToMysql(sql1);//创建DBHelper对象
                try {
                    ResultSet res= db2.pst.executeQuery(sql1);
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
                        ss.setLowprice(res.getString("lowprice"));
                        ss.setInprice(res.getString("price"));
                        ss.setEmailflag(res.getString("emailflag"));
                        shopList1.add(ss);

                    }
                    req.setAttribute("myprace", shopList1);
                    req.setAttribute("name", "停止成功！");
                    return "index";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    db1.close();db2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }}
        req.setAttribute("name", "停止失败！");
        return "index";
    }
    @RequestMapping("/alert")
    public String alert(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String shopid = req.getParameter("shopid");
        String email = req.getParameter("email");
        String newprice = req.getParameter("prace");

        if(!isemaill(email)||!isNumeric(shopid)||email.equals("")||shopid.equals("")){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        if(isDouble(shopid,email)){
            String sql = null;
            JavaToMysql db1 = null;
            String sql1 = null;
            JavaToMysql db2 = null;
            sql = "UPDATE   `shopping` SET `myprice`='"+newprice+"', `emailflag`='0' WHERE  `delflag`='0'AND `shopid`="+"'"+shopid+"'"+"AND `emailaddress`="+"'"+email+"'";
            db1 = new JavaToMysql(sql);//创建DBHelper对象
            try {
                db1.pst.execute(sql);
                List<Shop> shopList1 = new ArrayList<Shop>();
                sql1 = "SELECT * FROM `shopping` WHERE `delflag`='0'AND `emailaddress`="+"'"+email+"'";
                db2 = new JavaToMysql(sql1);//创建DBHelper对象
                try {
                    ResultSet res= db2.pst.executeQuery(sql1);
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
                        ss.setLowprice(res.getString("lowprice"));
                        ss.setInprice(res.getString("price"));
                        ss.setEmailflag(res.getString("emailflag"));
                        shopList1.add(ss);

                    }
                    req.setAttribute("myprace", shopList1);
                    req.setAttribute("name", "修改成功！");
                    return "index";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    db1.close();db2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }}
        req.setAttribute("name", "修改失败！");
        return "index";
    }
    @RequestMapping("/pic")
    public String pic(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String shopid = req.getParameter("shopid");
        String email = req.getParameter("email");
        ArrayList monthLine = new ArrayList();
        ArrayList priceLine = new ArrayList();

        String day = "error";
        if(!isemaill(email)||!isNumeric(shopid)||email.equals("")||shopid.equals("")){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        if(isDouble(shopid,email)){
            try {
            /*   String fileName =jfreechart.genLineChart(shopid,email,session);
                String imageURL=req.getContextPath() + "/chart?filename="+fileName;
                req.getSession().setAttribute("image", imageURL);*/
                String sql = null;
                JavaToMysql db1 = null;

                if("error".equals(day)){
                    java.util.Date currentTime = new java.util.Date();
                    int month = currentTime.getMonth()+1;
                    day= String.valueOf(month);
                    Map mo= new HashMap();
                    mo.put("1","一");
                    mo.put("2","二");
                    mo.put("3","三");
                    mo.put("4","四");
                    mo.put("5","五");
                    mo.put("6","六");
                    mo.put("7","七");
                    mo.put("8","八");
                    mo.put("9","九");
                    mo.put("10","十");
                    mo.put("11","十一");
                    mo.put("12","十二");
                    req.setAttribute("title", "'"+mo.get(day)+"月价格走势图'");
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
                    priceLine.add(res.getString("price"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("month", monthLine);
        req.setAttribute("price", priceLine);

        req.setAttribute("shopid", shopid);
        req.setAttribute("email", email);

        return "jfree";
    }
    @RequestMapping("/check")
    public String check(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String shopid = req.getParameter("shopid");
        String email = req.getParameter("email");
        ArrayList monthLine = new ArrayList();
        ArrayList priceLine = new ArrayList();

        String day = req.getParameter("month");
        if(!isemaill(email)||!isNumeric(shopid)||email.equals("")||shopid.equals("")){
            req.setAttribute("name", "不能使用非数字字符！");
            return "index";
        }
        if(isDouble(shopid,email)){
            try {
            /*   String fileName =jfreechart.genLineChart(shopid,email,session);
                String imageURL=req.getContextPath() + "/chart?filename="+fileName;
                req.getSession().setAttribute("image", imageURL);*/
                String sql = null;
                JavaToMysql db1 = null;
                if("error".equals(day)){
                    java.util.Date currentTime = new java.util.Date();
                    int month = currentTime.getMonth()+1;
                    day= String.valueOf(month);
                }
                Map mo= new HashMap();
                mo.put("1","一");
                mo.put("2","二");
                mo.put("3","三");
                mo.put("4","四");
                mo.put("5","五");
                mo.put("6","六");
                mo.put("7","七");
                mo.put("8","八");
                mo.put("9","九");
                mo.put("10","十");
                mo.put("11","十一");
                mo.put("12","十二");
                req.setAttribute("title", "'"+mo.get(day)+"月价格走势图'");
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
                    priceLine.add(res.getString("price"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("month", monthLine);
        req.setAttribute("price", priceLine);

        req.setAttribute("shopid", shopid);
        req.setAttribute("email", email);

        return "jfree";
    }
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isemaill(String str) {
        Pattern pattern = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
        return pattern.matcher(str).matches();
    }
    public static boolean isDouble(String str,String str1) {
        String sql = null;
        JavaToMysql db1 = null;
        sql = "SELECT * FROM `shopping` WHERE  `delflag`='0'AND `shopid`="+"'"+str+"'"+"AND `emailaddress`="+"'"+str1+"'";
        db1 = new JavaToMysql(sql);//创建DBHelper对象
        try {
            ResultSet res= db1.pst.executeQuery(sql);
            while(res.next()){//判断是否还有下一行
                if(res.getString("id")!=null){
                    return  true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            db1.close();
        }
        return false;

    }
    public static String datechance(String date){
        String a=date.substring(8,10);
        return a;
    }
}
