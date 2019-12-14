<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>道具管理</title>
    <%@ include file="/static/pub/include.jsp" %>
</head>

<body>

<div id="search-panel" class="container-fluid">
    <div id="tt" class="easyui-tabs">
        <div title="道具配置" style="padding:10px">
            <!-- 查询条件信息 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <span class="panel-title"><%--查询条件--%></span>
                            <%--服务器图片地址--%>
                            <input id="field5" name="field5" type="hidden" class="easyui-textbox">
                        </div>
                        <div class="panel-body">
                        </div>
                    </div>
                </div>
            </div>

            <!-- datagrid 信息 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel-body">
                        <!-- 操作按钮区域 -->
                        <div id="tb" style="display:none" class="opt-button-float">
                            <div class="group-button opt-button-float">
                                <button class="layui-btn layui-btn-sm layui-btn-normal" id="add" name="add">
                                    <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
                                </button>
                            </div>
                        </div>

                        <table id="datagrid1" class="easyui-datagrid" title="道具配置"

                               data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										method: 'GET',
										fit:false,
										toolbar: '#tb',
										idField: 'id',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

                            <thead>
                            <tr>
                                <th data-options="field:'id',align:'center',fixed:false,hidden:true">id</th>
                                <th data-options="field:'name',align:'center',fixed:false,width:30">道具名称</th>
                                <th data-options="field:'imgUrl',align:'center',fixed:false,width:60,formatter: function(value,row,index){return showImge(value,row,index)}">道具图片</th>
                                <th data-options="field:'exchangetypeDesc',align:'center',fixed:false,width:30">购买类型
                                </th>
                                <th data-options="field:'dotaRatio',align:'center',fixed:false,width:50">道具比例</th>
                                <th data-options="field:'useareaDesc',align:'center',fixed:false,width:30">使用范围</th>
                                <th data-options="field:'serviceareaDesc',align:'center',fixed:false,width:30">作用范围</th>
                                <th data-options="field:'buyresultsgoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    	购买增加金币
                                </th>
                                <th data-options="field:'useresultsgoldDouble',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                   	 使用增加金币
                                </th>
                                <th data-options="field:'recommendDesc',align:'center',fixed:false,width:30">是否推荐</th>
                                <th data-options="field:'sortid',align:'center',fixed:false,width:30">排序</th>
                                <th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">状态</th>
                                <th data-options="field:'account',align:'center',fixed:false,width:30">操作账号</th>
                                <th data-options="field:'merchant',align:'center',fixed:false,width:60">商户号</th>
                                <th data-options="field:'createDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">创建时间</th>
                                <th data-options="field:'updateDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">修改时间</th>

                                <th data-options="field:'option',align:'center',fixed:false,width:30,formatter: function(value,row,index){return optortion(value,row,index)}">
                                    	操作
                                </th>

                            </tr>
                            </thead>
                        </table>

                    </div>
                </div>
            </div>

        </div>
        <div title="购买记录" style="padding:10px">

            <!-- 查询条件信息 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <span class="panel-title">查询条件</span>
                            <a href="javascript:void(0);" class="pull-right panel-toggle">
                                <i class="iconfont">&#xe603;</i>
                            </a>
                        </div>
                        <div class="panel-body">
                            <form id="search2-form" method="post">
                                <table class="form-table" border="0" cellpadding="1" cellspacing="2">
                                    <tr>
                                        <td class="form-label">
                                            <label for="gameid">玩家ID：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="gameid" name="gameid" class="easyui-numberbox"/>
                                        </td>

                                        <td class="form-label">
                                            <label for="collectdateStartStr">日期查询：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="collectdateStartStr" name="collectdateStartStr"
                                                   class="easyui-datetimebox" editable="false"/>
                                        </td>

                                        <td class="form-label">
                                            <label for="collectdateEndStr">至：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="collectdateEndStr" name="collectdateEndStr"
                                                   class="easyui-datetimebox" editable="false"/>
                                        </td>

                                        <td class="button-group" colspan="2">
                                            <button type="button" class="btn btn-primary" id="search2" name="search2">
                                                <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                                            </button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- datagrid 信息 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel-body">

                        <table id="datagrid2" class="easyui-datagrid" title="购买记录"

                               data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit:false,
										method: 'GET',
										idField: 'recordid',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

                            <thead>
                            <tr>
                                <th data-options="field:'recordid',align:'center',fixed:false,hidden:true">recordid</th>
                                <th data-options="field:'collectdate',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">
                                    购买时间
                                </th>
                                <th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
                                <th data-options="field:'nickname',align:'center',fixed:false,width:30">玩家昵称</th>
                                <th data-options="field:'propertyname',align:'center',fixed:false,width:30">道具名称</th>
                                <th data-options="field:'exchangetypeDesc',align:'center',fixed:false,width:30">购买类型
                                </th>
                                <th data-options="field:'buynum',align:'center',fixed:false,width:30">购买数量</th>
                                <th data-options="field:'currencyDouble',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    消耗货币
                                </th>
                                <th data-options="field:'beforecurrencyDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    购买前货币
                                </th>
                                <th data-options="field:'clinetip',align:'center',fixed:false,width:30">购买地址</th>

                            </tr>
                            </thead>
                        </table>

                    </div>
                </div>
            </div>

        </div>
        <div title="使用记录" style="padding:10px">

            <!-- 查询条件信息 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <span class="panel-title">查询条件</span>
                            <a href="javascript:void(0);" class="pull-right panel-toggle">
                                <i class="iconfont">&#xe603;</i>
                            </a>
                        </div>
                        <div class="panel-body">
                            <form id="search3-form" method="post">
                                <table class="form-table" border="0" cellpadding="1" cellspacing="2">
                                    <tr>
                                        <td class="form-label">
                                            <label for="gameid3">玩家ID：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="gameid3" name="gameid" class="easyui-numberbox"/>
                                        </td>

                                        <td class="form-label">
                                            <label for="usedateStartStr">日期查询：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="usedateStartStr" name="usedateStartStr"
                                                   class="easyui-datetimebox" editable="false"/>
                                        </td>

                                        <td class="form-label">
                                            <label for="usedateEndStr">至：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="usedateEndStr" name="usedateEndStr" class="easyui-datetimebox"
                                                   editable="false"/>
                                        </td>

                                        <td class="button-group" colspan="2">
                                            <button type="button" class="btn btn-primary" id="search3" name="search3">
                                                <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                                            </button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- datagrid 信息 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel-body">
                        <table id="datagrid3" class="easyui-datagrid" title="使用记录"

                               data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit:false,
										method: 'GET',
										idField: 'recordid',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

                            <thead>
                            <tr>
                                <th data-options="field:'recordid',align:'center',fixed:false,hidden:true">recordid</th>
                                <th data-options="field:'usedate',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">
                                    	使用时间
                                </th>
                                <th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
                                <th data-options="field:'nickname',align:'center',fixed:false,width:30">玩家昵称</th>
                                <th data-options="field:'propertyname',align:'center',fixed:false,width:30">道具名称</th>
                                <th data-options="field:'propertycount',align:'center',fixed:false,width:30">使用数量</th>
                                <th data-options="field:'useresultsgoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    	使用增加游戏币
                                </th>
                                <th data-options="field:'lovelinesssend',align:'center',fixed:false,width:60">使用增加魅力值
                                </th>
                                <th data-options="field:'lovelinessrcv',align:'center',fixed:false,width:30">接收增加魅力值
                                </th>
                                <th data-options="field:'clientip',align:'center',fixed:false,width:30">使用地址</th>

                            </tr>
                            </thead>
                        </table>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>


