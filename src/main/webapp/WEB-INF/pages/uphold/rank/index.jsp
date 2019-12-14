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
    <title>排名管理</title>
    <%@ include file="/static/pub/include.jsp" %>
</head>

<style type="text/css">
    .panel .datagrid .panel-htop .easyui-fluid {
        width: 100%;
    }
    .panel-header {
        width: 100%;
    }
</style>

<body>

<div id="search-panel" class="container-fluid">
    <div id="tt" class="easyui-tabs" style="width:100%;">
        <div title="排行配置" style="padding:10px">
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
                            <form id="search1-form" method="post">
                                <table class="form-table" border="0" cellpadding="1" cellspacing="2">
                                    <tr>
                                        <td class="form-label">
                                            <label for="ranktype">排行榜排名：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="ranktype" name="ranktype" class="easyui-combobox" editable="false"
                                                   data-options="panelHeight:'auto', valueField:'id',textField:'text',
													   	data:[
													   		{id: 1,text: '第一名'},{id: 2,text: '第二名'},{id: 3,text: '第三名'},
													   		{id: 4,text: '第4-10名'},{id: 5,text: '第11-20名'},{id: 6,text: '第21-50名'},
													   		{id: 7,text: '第51-100名'}
													   	]"/>
                                        </td>

                                        <td class="button-group" colspan="2">
                                            <button type="button" class="btn btn-primary" id="search1" name="search1">
                                                <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                                            </button>
                                            <button type="button" class="btn btn-warning" id="reset1" name="reset1">
                                                <span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
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

                        <!-- 操作按钮区域 -->
                        <div id="tb" style="display:none" class="opt-button-float">
                            <div class="group-button opt-button-float">

                                <button class="layui-btn layui-btn-sm layui-btn-normal" id="add" name="add">
                                    <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
                                </button>
                                <button class="layui-btn layui-btn-sm layui-btn-danger" id="remove" name="remove">
                                    <i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
                                </button>

                            </div>
                        </div>

                        <!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
                        <table id="datagrid1" class="easyui-datagrid" title="排行配置"

                               data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit: false,
										method: 'GET',
										toolbar: '#tb',
										idField: 'configid',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

                            <thead>
                            <tr>
                                <th data-options="field:'ck',checkbox:true"></th>
                                <th data-options="field:'configid',align:'center',fixed:false,hidden:true">center</th>
                                <th data-options="field:'typeidDesc',align:'center',fixed:false,width:30">排行榜类型</th>
                                <th data-options="field:'ranktypeDesc',align:'center',fixed:false,width:30">排行榜排名</th>
                                <th data-options="field:'goldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    	奖励金币数
                                </th>
                                <th data-options="field:'collectdate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">
                                   	 配置时间
                                </th>

                                <th data-options="field:'merchant',align:'center',fixed:false,width:60">商户号</th>
                                <th data-options="field:'account',align:'center',fixed:false,width:30">操作账号</th>
                                <th data-options="field:'createdDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">
                                    	创建时间
                                </th>
                                <th data-options="field:'updatedDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">
                                    	修改时间
                                </th>

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
        <div title="奖励记录" style="padding:10px;">
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
                                            <label for="typeid">排行榜类型：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="typeid" name="typeid" class="easyui-combobox"
                                                   data-options="panelHeight:'auto', valueField:'id',textField:'text',required: true,
													   	data:[
													   		{id: 2,text: '胜局排行榜'}
													   	]" value="2"/>
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

                        <table id="datagrid2" title="奖励记录" class="easyui-datagrid"

                               data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit: false,
										method: 'GET',
										idField: 'dateid',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

                            <thead>
                            <tr>
                                <th data-options="field:'dateid',align:'center',fixed:false,hidden:true">dateid</th>
                                <th data-options="field:'collectdate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">
                                    	发奖时间
                                </th>
                                <th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
                                <th data-options="field:'nickname',align:'center',fixed:false,width:30">游戏昵称</th>
                                <th data-options="field:'typeidDesc',align:'center',fixed:false,width:30">排行榜类型</th>
                                <th data-options="field:'ranknum',align:'center',fixed:false,width:30,sortable:false">
                                    	排行榜排名
                                </th>
                                <th data-options="field:'rankvalue',align:'center',fixed:false,width:30">累计金币/累计胜局</th>
                                <th data-options="field:'goldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    	奖励金币数
                                </th>
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
<div id="dialog" style="overflow-x: hidden" class="easyui-dialog" closed="true"></div>


