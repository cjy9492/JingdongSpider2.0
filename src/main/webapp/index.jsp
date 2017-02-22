<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: hspcadmin
  Date: 2016/8/15
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>剁手小助手</title>
    <meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
</head>
<script>
    function addd(){
        document.content.action="add";
        document.content.submit();

    }
    function emaill(){
        document.content.action="email";
        document.content.submit();
    }
    function dellll(id){
        arr=id.split("+");
        document.getElementById("shopid").value=arr[0];
        document.getElementById("email").value=arr[1];
        document.content.action="del";
        document.content.submit();
    }
    function openn(id){
        arr=id.split("+");
        document.getElementById("shopid").value=arr[0];
        document.getElementById("email").value=arr[1];
        document.content.action="open";
        document.content.submit();
    }
    function closee(id){
        arr=id.split("+");
        document.getElementById("shopid").value=arr[0];
        document.getElementById("email").value=arr[1];
        document.content.action="close";
        document.content.submit();
    }
    function jfree(id){
        arr=id.split("+");
        document.getElementById("shopid").value=arr[0];
        document.getElementById("email").value=arr[1];
        document.content.action="pic";
        document.content.submit();
    }
    function alerttt(id){
        arr=id.split("+");
        document.getElementById("shopid").value=arr[0];
        document.getElementById("email").value=arr[1];
        sss=id+"+ssprace";
        document.getElementById("prace").value=document.getElementById(sss).value;
        document.content.action="alert";
        document.content.submit();
    }
    function show1(id){
        sss=id+"+alter";
        if (document.getElementById(sss).style.display=="block"){
            document.getElementById(sss).style.display="none";
        } else {
            document.getElementById(sss).style.display="block";
        }
    }
</script>
<br>
<div style="width:96%;height:auto;padding-top:0px;margin: 0 auto;">

    使用本系统，可以帮助你实时检测京东商品信息，当商品降价时，便会及时通知你，帮助你更好的剁手！<br>
    将要监视的商品ID，期望价格和邮箱输入后单击新增，添加成功后会在下方显示，当价格下降到希望价格后，则会发送邮件给你，非常简单！<br>
    <span style="color:#FF0000">邮箱查询</span>：如果想知道自己有多少任务清单可以输入自己的邮箱单击邮箱查询进行查询。<br>
    <span style="color:#FF0000">新增任务</span>：输入商品ID，期望价格和邮箱输入后单击新增任务，便可以添加任务，并且会在下方显示。如果商品名称和商品当前价格为空，执行邮箱查询即可。<br>
    <span style="color:#FF0000">删除任务</span>：输入商品id和自己的邮箱账号便可删除任务。<br>
    手机端也可访问！ <br>
    <br> <br></div>
<div style="width:96%;height:30%;border:1px solid #4b504c;text-align:center;padding-top:20px;margin: 0 auto;min-height: 160px;max-height: 170px;">
    <form action="" method="post" name="content">
        商品ID   :<input name="shopid" id="shopid"placeholder="商品ID" style="width: 60%;height: 10%;min-height: 23px;"><br><br>
        预期价格:<input name="prace" id="prace"placeholder="心理价位"style="width: 60%;height: 10%;min-height: 23px;"><br><br>
        邮箱地址:<input name="email" id="email"placeholder="你的邮箱"style="width: 60%;height: 10%;min-height: 23px;"><br><br>
        <input value="邮箱查询" type="submit" onclick="emaill()">
        <input value="新增任务" type="submit" onclick="addd()">
    </form>
    </div>
<div style="width:96%;height:auto;padding-top:20px;margin: 0 auto;">
    目前运行状态:${name}${num}<br>
    目前正在运行的信息:
    <c:forEach items="${myprace}" var="user" varStatus="vs">
        <br> 状态：${user.type}<c:if test="${user.type=='运行中'}"><input value="停止任务" id="${user.id}+${user.email}" type="submit" onclick="closee(this.id)"> </c:if>
        <c:if test="${user.type=='已停止'}">        <input value="启动任务" id="${user.id}+${user.email}" type="submit" onclick="openn(this.id)"> </c:if> <input value="价格趋势" id="${user.id}+${user.email}" type="submit" onclick="jfree(this.id)">  <input value="删除任务" id="${user.id}+${user.email}" type="submit" onclick="dellll(this.id)"> <br> <input value="修改价格" id="${user.id}+${user.email}" type="submit" onclick="show1(this.id)"><div id="${user.id}+${user.email}+alter" style="height: 20px;;display: none"> 预期价格:<input name="ssprace" id="${user.id}+${user.email}+ssprace"placeholder="心理价位"style="width: 60%;height: 100%;min-height: 23px;">  <input value="确认修改" id="${user.id}+${user.email}" type="submit" onclick="alerttt(this.id)"></div><br>商品ID：${user.id}<br>商品名称为：${user.name}<br>初始价格为：${user.inprice}<br>当前价格为：${user.price} <br>预期价格为：${user.myprice}<br>历史最低价格为：${user.lowprice}<br>邮件状态：<c:if test="${user.emailflag=='0'}">未发送</c:if><c:if test="${user.emailflag=='1'}">已发送</c:if><br>邮箱：${user.email}<br>
    </c:forEach>
</div>
</body>
</html>