<!-- 弹出页面 -->
<div id="dialog" class="easyui-dialog" closed="true"></div>


<script type="text/javascript">


    $('#tt').tabs({
        onSelect: function (title, index) {
            //alert(title + "==========" + index);
            if (index != 0) {
                var p = $("#datagrid2").datagrid('getPanel');
                p.panel('resize', {
                    height: 430
                });

                var p3 = $("#datagrid3").datagrid('getPanel');
                p3.panel('resize', {
                    height: 430
                });
            }
        }
    });

    //新增
    $("#add").unbind();
    $("#add").bind("click",function(){
        $('#dialog').dialog({
            title: '新增道具信息',
            href: '${pageContext.request.contextPath}/uphold/dota/toDotaAddPage',
            width: 800,
            height: 400,
            closed: false,
            cache: false,
            modal: true,
            collapsible: true,
            maximizable: false,
            resizable: true,
            shadow: true,
            left: 150,
            top:50,
            content: ''
        });
        // $('#dialog').dialog('open'); 注释掉  解决请求两次的问题
    });

    $(function ($) {
        //初始化日期
        $('#collectdateStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
        $('#collectdateEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");
        $('#usedateStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
        $('#usedateEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

        $("#search2").unbind();
        $("#search2").click(function () {
            search2();
        });

        $("#search3").unbind();
        $("#search3").click(function () {
            search3();
        });

        search1();
    });

    //查询
    function search1() {
        $('#datagrid1').datagrid({
            url: '${pageContext.request.contextPath}/uphold/dota/queryDotaWithPage',
        });
        search2();
    }

    function search2() {
        var obj = $("#search2-form").serializeObject();
        $('#datagrid2').datagrid({
            url: '${pageContext.request.contextPath}/uphold/dota/queryBuyDotaWithPage',
            queryParams: {
                collectdateEndStr: obj.collectdateEndStr,
                collectdateStartStr: obj.collectdateStartStr,
                gameid: obj.gameid
            },
			view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
        });
        search3();
    }

    function search3() {
        var obj = $("#search3-form").serializeObject();
        $('#datagrid3').datagrid({
            url: '${pageContext.request.contextPath}/uphold/dota/queryUseDotaWithPage',
            queryParams: {
                usedateEndStr: obj.usedateEndStr,
                usedateStartStr: obj.usedateStartStr,
                gameid: obj.gameid
            },
			view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
        });

    }

    function optortion(value, row, index) {
        var b = "<button class='layui-btn layui-btn-xs layui-btn-warm' onclick='optionEditEvent({0},{1})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{2}</span></button>";
        b = b.format(row.id, row.exchangetype, "编辑");
        return b;
    }

    function optionEditEvent(id, exchangetype) {
        openEditWindown(id, exchangetype);
    }

    //打开 编辑 窗口
    function openEditWindown(id, exchangetype) {
        $('#dialog').dialog({
            title: '编辑道具配置',
            href: '${pageContext.request.contextPath}/uphold/dota/toDotaEditPage',
            dialogParams: {id: id, exchangetype: exchangetype},
            width: 800,
            height: 400,
            closed: false,
            cache: false,
            modal: true,
            collapsible: true,
            maximizable: false,
            resizable: true,
            shadow: true,
            left: 150,
            top: 50,
            content: ''
        },'open');

        // $('#dialog').dialog('open');
    }

    //道具配置 道具图片
    function showImge(value,row,index){
        <%--var img = "<a href='${pageContext.request.contextPath}/static/Upload{0}' target='_blank'><img style='height: 50px;width: 100px;' src='${pageContext.request.contextPath}/static/Upload{1}' /></a>";--%>
        var img = "<a id='prefixPath' href='"+row.imgUrl+"' target='_blank'><img style='height: 50px;width: 100px;' src='"+row.imgUrl+"' /></a>";
        // var img = "<img style='height: 50px;width: 100px;' src='"+row.imgUrl+"' />";
        // var img = "<a id='prefixPath' href='"+s+'{'+'0'+'}'+"' target='_blank'><img style='height: 50px;width: 100px;' src='"+s+'{'+'1'+'}'+"' /></a>";
        img = img.format(row.imgUrl,row.imgUrl);
        return img;
    }

</script>
</body>
</html>

