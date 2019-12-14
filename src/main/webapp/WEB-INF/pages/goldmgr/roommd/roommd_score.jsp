<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>战绩详情</title>
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
							method: 'GET',
							fit:false,
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
                            <th data-options="field:'userid',align:'center',fixed:false,width:30">用户标识</th>
                            <th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
                            <th data-options="field:'nickname',align:'center',fixed:false,width:50">昵称</th>
                            <th data-options="field:'scoreDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">用户金币</th>
                            <th data-options="field:'allCount',align:'center',fixed:false,width:30">总局</th>
                            <th data-options="field:'wincount',align:'center',fixed:false,width:30">赢局</th>
                            <th data-options="field:'lostcount',align:'center',fixed:false,width:30">输局</th>
                            <th data-options="field:'drawcount',align:'center',fixed:false,width:30">和局</th>
                            <th data-options="field:'fleecount',align:'center',fixed:false,width:30">逃局</th>
                            <th data-options="field:'starttime',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">开始时间</th>

                        </tr>
                        </thead>
                    </table>

                </div>
            </div>

            <div class="opt-button-float" style="text-align: center;">
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
	
	$('#datagrid2').datagrid({
		url: '${pageContext.request.contextPath}/goldmgr/roommd/queryPersonalRoomScoreInfoWithPage?recordid='+${recordid},
		pageNumber:1,
		view: myview,
		loadMsg: "数据加载中，请稍后！",
		emptyMsg: "未搜索到相关数据！"
	});
</script>

</body>
</html>