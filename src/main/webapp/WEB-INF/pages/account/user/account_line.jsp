<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看下线</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">

                    <table id="datagrid2" class="easyui-datagrid"

                           data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							fit:false,
							method: 'GET',
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
                            <%--<th data-options="field:'ck',checkbox:true"></th>--%>
                            <!-- <th data-options="field:'aaaa',align:'center',fixed:false,hidden:true">编号1111</th> -->
                            <th data-options="field:'userId',align:'center',fixed:false,width:30">用户标识</th>
                            <th data-options="field:'gameId',align:'center',fixed:false,width:30">玩家ID</th>
                            <th data-options="field:'nickName',align:'center',fixed:false,width:50">昵称</th>

                            <th data-options="field:'scoreDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">携带金币</th>
                            <th data-options="field:'insurescoreDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">保险柜金币</th>
                            <th data-options="field:'revenueDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">服务费</th>
                            <th data-options="field:'allCount',align:'center',fixed:false,width:30">总局</th>
                            <th data-options="field:'wincount',align:'center',fixed:false,width:30">赢局</th>
                            <th data-options="field:'lostcount',align:'center',fixed:false,width:30">输局</th>
                            <th data-options="field:'drawcount',align:'center',fixed:false,width:30">和局</th>
                            <th data-options="field:'fleecount',align:'center',fixed:false,width:30">逃局</th>
                            <th data-options="field:'playTimeCount',align:'center',fixed:false,width:30">游戏时长（秒</th>
                            <th data-options="field:'onLineTimeCount',align:'center',fixed:false,width:30">在线时长（秒）</th>

                            <th data-options="field:'genderDesc',align:'center',fixed:false,width:30">性别</th>
                            <th data-options="field:'spreaderId',align:'center',fixed:false,width:30">代理</th>
                            <th data-options="field:'registerDate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">注册时间</th>
                            <th data-options="field:'registerIp',align:'center',fixed:false,width:40">注册地址(IP)</th>
                            <th data-options="field:'gameLogonTimes',align:'center',fixed:false,width:30">登录次数</th>
                            <th data-options="field:'lastLogonDate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">最后登录时间</th>
                            <th data-options="field:'lastLogonIp',align:'center',fixed:false,width:50">最后登录地址(IP)</th>
                            <th data-options="field:'registerOriginDesc',align:'center',fixed:false,width:30">注册来源</th>
                            <th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">状态</th>
                            <th data-options="field:'option1',align:'center',fixed:false,width:180,formatter:function(value,row,index){return optortion1(value,row,index)}">操作</th>
                        </tr>
                        </thead>
                    </table>

                </div>
            </div>

            <div class="opt-button-float">
               <%-- <button type="button" class="btn btn-primary" id="save" name="save">
                    <span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
                </button>--%>
<%--                   <button type="button" class="btn btn-default" onclick="goBack()">--%>
<%--                       <span class="glyphicon glyphicon-off" aria-hidden="true">返回上一级</span>--%>
<%--                   </button>--%>
                <button type="button" class="btn btn-default" id="cancel" name="cancel">
                    <span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
                </button>
            </div>

        </div>
    </div>
</div>



<script type="text/javascript">
	
    //加载参数
    var param = $.getOpenDialogParam("dialog");
	
    $('#datagrid2').datagrid({
    	url: '${pageContext.request.contextPath}/account/user/queryAccountUserWithPage?spreaderId='+${spreaderId},
		pageNumber:1,
		view: myview,
		loadMsg: "数据加载中，请稍后！",
		emptyMsg: "未搜索到相关数据！"
	});
    
    function optortion1(value,row,index){
        var b2 = "<button class='layui-btn layui-btn-xs' onclick='openAccountAgent1({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
        b2 = b2.format(row.userId,"查看下线");
        return b2;
    }

    function openAccountAgent1(userId){
        $('#datagrid2').datagrid({
            url:'${pageContext.request.contextPath}/account/user/queryAccountUserWithPage',
            queryParams:{
                spreaderId:userId
            }
        });
    }
    // function goBack(){
    //     $('#dialog').dialog("close");
        // history.back();
        // history.go();
    // }


    //取消
    $("#cancel").unbind();
    $("#cancel").bind("click",function(){
        //alert("取消");
        $('#dialog').dialog("close");
    });


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