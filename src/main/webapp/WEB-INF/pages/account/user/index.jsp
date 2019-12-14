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
<title>用户管理</title>
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
										<label for="accounts">账号：</label>
									</td>
									<td class="form-editor">
										<input id="accounts" name="accounts" class="easyui-textbox" />
									</td>
									<td class="form-label">
										<label for="nickName">用户昵称：</label>
									</td>
									<td class="form-editor">
										<input id="nickName" name="nickName" class="easyui-textbox" />
									</td>
									<td class="form-label">
										<label for="gameId">玩家ID：</label>
									</td>
									<td class="form-editor">
										<input id="gameId" name="gameId" class="easyui-numberbox" />
										<span style="color: red">*请输入数字，最大长度9位</span>
									</td>
									<td class="form-label">
										<label for="nullity">状态：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]" />
									</td>

									
								</tr>
								<tr>
									<td class="form-label">
										<label for="scoreStart">携带金币：</label>
									</td>
									<td class="form-editor">
										<input id="scoreStart" name="scoreStart" class="easyui-numberbox" />
										<label for="scoreEnd"> — </label>
										<input id="scoreEnd" name="scoreEnd" class="easyui-numberbox" />
									</td>
									<td class="form-label">
										<label for="insurescoreStart">保险柜金币：</label>
									</td>
									<td class="form-editor">
										<input id="insurescoreStart" name="insurescoreStart" class="easyui-numberbox" />
										<label for="scoreEnd"> — </label>
										<input id="insurescoreEnd" name="insurescoreEnd" class="easyui-numberbox" />
									</td>

									<td class="form-label">
										<label for="revenueStart">服务费：</label>
									</td>
									<td class="form-editor">
										<input id="revenueStart" name="revenueStart" class="easyui-numberbox" />
										<label for="scoreEnd"> — </label>
										<input id="revenueEnd" name="revenueEnd" class="easyui-numberbox" />
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
					<div id="tb" style="display:none;margin-right:8px;" class="opt-button-float">
						<div class="group-button opt-button-float">
						
							<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="nullityUp(1)">
                            	<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>冻结账号</span>
                        	</button>
							<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="nullityUp(0)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>解冻账号</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="batchTransfer(0)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>设置个人转账权限</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="batchTransfer(1)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>取消个人转账权限</span>
							</button>
							<%--<button class="layui-btn layui-btn-sm layui-btn-danger" onclick="batchTransferAll(0)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>设置所有人转账权限</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-danger" onclick="batchTransferAll(1)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>取消所有人转账权限</span>
							</button>--%>
						</div>
					</div>
					
					<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
					<table id="datagrid1" class="easyui-datagrid" title="用户信息" 
						data-options="
							singleSelect: false,
							fitColumns: true,
							nowrap:false,	//自动换行
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
							pageList: [10,20,30,40,50,100,200,500,1000]
							">
							
						<thead>
							<tr>
								<th data-options="field:'ck',checkbox:true"></th>
