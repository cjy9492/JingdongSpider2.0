<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
    <meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="http://cdn.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
    <script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="http://cdn.hcharts.cn/highstock/highstock.js"></script>
    <script src="http://cdn.hcharts.cn/highmaps/highmaps.js"></script>
    <title>价格走势</title>
</head>
<script>
    function emaill(){
        document.content.action="email";
        document.content.submit();
    }
    function check(){
        document.content.action="check";
        document.content.submit();
    }
    $(function () {
        $('#container').highcharts({
            chart: {
                type: 'line'
            },
            title: {
                text: ${title}
            },
           /* subtitle: {
                text: 'Source: WorldClimate.com'
            },*/
            xAxis: {
                categories: ${month}
            },
            yAxis: {
                title: {
                    text: '价格'
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },
            series: [{
                name: '商品',
                data: ${price}
            }]
        });
    });
</script>
<body>
<div id="container" style="width: 100%;height:80%"></div>
<form action="" method="post" name="content">
<div style="display:none;">
    商品ID   :<input name="shopid" id="shopid"placeholder="商品ID" value="${shopid}">
    预期价格:<input name="prace" id="prace"placeholder="心理价位">
    邮箱地址:<input name="email" id="email"placeholder="你的邮箱" value="${email}">
</div>
    <div style="text-align:center">
    <select id="month"  name="month">
        <option value="1">一月</option>
        <option value="2">二月</option>
        <option value="3">三月</option>
        <option value="4">四月</option>
        <option value="5">五月</option>
        <option value="6">六月</option>
        <option value="7">七月</option>
        <option value="8">八月</option>
        <option value="9">九月</option>
        <option value="10">十月</option>
        <option value="11">十一月</option>
        <option value="12">十二月</option>
    </select>
    <input type="submit" value="查询"onclick="check()">
        </div>
    </form>

<br>
<br>
<div style="text-align:center"> <input value="返回列表" type="submit" onclick="emaill()" ></div>

</body>
</html>