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
<title>亲友圈管理</title>
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
										<label for="creatergameid">圈主玩家ID：</label>
									</td>
									<td class="form-editor">
										<input id="creatergameid" name="creatergameid" class="easyui-numberbox" />
									</td>

									<td class="form-label">
										<label for="creaternickname">圈主游戏昵称：</label>
									</td>
									<td class="form-editor">
										<input id="creaternickname" name="creaternickname" class="easyui-textbox" />
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

							<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="nullityUp(2)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>冻结</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="nullityUp(1)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>解冻</span>
							</button>

						</div>
					</div>

					<table id="datagrid1" class="easyui-datagrid" title="亲友圈管理"
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
							idField: 'groupid',
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
								<th data-options="field:'groupid',align:'center',fixed:false,width:30">亲友圈编号</th>
								<th data-options="field:'groupname',align:'center',fixed:false,width:30">亲友圈名称</th>
								<th data-options="field:'creatergameid',align:'center',fixed:false,width:30">圈主玩家ID</th>
								<th data-options="field:'creaternickname',align:'center',fixed:false,width:30">圈主游戏昵称</th>
								<th data-options="field:'groupstatusDesc',align:'center',fixed:false,width:30">亲友圈状态</th>
								<th data-options="field:'limitDesc',align:'center',fixed:false,width:30">亲友圈人数/上限</th>
								<th data-options="field:'ingot',align:'center',fixed:false,width:30">亲友圈钻石</th>
								<th data-options="field:'consumeingot',align:'center',fixed:false,width:30">亲友圈消耗钻石</th>
								<th data-options="field:'battlecreate',align:'center',fixed:false,width:30">亲友圈开房次数</th>
								<th data-options="field:'createdatetime',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">亲友圈创建时间</th>
								<th data-options="field:'option',align:'center',fixed:false,width:60,formatter: function(value,row,index){return optortion(value,row,index)}">操作</th>
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
				url: '${pageContext.request.contextPath}/ffar/manage/queryIMGroupPropertyWithPage',
				queryParams : {
					creatergameid : obj.creatergameid,
					creaternickname : obj.creaternickname
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
			var b1 = "<button class='layui-btn layui-btn-xs' onclick='showIMGroup({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			b1 = b1.format(row.groupid,"查看亲友");
			return b1;
		}

		//打开 查看亲友 窗口
		function showIMGroup(groupid){
			$('#dialog').dialog({
				title: '查看亲友',
				href: '${pageContext.request.contextPath}/ffar/manage/toGroupMemberPage?groupid=' + groupid,
				dialogParams: {groupid: groupid},
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

		//冻结 解冻  状态（1：可用 2：不可用）
		function nullityUp(groupstatus){
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
						nmArr.push(o.groupid);
					});
					var groupids = s = nmArr.join(",");
					var data = {
						groupids : groupids,
						groupstatus : groupstatus
					}
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/ffar/manage/groupstatusUpate",
						type: 'PUT',
						dataType: 'json',
						data: data,
						success: function(resp){
							if(resp.success){
								var msg = "";
								if(groupstatus == 1){
									msg = "解冻成功";
								}else{
									msg = "冻结成功";
								}

								//提示操作成功
								$.messager.alert({
									title:'提示',
									msg: msg,
									icon: 'info',
									fn:function(){
										// 刷新列表
										$('#datagrid1').datagrid('load');
										//清除之前勾选的行
										$('#datagrid1').datagrid('clearChecked');
									}
								});

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