<script type="text/javascript">

    $('#tt').tabs({
        onSelect: function (title, index) {
            if (index == 1) {
                var p = $("#datagrid2").datagrid('getPanel');
                p.panel('resize', {
                    height: 430
                });
            }
        }
    });


    $(function ($) {
        //初始化日期
        $('#collectdateStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
        $('#collectdateEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

        search1();

        //查询
        $("#search1").unbind();
        $("#search1").click(function () {
            search1();
        });
        $("#search2").unbind();
        $("#search2").click(function () {
            search2();
        });

        //点击重置
        $("#reset1").unbind();
        $("#reset1").bind("click", function () {
            $('#search1-form').form('clear');
            search1();
        });

        //新增
        $("#add").unbind();
        $("#add").bind("click", function () {
            $('#dialog').dialog({
                title: '新增系统消息',
                href: '${pageContext.request.contextPath}/uphold/rank/toRankAddPage',
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
            });
            $('#dialog').dialog('open');
        });

        //删除
        $("#remove").unbind();
        $("#remove").bind("click", function () {
            removeByids();
        });

        //收缩面板
        $('.panel-toggle').unbind();
        $('.panel-toggle').bind("click", function () {
            var obj = $(this).parent('.panel-heading').next('.panel-body');
            if (obj.css('display') == "none") {
                $(this).find('i').html('&#xe603;');
                obj.slideDown();
            } else {
                $(this).find('i').html('&#xe604;');
                obj.slideUp();
            }
        });


    });

    //查询
    function search1() {
        var obj = $("#search1-form").serializeObject();
        // console.log("第一个传参:" + obj.ranktype);
        $('#datagrid1').datagrid({
            url: '${pageContext.request.contextPath}/uphold/rank/queryRankWithPage',
            queryParams: {
                ranktype: obj.ranktype
            },
            view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
        });
        search2();
    }

    

    function search2() {
        var obj = $("#search2-form").serializeObject();
        $('#datagrid2').datagrid({
            url: '${pageContext.request.contextPath}/uphold/rank/queryRankAwardWithPage',
            queryParams: {
                collectdateEndStr: obj.collectdateEndStr,
                collectdateStartStr: obj.collectdateStartStr,
                gameid: obj.gameid,
                typeid: obj.typeid
            },
            view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
        });

    }


    //操作按钮
    function optortion(value, row, index) {
        var a = $.rowFormater('configid', row, 'edit', {edit: '编辑'});
        return a;
    }

    function optionEditEvent(id) {
        openEditWindown(id);
    }

    //打开 编辑 窗口
    function openEditWindown(id) {
        $('#dialog').dialog({
            title: '编辑排行配置',
            href: '${pageContext.request.contextPath}/uphold/rank/toRankEditPage',
            dialogParams: {configid: id},
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
        });

        $('#dialog').dialog('open');
    }

    function removeByids() {
        var rows = $('#datagrid1').datagrid("getSelections");
        if (rows.length == 0) {
            $.messager.alert('警告', '请选择需要操作的记录');
            return;
        }

        $.messager.confirm('确认', "确定要删除选中的记录吗", function (r) {
            if (r) {
                //拼接主键
                var nmArr = [];
                $.each(rows, function (index, o) {
                    nmArr.push(o.configid);
                });
                //提交到后台
                $.ajax({
                    url: "${pageContext.request.contextPath}/uphold/rank/removeRankByIds?configids=" + nmArr,
                    type: 'DELETE',
                    success: function (resp) {
                        if (resp.success) {

                            //提示操作成功
                            $.messager.alert({
                                title: '提示',
                                msg: '删除成功',
                                icon: 'info',
                                fn: function () {
                                    // 刷新列表
                                    $('#datagrid1').datagrid('load');
                                    //清除之前勾选的行
                                    $('#datagrid1').datagrid('clearChecked');
                                }
                            });

                        } else {
                            $.messager.alert('警告', '删除失败');
                        }
                    }
                });
            }
        });
    }


</script>
</body>
</html>

