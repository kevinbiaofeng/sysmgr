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
<title>房间模式</title>
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
										<label for="userid">房主ID：</label>
									</td>
									<td class="form-editor">
										<input id="userid" name="userid" class="easyui-numberbox" data-options="required:false"/>
									</td>

									<td class="form-label">
										<label for="roomid">房间号：</label>
									</td>
									<td class="form-editor">
										<input id="roomid" name="roomid" class="easyui-numberbox" data-options="required:false"/>
									</td>

									<td class="form-label">
										<label for="createdateStartStr">创建时间：</label>
									</td>
									<td class="form-editor">
										<input id="createdateStartStr" name="createdateStartStr" class="easyui-datetimebox" editable="false"/>
										<label for="createdateEndStr">至：</label>
										<input id="createdateEndStr" name="createdateEndStr" class="easyui-datetimebox" editable="false"/>
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

					<table id="datagrid1" class="easyui-datagrid" title="房间模式"

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
							<th data-options="field:'createdate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">创建时间</th>
							<th data-options="field:'roomstatusDesc',align:'center',fixed:false,width:30">房间状态</th>
							<th data-options="field:'userid',align:'center',fixed:false,width:50">房主ID</th>
							<th data-options="field:'nickname',align:'center',fixed:false,width:30">房主昵称</th>
							<th data-options="field:'gameid',align:'center',fixed:false,width:50">房主玩家ID</th>
							<th data-options="field:'servername',align:'center',fixed:false,width:50">游戏房间</th>
							<th data-options="field:'roomid',align:'center',fixed:false,width:30">房间号</th>
							<th data-options="field:'cellscore',align:'center',fixed:false,width:30">游戏底分(分)</th>
							<th data-options="field:'countlimit',align:'center',fixed:false,width:30">局数限制</th>
							<th data-options="field:'taxcountDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">服务费</th>
							<th data-options="field:'dissumedate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">解散时间</th>
							<th data-options="field:'option',align:'center',fixed:false,width:50,formatter:function(value,row,index){return optortion(value,row,index)}">操作</th>

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


		$('#createdateEnd').datebox({
			onSelect: function(date){
				var v = $._dateFormatterYmd(date);
				$('#createdateEndStr').textbox('setValue', v);
			}
		});

		$('#createdateStart').datebox({
			onSelect: function(date){
				var v = $._dateFormatterYmd(date);
				$('#createdateStartStr').textbox('setValue', v);
			}
		});

		$(function($){
			$('#gameid').numberbox('textbox').attr('maxlength', 9);

			//初始化日期
			$('#createdateStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
			$('#createdateEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

			var obj = $("#search-form").serializeObject();
			$('#datagrid1').datagrid({
				queryParams: {
					createdateEndStr: obj.createdateEndStr,
					createdateStartStr: obj.createdateStartStr,
					roomid: obj.roomid,
					userid: obj.userid,
					gameid: obj.gameid
				}
			});

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

		//查询
		function search(){
			var obj = $("#search-form").serializeObject();
			$('#datagrid1').datagrid({
				url: '${pageContext.request.contextPath}/goldmgr/roommd/queryStreamCreateTableFeeInfoWithPage',
				queryParams : {
					createdateEndStr: obj.createdateEndStr,
					createdateStartStr: obj.createdateStartStr,
					roomid: obj.roomid,
					userid: obj.userid,
					gameid: obj.gameid
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

		//操作按钮
		function optortion(value,row,index){
			var b1 = "<button class='layui-btn layui-btn-xs' onclick='showRoomScore({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			b1 = b1.format(row.recordid,"查看战绩");
			return b1;
		}

		function showRoomScore(recordid){
			//房间记录id
			$('#dialog').dialog({
				title: '战绩详情',
				href: '${pageContext.request.contextPath}/goldmgr/roommd/toRoommdScorePage?recordid=' + recordid,
				dialogParams: {recordid : recordid},
				width: 1000,
				height: 500,
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

			$('#dialog').dialog('open');
		}



	</script>
</body>
</html>

