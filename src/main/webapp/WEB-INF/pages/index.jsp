<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>${app.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/bootstrap/3.3.7/css/bootstrap.min.css?<%=new Date()%>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/font-awesome/css/font-awesome.css?<%=new Date()%>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/layui/css/layui.css?<%=new Date()%>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/jqadmin.css?<%=new Date()%>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/font/iconfont.css?<%=new Date()%>" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/main.css?<%=new Date()%>" type="text/css"/>
    <link rel="icon" href="${pageContext.request.contextPath}/static/assets/favicon.ico?<%=new Date()%>" type="image/x-icon"/>
</head>
<body>
<ul class='right-click-menu'>
    <li><a href='javascript:;' data-event='fresh'>刷新</a></li>
    <li><a href='javascript:;' data-event='close'>关闭</a></li>
    <li><a href='javascript:;' data-event='other'>关闭其它</a></li>
    <li><a href='javascript:;' data-event='all'>全部关闭</a></li>
</ul>
<div class="layui-layout layui-layout-admin">

    <div class="layui-header">
        <!-- logo区域 -->
        <div class="jqadmin-logo-box">
            <a class="logo" href="javascript:;" title=""><img src="" alt="" width="60" height="60"></a>
            <span>平台管理系统</span>
            <div class="menu-type" data-type="2"><i class="iconfont">&#xe61a;</i></div>
        </div>

        <!-- 主菜单区域 -->
        <div class="jqadmin-main-menu">
            <ul class="layui-nav clearfix" id="menu" lay-filter="main-menu">
            </ul>
        </div>
        
        <!-- 头部右侧导航 -->
        <div class="header-right">
            <ul class="layui-nav jqadmin-header-item right-menu">
                <li class="layui-nav-item first">
                    <a href="javascript:;">
                        <cite><shiro:principal property="name"/></cite>
                        <span class="layui-nav-more"></span>
                    </a>

                    <dl class="layui-nav-child">
                        <%--<dd><a href="javascript:;" onclick="_addTab({'icon':'&#xe6bc;','href':'${pageContext.request.contextPath}/userinfo?userId=1','title':'用户信息'})" href-url="#"><i class="fa fa-user fa-fw"></i>用户信息</a></dd>
                        <dd><a href="javascript:;" onclick="_addTab({'icon':'&#xe6bc;','href':'${pageContext.request.contextPath}/setting?userId=1','title':'设置'})" href-url='#'><i class="fa fa-gear fa-fw"></i>设置</a></dd>--%>
                        <dd><a href="${pageContext.request.contextPath}/authority/shiroLogout" href-url='#'><i class="fa fa-sign-out fa-fw"></i>退出</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item tab-menu">
                	<a class="msg" href="javascript:;" data-url="#" data-title="站内信息">
                		<!-- <i class="iconfont" data-icon='&#xe63c;'>&#xe63c; </i>
                		<span class="msg-num">1</span> -->
                	</a>
                </li>
            </ul>
            <button title="全屏" class="layui-btn layui-btn-small layui-btn-radius my-tips" style="position: absolute; top: 14px; right:45px;background: black">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
            </button>
            <button title="刷新" class="layui-btn layui-btn-small layui-btn-radius fresh-btn" style="position: absolute; top: 14px;background: black">
                <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
            </button>
        </div>
    </div>

    <!-- 左侧导航-->
    <div class="layui-side layui-bg-black jqamdin-left-bar">
        <div class="layui-side-scroll">
            <!--子菜单项-->
            <div id="submenu"></div>
        </div>
    </div>

    <!-- 左侧侧边导航结束 -->
    <!-- 右侧主体内容 -->
    <div class="layui-body jqadmin-body">

        <div class="layui-tab layui-tab-card jqadmin-tab-box" id="jqadmin-tab" lay-filter="tabmenu" lay-notAuto="true">
            <ul class="layui-tab-title">
                <li class="layui-this" id="admin-home" lay-id="0" fresh=1><i class="iconfont">&#xe600;</i><em>首页</em>
                </li>
            </ul>
            <div class="menu-btn" title="显示左则菜单">
                <i class="iconfont">&#xe616;</i>
            </div>
            <div class="tab-move-btn">
                <span>更多<i class="iconfont">&#xe604;</i></span>
            </div>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <iframe class="jqadmin-iframe" data-id='0' src="${pageContext.request.contextPath}/welcome"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>
<ul class="menu-list" id="menu-list"></ul>
</body>


<%--<script id="menu-tpl" type="text/html" data-params='{"url":"${pageContext.request.contextPath}/static/config/mymenu2_auth.json","listid":"menu","icon":"true"}'>
    {{# layui.each(d.list, function(index, item){ }}
    <li class="layui-nav-item {{# if(index==0){ }}layui-this{{# } }}">
        <a href="javascript:;" data-title="{{item.name}}"><i
                class="iconfont">{{item.iconfont}}</i><span>{{item.name}}</span></a>
    </li>
    {{# }); }}
</script>--%>

<script id="menu-tpl" type="text/html" data-parent="true" data-params='{"url":"${pageContext.request.contextPath}/authority/menuResource","listid":"menu","icon":"true","isFresh":"true"}'>
    {{# layui.each(d.list, function(index, item){ }}
    <li class="layui-nav-item {{# if(index==0){ }}layui-this{{# } }}">
        <a href="javascript:;" data-title="{{item.name}}"><i class="iconfont">{{item.iconfont}}</i><span>{{item.name}}</span></a>
    </li>
    {{# }); }}
</script>

<script id="submenu-tpl" type="text/html">
    {{# layui.each(d.list, function(index, menu){ }}
    <div class="sub-menu">
        <ul class="layui-nav layui-nav-tree">
            {{# layui.each(menu.sub, function(index, submenu){ }} {{# if(submenu.sub && submenu.sub.length>0){ }}
            <li class="layui-nav-item" data-bind="0">
                <a href="javascript:;" data-title="{{submenu.name}}">
                    <i class="iconfont">{{submenu.iconfont}}</i>
                    <span>{{submenu.name}}</span>
                    <em class="layui-nav-more"></em>
                </a>
                <dl class="layui-nav-child">
                    {{# layui.each(submenu.sub, function(index, secMenu){ }}
                    <dd>
                        <a href="javascript:;" data-url="${pageContext.request.contextPath}{{secMenu.url}}" data-title="{{secMenu.name}}">
                            <i class="iconfont " data-icon='{{secMenu.iconfont}}'>{{secMenu.iconfont}}</i>
                            <span>{{secMenu.name}}</span>
                        </a>
                    </dd>
                    {{# }); }}
                </dl>
            </li>
            {{# }else{ }}
            <li class="layui-nav-item">
                <a href="javascript:;" data-url="${pageContext.request.contextPath}{{submenu.url}}" data-title="{{submenu.name}}">
                    <i class="iconfont" data-icon='{{submenu.iconfont}}'>{{submenu.iconfont}}</i>
                    <span>{{submenu.name}}</span>
                </a>
            </li>
            {{# } }} {{# }); }}
        </ul>
    </div>
    {{# }); }}
</script>
<script id="menu-list-tpl" type="text/html">

    {{# layui.each(d.list, function(index, item){ }}
    <li>
        <a href="javascript:;" data-url="{{item.href}}" data-title="{{item.title}}">
            <i class="iconfont " data-icon='{{item.icon}}'>{{item.icon}}</i>
            <span>{{item.title}}</span>
        </a>
    </li>
    {{# }); }}

</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/layui/layui.js"></script>
<script>
	var appid = "payplatform_sysmgr_menu";//appId
	var baseUrl = "${pageContext.request.contextPath}/static/assets/jqadmin/js/";
    layui.config({
        base: baseUrl,
        version: "1.3.4r2"
    }).extend({
        elem: 'jqmodules/elem',
        tabmenu: 'jqmodules/tabmenu',
        modal: 'jqmodules/modal',
        jqmenu: 'jqmodules/jqmenu'
    });
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/index2.js"></script>

</html>