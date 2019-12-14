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
<title>游戏记录</title>
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
										<label for="gameid">玩家ID：</label>
									</td>
									<td class="form-editor">
										<input id="gameid" name="gameid" class="easyui-numberbox" data-options="required:false"/>
										<span style="color: red">*请输入数字，最大长度9位</span>
									</td>

									<td class="form-label">
										<label for="inserttimeStartStr">记录时间：</label>
									</td>
									<td class="form-editor">
										<input id="inserttimeStartStr" name="inserttimeStartStr" class="easyui-datetimebox" editable="false"/>
										<label for="inserttimeEndStr">至：</label>
										<input id="inserttimeEndStr" name="inserttimeEndStr" class="easyui-datetimebox" editable="false"/>
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
					<table id="datagrid1" title="游戏记录"

						   data-options="
							singleSelect: true,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							nowrap:false,
							fit:false,
							method: 'GET',
							toolbar: '#tb',
							idField: 'drawid',
							rownumbers: true,
							pagination: true,
							autoRowHeight: false,
							pageSize: 10,
							pageNumber: 1,
							pageList: [10,20,30,40,50,100,200,500,1000]
							">

						<thead>
						<tr>
							<th data-options="field:'drawid',align:'center',fixed:false,hidden:true">drawid</th>
							<th data-options="field:'inserttime',align:'center',fixed:false,width:50,formatter:function(value,row,index){return $.dateFormatter(value)}">记录时间</th>
							<th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
							<th data-options="field:'kindname',align:'center',fixed:false,width:30">游戏</th>
							<th data-options="field:'servername',align:'center',fixed:false,width:50">房间</th>
							<th data-options="field:'tableid',align:'center',fixed:false,width:30">桌子</th>
							<th data-options="field:'usercount',align:'center',fixed:false,width:30">用户数</th>
							<th data-options="field:'androidcount',align:'center',fixed:false,width:30">机器人数</th>
							<th data-options="field:'wasteDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">损耗</th>
							<th data-options="field:'revenueDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">服务费</th>
							<th data-options="field:'starttime',align:'center',fixed:false,width:50,formatter:function(value,row,index){return $.dateFormatter(value)}">开始时间</th>
							<th data-options="field:'concludetime',align:'center',fixed:false,width:50,formatter:function(value,row,index){return $.dateFormatter(value)}">结束时间</th>

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
		const tableStr = "<table style='width: 100%;text-align:center;'><tr><td>昵称</td><td>是否机器人</td><td>椅子编号</td><td>输赢积分</td><td>服务费</td><td>游戏时长(秒)</td><td>插入时间</td></tr>" +
				"<tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td></tr></table>";

		$(function($){
			$('#gameid').numberbox('textbox').attr('maxlength', 9);
			//初始化日期
			$('#inserttimeStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
			$('#inserttimeEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

			search();

			//查询
			$("#search").unbind();
			$("#search").click(function(){
				search();
			});

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

		function search(){
			var obj = $("#search-form").serializeObject();
			$('#datagrid1').datagrid({
				queryParams: {
					inserttimeEndStr: obj.inserttimeEndStr,
					inserttimeStartStr: obj.inserttimeStartStr,
					gameid: obj.gameid
				},
				url: '${pageContext.request.contextPath}/goldmgr/gamerd/queryRecordDrawInfoWithPage',
				view: myview,
				detailFormatter: function(rowIndex, rowData){
							let result = tableStr.format(
							rowData.nickname,
							rowData.isandroidDesc,
							rowData.chairid,
							$.numberFormatter(rowData.scoreDouble),
							$.numberFormatter(rowData.revenueDouble),
							rowData.playtimecount,
							$.dateFormatter(rowData.inserttime)
					);
					return result;
				},
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

	</script>
</body>
</html>

