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
<title>卡号绑定管理</title>
<%@ include file="/static/pub/include.jsp"%>
</head>

<body>

	<div id="search-panel" class="container-fluid">
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
										<label for="gameId">玩家ID：</label>
									</td>
									<td class="form-editor">
										<input id="gameId" name="gameId" class="easyui-numberbox" />
										<span style="color: red">*请输入数字，最大长度9位</span>
									</td>
									<td class="form-label">
										<label for="nickName">用户昵称：</label>
									</td>
									<td class="form-editor">
										<input id="nickName" name="nickName" class="easyui-textbox" />
									</td>
									<td class="form-label">
										<label for="nullity">状态：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]" />
									</td>

									<td class="button-group" colspan="2">
										<button type="button" class="btn btn-primary" id="search" name="search">
											<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
										</button>
										<button type="button" class="btn btn-warning" id="reset" name="reset">
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
				<div class="panel-body" >
					
					<!-- 操作按钮区域 -->
					<div id="tb" style="display:none" class="opt-button-float">
						<div class="group-button opt-button-float">
						
						</div>
					</div>
					
					<table id="datagrid1" class="easyui-datagrid" title="用户信息"
						
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
							idField: 'userId',
							rownumbers: false,
							pagination: true,
							autoRowHeight: false,
							pageSize: 10,
							pageNumber: 1,
							pageList: [10,20,30,40,50,100,200,500,1000],
							emptyMsg: '<span>没有相关数据!</span>'
							">
							
						<thead>
							<tr>
								<th data-options="field:'userId',align:'center',fixed:false,width:30">用户标识</th>
								<th data-options="field:'gameId',align:'center',fixed:false,width:30">玩家ID</th>
								<th data-options="field:'nickName',align:'center',fixed:false,width:50">昵称</th>
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

		//权限
		let permission = "<shiro:principal property='permission'/>";

		//设置日期
		$(function($){
			$('#gameId').numberbox('textbox').attr('maxlength', 9);
			search();
			//查询
			$("#search").unbind();
			$("#search").click(function(){
				search();
			});

			//点击重置
			$("#reset").unbind();
			$("#reset").bind("click",function(){
				$('#search-form').form('clear');
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
				url: '${pageContext.request.contextPath}/withdraw/bindcard/queryAccountUserWithPage',
				pageNumber:1,
				queryParams : {
					nickName : obj.nickName,
					gameId : obj.gameId,
					nullity : obj.nullity
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
			var b1 = "<button class='layui-btn layui-btn-xs' onclick='showUserCard({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			b1 = b1.format(row.userId,"查看银行卡信息");
			return b1;
		}

		//绑定账号
		function showUserCard(id){
			$('#dialog').dialog({
				title: '绑定卡号列表',
				href: '${pageContext.request.contextPath}/withdraw/bindcard/toBindcardListPage?userId='+id,
				dialogParams: {userId : id},
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

		}



	</script>
</body>
</html>

