<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>银行记录</title>
	<%@ include file="/static/pub/include.jsp"%>
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
									<label for="sourceGameId">汇款玩家ID：</label>
								</td>
								<td class="form-editor">
									<input id="sourceGameId" name="sourceGameId" class="easyui-numberbox" />
								</td>

								<td class="form-label">
									<label for="targetGameId">收款玩家ID：</label>
								</td>
								<td class="form-editor">
									<input id="targetGameId" name="targetGameId" class="easyui-numberbox" />
								</td>

								<td class="form-label">
									<label for="tradetype">交易类型：</label>
								</td>
								<td class="form-editor">
									<input id="tradetype" name="tradeType" class="easyui-combobox" editable="false"
										   data-options="panelHeight:'auto', valueField:'id',textField:'text',
											   		data:[{id: 0,text: '全部'},{id: 1,text: '存款'},
											   		{id: 2,text: '取款'},{id: 3,text: '转账'}
											   		]" value="0"/>
								</td>

								<td class="form-label">
									<label for="collectdateStartStr">日期查询：</label>
								</td>
								<td class="form-editor">
									<input id="collectdateStartStr" name="collectdateStartStr" class="easyui-datetimebox" editable="false"/>
									<label for="collectdateEndStr">至：</label>
									<input id="collectdateEndStr" name="collectdateEndStr" class="easyui-datetimebox" editable="false"/>
								</td>

								<td class="button-group" colspan="2">
									<button type="button" class="btn btn-primary" id="search" name="search">
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
			<div class="panel-body" >

				<!-- 操作按钮区域 -->
				<div id="tb" style="display:none" class="opt-button-float">
					<div class="group-button opt-button-float">
					</div>
				</div>

				<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
				<table id="datagrid1" class="easyui-datagrid" title="银行记录"

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
						<th data-options="field:'remitterGameId',align:'center',fixed:false,hidden:true">remitterGameId</th>
						<th data-options="field:'payeeGameId',align:'center',fixed:false,hidden:true">payeeGameId</th>
						<th data-options="field:'collectDate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">时间</th>
						<th data-options="field:'remitter',align:'center',fixed:false,width:60,formatter:function(value,row,index){return formatRemitterValue(value,row,index)}">汇款人</th>
						<th data-options="field:'payee',align:'center',fixed:false,width:30,formatter:function(value,row,index){return formatPayeeValue(value,row,index)}">收款人</th>
						<th data-options="field:'tradetypeDesc',align:'center',fixed:false,width:30">交易类型</th>
						<th data-options="field:'sourcegoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">汇款人携带</th>
						<th data-options="field:'sourcebankDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">汇款人银行</th>
						<th data-options="field:'targetgoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">收款人携带</th>
						<th data-options="field:'targetbankDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">收款人银行</th>
						<th data-options="field:'swapscoreDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">交易金币</th>
						<th data-options="field:'revenueDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">服务费</th>
						<th data-options="field:'clientip',align:'center',fixed:false,width:30">操作地址</th>

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

	$(function($){

		//初始化日期
		$('#collectdateStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
		$('#collectdateEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

		search();

		//点击重置
		$("#reset").unbind();
		$("#reset").bind("click",function(){
			search();
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

	});

	//查询
	function search(){
		var obj = $("#search-form").serializeObject();
		$('#datagrid1').datagrid({
			url:'${pageContext.request.contextPath}/goldmgr/bank/queryRecordInsureWithPage',//需要立即查询时开启url
			pageNumber:1,
			queryParams : {
				collectdateEndStr: obj.collectdateEndStr,
				collectdateStartStr: obj.collectdateStartStr,
				sourceGameId: obj.sourceGameId,
				targetGameId : obj.targetGameId,
				tradeType: obj.tradeType
			},
			view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
		});
	}

	$(window).resize(function(){
		$('#datagrid1').datagrid('resize');
	});

	//查询
	$("#search").unbind();
	$("#search").click(function(){
		search();
	});
	
	function formatRemitterValue(value,row,index){
		if(value != null)
		return value + '(' + row.remitterGameId + ')';
	}
	
	function formatPayeeValue(value,row,index){
		if(value != null)
		return value + '(' + row.payeeGameId + ')';
	}
</script>
</body>
</html>

