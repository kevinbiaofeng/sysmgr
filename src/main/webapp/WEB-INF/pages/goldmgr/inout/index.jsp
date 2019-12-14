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
<title>进出记录</title>
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
										<input id="gameid" name="gameid" class="easyui-numberbox" />
										<span style="color: red">*请输入数字，最大长度9位</span>
									</td>

									<td class="form-label">
										<label for="kindname">游戏名称：</label>
									</td>
									<td class="form-editor">
										<input id="kindname" name="kindname" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',
											   	url:'${pageContext.request.contextPath}/uphold/gamels/loadGameModuleData',method: 'GET'" value="0"/>
									</td>

									<td class="form-label">
										<label for="nickName">昵称：</label>
									</td>
									<td class="form-editor">
										<input id="nickName" name="nickName" class="easyui-textbox" />
									</td>

									<td class="form-label">
										<label for="entertimeStartStr">进入时间：</label>
									</td>
									<td class="form-editor">
										<input id="entertimeStartStr" name="entertimeStartStr" class="easyui-datetimebox" editable="false"/>
										<label for="entertimeEndStr">至：</label>
										<input id="entertimeEndStr" name="entertimeEndStr" class="easyui-datetimebox" editable="false"/>
									</td>

									<td class="button-group">
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
					<table id="datagrid1" class="easyui-datagrid" title="进出记录"

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
							<th data-options="field:'remitterGameId',align:'center',fixed:false,hidden:true">remitterGameId</th>
							<th data-options="field:'entertime',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">进入时间</th>
							<th data-options="field:'remitter',align:'center',fixed:false,width:60,formatter:function(value,row,index){return formatRemitterValue(value, row, index)}">用户信息</th>
							<th data-options="field:'enterclientip',align:'center',fixed:false,width:30">进入地址</th>
							<th data-options="field:'kindname',align:'center',fixed:false,width:30">游戏</th>
							<th data-options="field:'servername',align:'center',fixed:false,width:30">房间</th>
							<th data-options="field:'enterscoreDouble',align:'center',fixed:false,width:30">携带金币</th>
							<th data-options="field:'enterinsureDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">银行金币</th>
							<th data-options="field:'leavetimeStr',align:'center',fixed:false,width:60">离开时间</th>
							<th data-options="field:'leaveclientip',align:'center',fixed:false,width:30">离开地址</th>
							<th data-options="field:'leavereasonDesc',align:'center',fixed:false,width:30">离开原因</th>
							<th data-options="field:'scoreDouble',align:'center',fixed:false,width:30">携带金币变化</th>
							<th data-options="field:'insureDouble',align:'center',fixed:false,width:30">银行金币变化</th>
							<th data-options="field:'revenueDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">游戏服务费</th>
							<th data-options="field:'playtimecount',align:'center',fixed:false,width:30">游戏时长(秒)</th>
							<th data-options="field:'onlinetimecount',align:'center',fixed:false,width:30">在线时长(秒)</th>
							<th data-options="field:'allCount',align:'center',fixed:false,width:30">总局</th>

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

		$('#entertimeEnd').datebox({
			onSelect: function(date){
				var v = $._dateFormatterYmd(date);
				$('#entertimeEndStr').textbox('setValue', v);
			}
		});

		$('#entertimeStart').datebox({
			onSelect: function(date){
				var v = $._dateFormatterYmd(date);
				$('#entertimeStartStr').textbox('setValue', v);
			}
		});

		$(function($){
			$('#gameid').numberbox('textbox').attr('maxlength', 9);

			//初始化日期
			$('#entertimeStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
			$('#entertimeEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

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
				url:'${pageContext.request.contextPath}/goldmgr/inout/queryRecordUserInoutWithPage',
				pageNumber:1,
				queryParams : {
					entertimeEndStr: obj.entertimeEndStr,
					entertimeStartStr: obj.entertimeStartStr,
					kindname: obj.kindname,
					nickName: obj.nickName,
					gameid: obj.gameid
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！",
				onLoadSuccess: function(data){
					var rows = $('#datagrid1').datagrid("getRows");
					var enterscoreDoubleTotal = 0;
					var enterinsureDoubleTotal = 0;
					var revenueDoubleTotal = 0;
					for (var i = 0; i < rows.length; i++) {
						enterscoreDoubleTotal += rows[i]['enterscoreDouble'];
						enterinsureDoubleTotal += rows[i]['enterinsureDouble'];
						revenueDoubleTotal += rows[i]['revenueDouble'];
					}
					$('#datagrid1').datagrid('appendRow', {
						enterclientip: '<b>统计：</b>', enterscoreDouble: enterscoreDoubleTotal, enterinsureDouble: enterinsureDoubleTotal, leavetime: null, revenueDouble: revenueDoubleTotal
					});
				}
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

		function formatRemitterValue(value,row,index){
			if(value != null)
			return value + '(' + row.remitterGameId + ')';
		}
	</script>
</body>
</html>

