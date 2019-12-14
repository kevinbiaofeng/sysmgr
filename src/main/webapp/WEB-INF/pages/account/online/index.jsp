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
<title>在线玩家</title>
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
										<label for="serverid">游戏房间：</label>
									</td>
									<td class="form-editor">
										<input id="serverid" name="serverid" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/account/online/loadGameRoomComboData?nullity=0',method: 'GET'"
											   value="0" />
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
						
							<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="cleanOnLine()">
                            	<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>清除卡线</span>
                        	</button>
						</div>
					</div>

					<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
					<table id="datagrid1" class="easyui-datagrid" title="在线玩家"

						   data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							fit:false,
							queryParams: {serverid : 0},
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
								<!-- <th data-options="field:'aaaa',align:'center',fixed:false,hidden:true">编号1111</th> -->
								<%--<th data-options="field:'id',align:'center',fixed:false,hidden:true,width:30">主键</th>--%>
								<th data-options="field:'userid',align:'center',fixed:false,width:30">用户标识</th>
								<th data-options="field:'nickName',align:'center',fixed:false,width:30">用户昵称</th>
								<th data-options="field:'gameId',align:'center',fixed:false,width:30">玩家ID</th>
								<th data-options="field:'kindName',align:'center',fixed:false,width:30">所在游戏</th>
								<th data-options="field:'serverName',align:'center',fixed:false,width:30">所在房间</th>
								<th data-options="field:'enterip',align:'center',fixed:false,width:30">进入IP</th>
								<th data-options="field:'collectdate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">进入时间</th>

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

		//var abc = "<shiro:principal property='name'/>";
		//console.log("abc==========>" + abc);

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
				//$('#isLocked').combobox('setValue','0');
				//$('#isDeleted').combobox('setValue','0');
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
				url:'${pageContext.request.contextPath}/account/online/queryGamescorelockerWithPage',
				pageNumber:1,
				queryParams : {
					nickName : obj.nickName,
					gameId : obj.gameId,
					serverid : obj.serverid
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

		//清除卡线
		function cleanOnLine(){
			var rows = $('#datagrid1').datagrid("getSelections");
			//console.log(rows);
			if(rows.length == 0){
				$.messager.alert('警告','请选择需要清除卡线的玩家');
				return;
			}

			$.messager.confirm('确认',"确定要清除卡线的玩家吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						//console.log(index);
						//console.log(obj.id);
						nmArr.push(o.userid);
					});
					var userIds = s = nmArr.join(",");
					var data = {
						userIds : userIds
					}
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/account/online/batchCleanLocker",
						type: 'PUT',
						dataType: 'json',
						data: data,
						success: function(resp){
							//{success: true, errors: Array(0), data: null, lastError: null}
							//console.log(resp);
							if(resp.success){
								//执行成功
								// 关闭窗口
								//$('#dialog').dialog("close");

								//提示操作成功
								$.messager.alert({
									title:'提示',
									msg:'清除成功',
									icon: 'info',
									//width: 300,
									//top:200 , //与上边距的距离
									fn:function(){
										// 刷新列表
										//console.log ( $('#datagrid1') );
										$('#datagrid1').datagrid('load');
										//清除之前勾选的行
										$('#datagrid1').datagrid('clearChecked');
									}
								});

								// 刷新列表
								//console.log ( $('#datagrid1') );
								//$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								//$('#datagrid1').datagrid('clearChecked');

								//$.messager.alert('警告','清除成功');

							}else{
								$.messager.alert('警告','清除失败');
							}
						}
					});
				}
			});

		}



	</script>
</body>
</html>

