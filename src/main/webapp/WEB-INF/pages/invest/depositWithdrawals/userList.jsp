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
    <title>用户列表</title>
    <%@ include file="/static/pub/include.jsp" %>
</head>

<body>
<div id="search-panel" class="container-fluid">
    <!-- 查询条件信息 -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="panel-title">查询条件</span>
                </div>
                <div class="panel-body">
                    <form id="searchAccounts-form" method="post">
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2">
                            <tr>
                                <td class="form-label">
                                    <label for="accounts">账号：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="accounts" name="accounts" class="easyui-textbox"/>
                                </td>
                                <td class="form-label">
                                    <label for="nickName">用户昵称：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="nickName" name="nickName" class="easyui-textbox"/>
                                </td>
                                <td class="form-label">
                                    <label for="gameId">玩家ID：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="gameId" name="gameId" class="easyui-numberbox"/>
                                </td>
                                <td class="button-group" colspan="2">
                                    <button type="button" class="btn btn-primary" id="search1" name="search">
                                        <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                                    </button>
                                    <button type="button" class="btn btn-warning" id="reset1" name="reset">
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

    <div class="row">
        <div class="col-lg-12">
            <div class="panel-body">
                <div id="tg" style="display:none;margin-right:8px;" class="opt-button-float">
                    <div class="group-button opt-button-float">
                        <%--<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="depositWithdrawalGold(1)">
                            <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>充值金币</span>
                        </button>
                        <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="depositWithdrawalGold(2)">
                            <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>扣除金币</span>
                        </button>--%>
                        <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="chooseAccounts()">
                            <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>选取用户</span>
                        </button>
                    </div>
                </div>

                <table id="datagrid2" class="easyui-datagrid" title="用户信息"
                       data-options="
							singleSelect: true,
							fitColumns: true,
							nowrap:false,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							fit:false,
							method: 'GET',
							toolbar: '#tg',
							idField: 'userId',
							rownumbers: false,
							pagination: true,
							autoRowHeight: false,
							pageSize: 10,
							pageNumber: 1,
							pageList: [10,20,30,40,50,100,200,500,1000]
							">
                    <thead>
                    <tr>
                        <th data-options="field:'ck',checkbox:true"></th>
                        <th data-options="field:'gameId',align:'center',fixed:false,width:35">玩家ID</th>
                        <th data-options="field:'accounts',align:'center',fixed:false,width:35">账号</th>
                        <th data-options="field:'nickName',align:'center',fixed:false,width:50">
                            昵称
                        </th>
                        <th data-options="field:'scoreDouble',align:'center',fixed:false,width:32,formatter:function(value,row,index){return $.numberFormatter(value)}">
                            携带金币
                        </th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<%--<div id="tg" style="display:none;margin-right:8px;" class="opt-button-float">
    <div class="group-button opt-button-float">
        <div class="easyui-dialog" closed="true">
            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="chooseAccounts()">
                <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>选取用户</span>
            </button>
        </div>
    </div>
</div>--%>

<!-- 弹出页面 -->
<div id="dialog" class="easyui-dialog" closed="true"></div>

<script type="text/javascript">

    $(function () {
        search();
    });

    //查询按钮
    $("#search1").unbind();
    $("#search1").click(function () {
        search();
    });

    //重置按钮
    $("#reset1").unbind();
    $("#reset1").bind("click", function () {
        $('#searchAccounts-form').form('clear');
        search();
    });

    //查询
    function search() {
        var obj = $("#searchAccounts-form").serializeObject();
        $('#datagrid2').datagrid({
            url: '${pageContext.request.contextPath}/account/user/queryAccountUserWithPage',
            queryParams: {
                nickName: obj.nickName,
                gameId: obj.gameId,
                accounts: obj.accounts
            },
            view: myview,
            loadMsg: "数据加载中，请稍后！",
            emptyMsg: "未搜索到相关数据！"
        });
    }

    //存提金币
    /*function depositWithdrawalGold(deposiWithdrawalState) {
        $('#dialog').dialog({
            title: '存提金币',
            href: '${pageContext.request.contextPath}/invest/depositWithdrawals/toDepositWithdrawalGold',
            dialogParams: {deposiWithdrawalState: deposiWithdrawalState},
            width: 1000,
            height: 500,
            closed: false,
            cache: false,
            modal: true,
            collapsible: true,
            maximizable: false,
            resizable: true,
            shadow: true,
            content: ''
        }).window('center');
    }*/

    //选择单条数据把值显示给某关联页面的控件id
    function chooseAccounts() {
        var rows = $('#datagrid2').datagrid("getSelected");
        // console.log(rows)
        if (rows == null) {
            $.messager.alert('警告', '请选择要操作的记录');
            return;
        }
        $.messager.confirm('确认', "确定要选取该用户吗", function (r) {
            if (r) {
                $("#userId").numberbox("setValue", rows.userId);
                $("#accounts2").textbox("setValue", rows.accounts);
                $("#nickName2").textbox("setValue", rows.nickName);
                //关闭当前弹框
                $('#dialog1').dialog("destroy");
            }
        });
    }

</script>
</body>
</html>

