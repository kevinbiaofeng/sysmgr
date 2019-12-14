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
<title>提现记录查询</title>
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
<%--						<a href="javascript:void(0);" class="pull-right panel-toggle">--%>
<%--							<i class="iconfont">&#xe603;</i>--%>
<%--						</a>--%>
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
										<label for="reviewStatus">风控状态：</label>
									</td>
									<td class="form-editor">
										<input id="reviewStatus" name="reviewStatus" class="easyui-combobox" editable="false"
											   data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: '', text: '全部', selected: true},{id: '0',text: '未审核'},{id: '1',text: '审核通过'},{id: '2',text: '审核未通过'}]" />
									</td>

									<td class="button-group" colspan="2">
										<button type="button" class="btn btn-primary" id="search" name="search">
											<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
										</button>
										<button type="button" class="btn btn-warning" id="resetButton" name="resetButton">
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
							<shiro:hasPermission name="/withdraw/record/updateCashOutOrders">
<%--							<shiro:hasPermission name="10206101">--%>
								<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="nullityUp(1)">
									<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>风控审核通过</span>
								</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="/withdraw/record/updateCashOutOrders">
								<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="nullityUp(2)">
									<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>风控审核不通过</span>
								</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="/withdraw/record/updateFinancialStatus">
								<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="financialIsPass(1)">
									<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>财务审核通过</span>
								</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="/withdraw/record/updateFinancialStatus">
								<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="financialIsPass(2)">
									<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>财务审核不通过</span>
								</button>
							</shiro:hasPermission>
						</div>
					</div>
					
					<table id="datagrid1" class="easyui-datagrid" title="提现信息"
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
							pageList: [10,20,30,40,50,100,200,500,1000]
							">
							
						<thead>
							<tr>
								<th data-options="field:'ck',checkbox:true"></th>
								<th data-options="field:'orderid',align:'center',fixed:false,width:30">提现订单号</th>
								<th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
								<th data-options="field:'nickname',align:'center',fixed:false,width:50">昵称</th>
								<th data-options="field:'bankchoiceDesc',align:'center',fixed:false,width:30">提款类型</th>
								<th data-options="field:'bankcardid',align:'center',fixed:false,width:30">卡号</th>
								<th data-options="field:'bankname',align:'center',fixed:false,width:60">银行名称</th>
								<th data-options="field:'idcardname',align:'center',fixed:false,width:40">实名</th>
								<th data-options="field:'scoreDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">兑换金币</th>
								<th data-options="field:'servicerate',align:'center',fixed:false,width:30">手续费比率(‰)</th>
								<th data-options="field:'servicefeeDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">提现手续费</th>
								<th data-options="field:'realMoneyDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">实际提现金额</th>
								<th data-options="field:'merchant',align:'center',fixed:false,width:30">商户号</th>
								<th data-options="field:'reviewstatusDesc',align:'center',fixed:false,width:30">风控状态</th>
								<th data-options="field:'reviewer',align:'center',fixed:false,width:30">风控审核人</th>
								<th data-options="field:'reviewtime',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">风控审核时间</th>
								<th data-options="field:'financialStatusDesc',align:'center',fixed:false,width:30">财务状态</th>
								<th data-options="field:'financialOperator',align:'center',fixed:false,width:30">财务审核人</th>
								<th data-options="field:'financialTime',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">财务审核时间</th>
								<th data-options="field:'addtime',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">申请时间</th>
								<%--<th data-options="field:'remark',align:'center',fixed:false,width:30">备注</th>--%>

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
		console.log("permission:"+permission)

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
			$("#resetButton").unbind();
			$("#resetButton").bind("click",function(){
				$('#search-form').form('reset');
				search();
			});

		});

		//查询
		function search(){
			var obj = $("#search-form").serializeObject();
			$('#datagrid1').datagrid({
				url: '${pageContext.request.contextPath}/withdraw/record/queryCashOutOrdersWithPage',
				queryParams : {
					nickname : obj.nickName,
					gameid : obj.gameId,
					reviewstatus : obj.reviewStatus
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！",
				onLoadSuccess: function(data){
					// alert(data.total);
					var rows = $('#datagrid1').datagrid("getRows");
					var scoreDoubleTotal = 0;
					var servicefeeDoubleTotal = 0;
					var realMoneyDoubleTotal = 0;
					for (var i = 0; i < rows.length; i++) {
						scoreDoubleTotal += rows[i]['scoreDouble'];
						servicefeeDoubleTotal += rows[i]['servicefeeDouble'];
						realMoneyDoubleTotal += rows[i]['realMoneyDouble'];
					}
					$('#datagrid1').datagrid('appendRow', {
						orderid: '<b>统计：</b>', scoreDouble: scoreDoubleTotal, servicefeeDouble: servicefeeDoubleTotal, realMoneyDouble: realMoneyDoubleTotal
					});
				}
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

		//风控审核状态修改
		function nullityUp(reviewstatus){
			var rows = $('#datagrid1').datagrid("getSelections");
			if(rows.length == 0){
				$.messager.alert('警告','请选择要操作的记录');
				return;
			}
			//拼接主键
			var nmArr = [];
			$.each(rows,function(index,o){
				nmArr.push(o.orderid);
			});
			// 操作主键
			var orderids = s = nmArr.join(",");
			var data = {
				orderids : orderids,
				reviewstatus : reviewstatus
			}
			// 发送风控审核状态请求到后台
			$.ajax({
				url: '${pageContext.request.contextPath}/withdraw/record/updateCashOutOrders',
				type: 'POST',
				dataType: 'json',
				data: data,
				success: function(resp){
					if(resp.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'审核完成',
							icon: 'info',
							fn:function(){
								// 关闭窗口
								$('#dialog').dialog("close");
								// 刷新列表
								$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								$('#datagrid1').datagrid('clearChecked');
							}
						});

					}else{
						$.messager.alert('警告', resp.lastError);
					}
				}
			});
			// 打开审核页面
			/*$('#dialog').dialog({
				title: '提现审核',
				href: '${pageContext.request.contextPath}/withdraw/record/toRecordReviewPage?orderids=' + orderids + '&reviewstatus=' + reviewstatus,
				dialogParams: data,
				width: 600,
				height: 300,
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
			});*/

			/*$.messager.confirm('确认',"确定要操作选中的记录吗",function(r){
				if (r){
					//提交到后台
					<%--  ${pageContext.request.contextPath}  --%>
					$.ajax({
						url: "/account/user/nullityUpate",
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
			});*/
		}

		//风控审核状态修改
		function financialIsPass(financialStatus){
			var rows = $('#datagrid1').datagrid("getSelections");
			if(rows.length == 0){
				$.messager.alert('警告','请选择要操作的记录');
				return;
			}
			//拼接主键
			var nmArr = [];
			$.each(rows,function(index,o){
				nmArr.push(o.orderid);
			});
			// 操作主键
			var orderids = s = nmArr.join(",");
			var data = {
				orderids : orderids,
				financialStatus : financialStatus
			}
			// 发送财务审核状态请求到后台
			$.ajax({
				/*url: '${pageContext.request.contextPath}/withdraw/record/toRecordReviewPage?orderids=' + orderids + '&financialStatus=' + financialStatus,*/
				url: '${pageContext.request.contextPath}/withdraw/record/updateFinancialStatus',
				type: 'POST',
				dataType: 'json',
				data: data,
				success: function(resp){
					if(resp.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'审核完成',
							icon: 'info',
							fn:function(){
								// 关闭窗口
								$('#dialog').dialog("close");
								// 刷新列表
								$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								$('#datagrid1').datagrid('clearChecked');
							}
						});

					}else{
						$.messager.alert('警告', resp.lastError);
					}
				}
			});
		}





	</script>
</body>
</html>

