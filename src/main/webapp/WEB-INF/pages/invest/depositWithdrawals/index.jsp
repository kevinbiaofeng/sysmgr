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
    <title>人工存提</title>
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
                    <a href="javascript:void(0);" class="pull-right panel-toggle">
                        <i class="iconfont">&#xe603;</i>
                    </a>
                </div>
                <div class="panel-body">
                    <form id="search-form" method="post">
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2">
                            <tr>
                                <td class="form-label">
                                    <label for="accounts">账号：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="accounts" name="accounts" class="easyui-textbox"/>
                                </td>
                                <td class="form-label">
                                    <label for="gameId">玩家ID：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="gameId" name="gameId" class="easyui-numberbox"/>
                                    <span style="color: red">*请输入数字，最大长度9位</span>
                                </td>

                                <td class="form-label">
                                    <label for="collectdateStartStr">日期查询：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="collectdateStartStr" name="collectdateStartStr"
                                           class="easyui-datetimebox"
                                           editable="false"/>
                                    <label for="collectdateEndStr">至：</label>
                                    <input id="collectdateEndStr" name="collectdateEndStr" class="easyui-datetimebox"
                                           editable="false"/>
                                </td>

                                <td class="button-group" colspan="2">
                                    <button type="button" class="btn btn-primary" onclick="searchSerial()">
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
                <!-- 操作按钮区域 -->
                <div id="tb" style="display:none" class="opt-button-float">
                    <div class="group-button opt-button-float">
                        <%--<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="userList()">
                            <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>用户存提</span>
                        </button>--%>
                        <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="depositWithdrawalGold(1)">
                            <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>充值金币</span>
                        </button>
                        <button class="layui-btn layui-btn-sm layui-btn-danger" onclick="depositWithdrawalGold(2)">
                            <i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>扣除金币</span>
                        </button>
                    </div>
                </div>

                <table id="datagrid1" class="easyui-datagrid" title="人工存提明细"
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
							idField: 'userid',
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
                        <th data-options="field:'accounts',align:'center',fixed:false,width:40">账号</th>
                        <th data-options="field:'gameId',align:'center',fixed:false,width:40">玩家ID</th>
                        <th data-options="field:'merchant',align:'center',fixed:false,width:40">商户号</th>
                        <th data-options="field:'serialnumber',align:'center',fixed:false,width:40">流水号</th>
                        <th data-options="field:'collectdate',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">
                            流水时间
                        </th>
                        <th data-options="field:'typeidDesc',align:'center',fixed:false,width:40">流水类型</th>
                        <th data-options="field:'curScoreDesc',align:'center',fixed:false,width:40">操作前携带金币</th>
                        <th data-options="field:'curinSureScoreDesc',align:'center',fixed:false,width:40">操作前银行金币</th>
                        <th data-options="field:'changeScoreDesc',align:'center',width:40,fixed:false">金币变化</th>
                        <th data-options="field:'masterName',align:'center',width:40">操作管理员</th>
                        <th data-options="field:'clientip',align:'center',width:40,fixed:false">操作地址</th>
                    </tr>
                    </thead>
                </table>

            </div>
        </div>
    </div>
</div>

<!-- 弹出页面 -->
<div id="dialog" style="overflow-x: hidden" class="easyui-dialog" closed="true"></div>

<script type="text/javascript">

    $(function ($) {
        $('#gameId').numberbox('textbox').attr('maxlength', 9);
        initShow();
    });

    function initShow(){
        //初始化
        $('#collectdateStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
        $('#collectdateEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

        searchSerial();
    }

    //点击重置
    $("#reset1").unbind();
    $("#reset1").bind("click", function () {
        $('#search-form').form('clear');
        initShow();
        // search();
    });

    //查询
    function searchSerial() {
        var obj = $("#search-form").serializeObject();
        $('#datagrid1').datagrid({
            url: '${pageContext.request.contextPath}/account/user/queryHandRecord',
            queryParams: {
                accounts: obj.accounts,
                collectdateStartStr: obj.collectdateStartStr,
                collectdateEndStr: obj.collectdateEndStr,
                gameId: obj.gameId
            },
            view: myview,
            loadMsg: "数据加载中，请稍后！",
            emptyMsg: "未搜索到相关数据！"
        });
    };

    $(window).resize(function(){
        $('#datagrid1').datagrid('resize');
    });

    //用户列表（存提）
    /*function userList() {
        $('#dialog').dialog({
            title: '用户存提',
            href: '${pageContext.request.contextPath}/invest/depositWithdrawals/toUserList',
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
    };*/

    //存提金币
    function depositWithdrawalGold(deposiWithdrawalState) {
        $('#dialog').dialog({
            title: '存提金币',
            href: '${pageContext.request.contextPath}/invest/depositWithdrawals/toDepositWithdrawalGold',
            dialogParams: {deposiWithdrawalState: deposiWithdrawalState},
            width: 800,
            height: 300,
            closed: false,
            cache: false,
            modal: true,
            collapsible: true,
            maximizable: false,
            resizable: true,
            shadow: true,
            content: ''
        }).window('center');

    }

</script>
</body>
</html>