<!-- 								<th data-options="field:'userId',align:'center',fixed:false,width:30">用户ID</th> -->
								<th data-options="field:'gameId',align:'center',fixed:false,width:35">玩家ID</th>
								<th data-options="field:'accounts',align:'center',fixed:false,width:35">账号</th>
								<th data-options="field:'nickName',align:'center',fixed:false,width:50,formatter:function(value,row,index){return linkNickName(value,row,index)}">昵称</th>
								<th data-options="field:'scoreDouble',align:'center',fixed:false,width:32,formatter:function(value,row,index){return $.numberFormatter(value)}">携带金币</th>
								<th data-options="field:'insurescoreDouble',align:'center',fixed:false,width:35,formatter:function(value,row,index){return $.numberFormatter(value)}">保险柜金币</th>
								<th data-options="field:'revenueDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">服务费</th>
								<th data-options="field:'allCount',align:'center',fixed:false,width:30">总局</th>
								<th data-options="field:'wincount',align:'center',fixed:false,width:30">赢局</th>
								<th data-options="field:'lostcount',align:'center',fixed:false,width:30">输局</th>
								<th data-options="field:'drawcount',align:'center',fixed:false,width:30">和局</th>
								<th data-options="field:'fleecount',align:'center',fixed:false,width:30">逃局</th>
								<th data-options="field:'playTimeCount',align:'center',fixed:false,width:30">游戏时长（秒</th>
								<%--<th data-options="field:'onLineTimeCount',align:'center',fixed:false,width:30">在线时长（秒）</th>
								<th data-options="field:'genderDesc',align:'center',fixed:false,width:30">性别</th>--%>
								<th data-options="field:'spreaderId',align:'center',fixed:false,width:30,formatter:function(value,row,index){return linkAgent(value,row,index)}">代理</th>
								<th data-options="field:'registerDate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">注册时间</th>
								<th data-options="field:'registerIp',align:'center',fixed:false,width:40">注册IP</th>
								<th data-options="field:'gameLogonTimes',align:'center',fixed:false,width:30">登录次数</th>
								<th data-options="field:'lastLogonDate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">最后登录时间</th>
								<th data-options="field:'lastLogonIp',align:'center',fixed:false,width:50">最后登录IP</th>
								<th data-options="field:'registerOriginDesc',align:'center',fixed:false,width:30">注册来源</th>
								<th data-options="field:'merchant',align:'center',fixed:false,width:30">商户号</th>
								<th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">状态</th>
								<th data-options="field:'option',align:'center',fixed:false,width:120,formatter:function(value,row,index){return optortion(value,row,index)}">操作</th>
							</tr>
						</thead>
					</table>
					
				</div>
			</div>
		</div>
		
	</div>
	
	
	<!-- 弹出页面 -->
	<div id="dialog" style="overflow-x: hidden"  class="easyui-dialog" closed="true"></div>


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
				url:'${pageContext.request.contextPath}/account/user/queryAccountUserWithPage',
				pageNumber:1,
				queryParams : {
					scoreStart : obj.scoreStart,
					scoreEnd: obj.scoreEnd,
					insurescoreStart : obj.insurescoreStart,
					insurescoreEnd : obj.insurescoreEnd,
					revenueStart : obj.revenueStart,
					revenueEnd : obj.revenueEnd,
					nickName : obj.nickName,
					gameId : obj.gameId,
					accounts : obj.accounts,
					nullity : obj.nullity
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		//监听浏览器宽度变化，调整datagrid尺寸和布局
		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');//信息列表 datagrid
			// $('.easyui-panel').panel('resize');//搜索栏 panel
		});

		//给昵称 添加链接
		function linkNickName(value,row,index){
			var nickName = "<a href='javascript:;' onclick='openAccountInfo({0})' style='color: #00ee00'>{1}</a>";
			var result = nickName.format(row.userId,row.nickName);
			return result;
		}

		//给代理添加链接
		function linkAgent(value,row,index){
			var agent = "<a href='javascript:;' onclick='openAccountAgent({0})' style='color: #00ee00'>{1}</a>";
			if(row.spreaderId == 0){
				var result = agent.format(row.spreaderId,"");
			}else{
				var result = agent.format(row.spreaderId,row.spreaderId);
			}
			return result;
		}

		//查看代理下所有 下线
		function openAccountAgent(spreaderId){
			$('#dialog').dialog({
				title: '查看下线',
				href: '${pageContext.request.contextPath}/account/user/toAccountLinePage?spreaderId=' + spreaderId,
				dialogParams: {spreaderId : spreaderId},
				width: 1000,
				height: 500,
				closed: false,
				cache: false,
				modal: true,
				collapsible: true,
				maximizable: false,
				resizable: true,
				shadow: true
			}).window('center');
		}

		//打开项目详情
		function openAccountInfo(userId){
			$('#dialog').dialog({
				title: '用户详情',
				href: '${pageContext.request.contextPath}/account/user/toAccountEditPage?userId='+userId,
				dialogParams: {userId : userId},
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

		//操作按钮
		function optortion(value,row,index){
			var b1 = "<button class='layui-btn layui-btn-xs' onclick='giveNum({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			b1 = b1.format(row.userId,"赠送靓号");
			var b2 = "<button class='layui-btn layui-btn-xs' onclick='openAccountAgent({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			b2 = b2.format(row.userId,"查看下线");
			/*var b3 = "<button class='layui-btn layui-btn-xs' onclick='giveGold({0},{1})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{2}</span></button>";
			b3 = b3.format(row.userId,row.nullity,"赠送金币");*/
			// return b1  + b2 + b3;
			return b1  + b2;
		}

		//赠送金币
		function giveGold(userId,nullity){
			if(nullity == 1){
				$.messager.alert('警告','该用户已被禁用无法赠送金币');
				return;
			}
			$('#dialog').dialog({
				title: '赠送金币',
				href: '${pageContext.request.contextPath}/account/user/toAccountGiveGoldPage',
				dialogParams: {userId : userId},
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

		//赠送靓号
		function giveNum(userId){
			$('#dialog').dialog({
				title: '赠送靓号',
				href: '${pageContext.request.contextPath}/account/user/toAccountGiveNumPage',
				dialogParams: {userId : userId},
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

		//冻结 解冻
		function nullityUp(nullity){
			var rows = $('#datagrid1').datagrid("getSelections");
			if(rows.length == 0){
				$.messager.alert('警告','请选择要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要操作选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						nmArr.push(o.userId);
					});
					var userIds = s = nmArr.join(",");
					var data = {
						userIds : userIds,
						nullity : nullity
					}
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/account/user/nullityUpate",
						type: 'PUT',
						dataType: 'json',
						data: data,
						success: function(resp){
							if(resp.success){
								// 刷新列表
								$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								$('#datagrid1').datagrid('clearChecked');

								if(nullity == 0){
									$.messager.alert('警告','解冻成功');
								}else{
									$.messager.alert('警告','冻结成功');
								}
							}else{
								$.messager.alert('警告','');
							}
						}
					});
				}
			});
		}


		// 设置批量转账权限
		function batchTransfer(transfer){
			var rows = $('#datagrid1').datagrid("getSelections");
			if(rows.length == 0){
				$.messager.alert('警告','请选择要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要操作选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						nmArr.push(o.userId);
					});
					var userIds = s = nmArr.join(",");
					var data = {
						userIds : userIds,
						transfer : transfer
					}
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/account/user/batchTransfer",
						type: 'PUT',
						dataType: 'json',
						data: data,
						success: function(resp){
							if(resp.success){
								// 刷新列表
								$('#datagrid1').datagrid('load');

								if(transfer == 0){
									$.messager.alert('警告','批量设置转账权限成功');
								}else{
									$.messager.alert('警告','批量取消转账权成功');
								}
							}else{
								$.messager.alert('警告','');
							}
						},
						error: function(resp) {
							console.log(resp)
							$.messager.alert('警告', resp.responseJSON.lastError);
						}
					});
				}
			});
		}

		// 设置批量转账权限
		function batchTransferAll(transfer){

			$.messager.confirm('确认',"确定要进行此操作吗",function(r){
				if (r){
					var data = {
						transfer : transfer
					}
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/account/user/batchTransferAll",
						type: 'PUT',
						dataType: 'json',
						data: data,
						success: function(resp){
							if(resp.success){
								// 刷新列表
								$('#datagrid1').datagrid('load');

								if(transfer == 0){
									$.messager.alert('警告','设置所有人转账权限成功');
								}else{
									$.messager.alert('警告','取消所有人转账权限成功');
								}
							}else{
								$.messager.alert('警告','');
							}
						}
					});
				}
			});
		}


	</script>
</body>
</html>

