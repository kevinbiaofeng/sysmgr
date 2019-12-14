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
        <!--顶部统计数据预览 -->
        <div class="row">

            <%--用户总数--%>
            <div class="col-xs-6 col-sm-4 col-md-2">
                <section class="panel">
                    <div class="symbol bgcolor-blue"> <i class="iconfont">&#xe672;</i>
                    </div>
                    <div class="value tab-menu">
                        <a href="javascript:;" data-url="#" data-parent="true" data-title="用户总数"><i class="iconfont " data-icon='&#xe672;'></i>
                            <h1>1000</h1>
                        </a>

                        <a href="javascript:;" data-url="#" data-parent="true" data-title="用户总数"> <i class="iconfont " data-icon='&#xe672;'></i><span>用户总数</span></a>

                    </div>
                </section>
            </div>

            <%--今日新增--%>
            <div class="col-xs-6 col-sm-4 col-md-2">
                <section class="panel">
                    <div class="symbol bgcolor-commred"> <i class="iconfont">&#xe674;</i>
                    </div>
                    <div class="value tab-menu">
                        <a href="javascript:;" data-url="#" data-parent="true" data-title="今日新增"> <i class="iconfont " data-icon='&#xe674;'></i>
                            <h1>10</h1>
                        </a>

                        <a href="javascript:;" data-url="#" data-parent="true" data-title="今日新增"> <i class="iconfont " data-icon='&#xe674;'></i><span>今日新增</span></a>

                    </div>
                </section>
            </div>

            <%--今日活跃--%>
            <div class="col-xs-6 col-sm-4 col-md-2">
                <section class="panel">
                    <div class="symbol bgcolor-orange"> <i class="iconfont">&#xe638;</i>
                    </div>
                    <div class="value tab-menu">
                        <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日活跃"> <i class="iconfont " data-icon='&#xe638;'></i>
                            <h1>10</h1>
                        </a>
                        <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日活跃"> <i class="iconfont " data-icon='&#xe638;'></i><span>今日活跃</span></a>
                    </div>
                </section>
            </div>


            <%--昨日新增--%>
            <div class="col-xs-6 col-sm-4 col-md-2">
                <section class="panel">
                    <div class="symbol bgcolor-dark-green"> <i class="iconfont">&#xe674;</i>
                    </div>
                    <div class="value tab-menu">
                        <a href="javascript:;" data-url="#" data-parent="true" data-title="昨日新增"> <i class="iconfont " data-icon='&#xe674;'></i>
                            <h1>10</h1>
                        </a>

                        <a href="javascript:;" data-url="#" data-parent="true" data-title="昨日新增"> <i class="iconfont " data-icon='&#xe674;'></i><span>昨日新增</span></a>

                    </div>
                </section>
            </div>

        </div>
    </div>

<%--<div class="row">


    <div class="col-xs-6 col-sm-4 col-md-2">
        <section class="panel">
            <div class="symbol bgcolor-commred"> <i class="iconfont">&#xe674;</i>
            </div>
            <div class="value tab-menu">
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日注册"> <i class="iconfont " data-icon='&#xe674;'></i>
                    <h1>10</h1>
                </a>

                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日注册"> <i class="iconfont " data-icon='&#xe674;'></i><span>今日注册</span></a>

            </div>
        </section>
    </div>

    <div class="col-xs-6 col-sm-4 col-md-2">
        <section class="panel">
            <div class="symbol bgcolor-dark-green"> <i class="iconfont">&#xe6bc;</i>
            </div>
            <div class="value tab-menu">
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="文章总数"> <i class="iconfont " data-icon='&#xe6bc;'></i>
                    <h1>10</h1>
                </a>
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="文章总数"> <i class="iconfont " data-icon='&#xe6bc;'></i><span>文章总数</span></a>
            </div>
        </section>
    </div>

    <div class="col-xs-6 col-sm-4 col-md-2">
        <section class="panel">
            <div class="symbol bgcolor-yellow-green"> <i class="iconfont">&#xe649;</i>
            </div>
            <div class="value tab-menu">
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日新增"> <i class="iconfont " data-icon='&#xe649;'></i>
                    <h1>10</h1>
                </a>
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日新增"> <i class="iconfont " data-icon='&#xe649;'></i><span>今日新增</span></a>
            </div>
        </section>
    </div>

    <div class="col-xs-6 col-sm-4 col-md-2">
        <section class="panel">
            <div class="symbol bgcolor-orange"> <i class="iconfont">&#xe638;</i>
            </div>
            <div class="value tab-menu">
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="评论总数"> <i class="iconfont " data-icon='&#xe638;'></i>
                    <h1>10</h1>
                </a>
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="评论总数"> <i class="iconfont " data-icon='&#xe638;'></i><span>评论总数</span></a>
            </div>
        </section>
    </div>

    <div class="col-xs-6 col-sm-4 col-md-2">
        <section class="panel">
            <div class="symbol bgcolor-yellow"> <i class="iconfont">&#xe669;</i>
            </div>
            <div class="value tab-menu">
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日评论"> <i class="iconfont " data-icon='&#xe669;'></i>
                    <h1>10</h1>
                </a>
                <a href="javascript:;" data-url="user-info.html" data-parent="true" data-title="今日评论"> <i class="iconfont " data-icon='&#xe669;'></i><span>今日评论</span></a>
            </div>
        </section>
    </div>

</div>--%>



<script type="text/javascript">



</script>
</body>
</html>








