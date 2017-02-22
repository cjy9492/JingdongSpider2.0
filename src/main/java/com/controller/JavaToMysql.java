package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JavaToMysql {
    
    public static final String url = "你的数据库url";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "你的数据库名称";
    public static final String password = "你的数据库密码";

    public Connection conn = null;
    public PreparedStatement pst = null;



    public JavaToMysql(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}