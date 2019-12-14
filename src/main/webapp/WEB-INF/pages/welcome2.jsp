<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>欢迎页</title>
    <%@ include file="/static/pub/include.jsp" %>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/font-awesome/css/font-awesome.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/layui/css/layui.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/jqadmin.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/main.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/font/iconfont.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/style.css" type="text/css"></link>
    <link rel="icon" href="${pageContext.request.contextPath}/asset/logo.png" type="image/x-icon"/></link>

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/jqadmin/js/main.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/echarts/echarts.min.js"></script>

    <script>var $ContextPath = '<c:url value="/"/>';</script>

</head>

<body>

<div id="search-panel" class="container-fluid">

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">全局统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    <form id="global-form" method="post" data-options="novalidate:true" >
                        全局统计
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">每日统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    每日统计
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">在线统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    在线统计
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">分布统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    分布统计
                </div>
            </div>
        </div>
    </div>

</div>




<script type="text/javascript">



    //收缩面板
    $('.panel-toggle').unbind();
    $('.panel-toggle').bind("click", function() {
        var obj = $(this).parent('.panel-heading').next('.panel-body');
        if (obj.css('display') == "none") {
            $(this).find('i').html('&#xe603;');
            obj.slideDown();
        } else {
            $(this).find('i').html('&#xe604;');
            obj.slideUp();
        }
    });


</script>
</body>
</html>








