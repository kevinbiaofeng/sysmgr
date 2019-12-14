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
    <title>每日分享</title>
    <%@ include file="/static/pub/include.jsp" %>
</head>

<body>
<div id="search-panel" class="container-fluid">
    <div id="tt" class="easyui-tabs" style="width:100%;">
        <div title="每日分享配置" style="padding:10px">
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
                        </div>
                    </div>
                </div>
            </div>

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

                        <table id="datagrid1" class="easyui-datagrid" title="每日分享配置"
                               data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit:false,
										method: 'GET',
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
                                <th data-options="field:'daysharelmt',align:'center',fixed:false,width:30">每日分享次数</th>
                                <th data-options="field:'timesharegoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    每日分享获得金币
                                </th>
                                <%--<th data-options="field:'timesharediamond',align:'center',fixed:false,width:50">每日分享获得钻石</th>--%>
                                <th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">状态</th>

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
        <div title="分享记录" style="padding:10px">

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
                                            <input id="gameid" name="gameid" class="easyui-numberbox"
                                            />
                                        </td>

                                        <td class="form-label">
                                            <label for="logtimeStartStr">日期查询：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="logtimeStartStr" name="logtimeStartStr"
                                                   class="easyui-datetimebox"
                                                   editable="false"/>
                                        </td>

                                        <td class="form-label">
                                            <label for="logtimeEndStr">至：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="logtimeEndStr" name="logtimeEndStr" class="easyui-datetimebox"
                                                   editable="false"/>
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

            <div class="row">
                <div class="col-lg-12">
                    <div class="panel-body">
                        <table id="datagrid2" class="easyui-datagrid" title="分享记录"
                               data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit:false,
										method: 'GET',
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
                                <th data-options="field:'logtime',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">
                                    分享时间
                                </th>
                                <th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
                                <th data-options="field:'nickname',align:'center',fixed:false,width:30">玩家昵称</th>
                                <th data-options="field:'timesharegoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                                    分享获得金币
                                </th>
                                <th data-options="field:'remark',align:'center',fixed:false,width:30">备注</th>

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
            if (index != 0) {
                var p = $("#datagrid2").datagrid('getPanel');
                p.panel('resize', {
                    height: 450
                });
            }
        }
    });

    $(function ($) {
        //初始化日期
        $('#logtimeStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
        $('#logtimeEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

        search();
        search2();
        //查询
        $("#search").unbind();
        $("#search").click(function () {
            search();
        });

        //新增
        $("#add").unbind();
        $("#add").bind("click", function () {
            $('#dialog').dialog({
                title: '新增每日分享',
                href: '${pageContext.request.contextPath}/uphold/share/toShareAddPage',
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

        $("#search2").unbind();
        $("#search2").click(function () {
            search2();
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
    function search() {
        var obj = $("#search-form").serializeObject();
        $('#datagrid1').datagrid({
            url: '${pageContext.request.contextPath}/uphold/share/queryShareConfigWithPage',
			view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
        });
        search2();
    }

    function search2() {
        var obj = $("#search2-form").serializeObject();
        $('#datagrid2').datagrid({
            url: '${pageContext.request.contextPath}/uphold/share/queryShareLogWithPage',
            queryParams: {
                logtimeEndStr: obj.logtimeEndStr,
                logtimeStartStr: obj.logtimeStartStr,
                gameid: obj.gameid
            },
			view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
        });
    }


    //操作按钮
    function optortion(value, row, index) {
        var a = $.rowFormater('id', row, 'edit', {edit: '编辑'});
        return a;
    }

    function optionEditEvent(id) {
        openEditWindown(id);
    }

    //打开 编辑 窗口
    function openEditWindown(id) {
        $('#dialog').dialog({
            title: '修改每日分享',
            href: '${pageContext.request.contextPath}/uphold/share/toShareEditPage',
            dialogParams: {id: id},
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
        //console.log(rows);
        if (rows.length == 0) {
            $.messager.alert('警告', '请选择需要操作的记录');
            return;
        }

        $.messager.confirm('确认', "确定要删除选中的记录吗", function (r) {
            if (r) {
                //拼接主键
                var nmArr = [];
                $.each(rows, function (index, o) {
                    nmArr.push(o.kindid);
                });
                //提交到后台
                $.ajax({
                    url: "${pageContext.request.contextPath}/uphold/gamels/removeGamelsByIds?kindids=" + nmArr,
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

    //批量操作
    function batchTransfer(sing) {
        var rows = $('#datagrid1').datagrid("getSelections");
        if (rows.length == 0) {
            $.messager.alert('警告', '请选择要操作的记录');
            return;
        }
        $.messager.confirm('确认', "确定要操作选中的记录吗", function (r) {
            if (r) {
                //拼接主键
                var nmArr = [];
                $.each(rows, function (index, o) {
                    nmArr.push(o.kindid);
                });
                var kindids = s = nmArr.join(",");
                var data = {
                    nullity: sing,
                    kindids: kindids
                }
                //提交到后台
                $.ajax({
                    url: "${pageContext.request.contextPath}/uphold/gamels/batchTransfer",
                    type: 'PUT',
                    dataType: 'json',
                    data: data,
                    success: function (resp) {
                        if (resp.success) {
                            var msg = '';
                            if (sing == 0) {
                                //$.messager.alert('警告','批量启用成功');
                                msg = '批量启用成功';
                            } else {
                                //$.messager.alert('警告','批量禁用成功');
                                msg = '批量禁用成功';
                            }

                            $.messager.alert({
                                title: '提示',
                                msg: msg,
                                icon: 'info',
                                //width: 300,
                                //top:200 , //与上边距的距离
                                fn: function () {
                                    // 刷新列表
                                    //console.log ( $('#datagrid1') );
                                    $('#datagrid1').datagrid('load');
                                    //清除之前勾选的行
                                    $('#datagrid1').datagrid('clearChecked');
                                }
                            });
                        } else {
                            $.messager.alert('警告', '');
                        }
                    }
                });
            }
        });

    }

</script>
</body>
</html>

