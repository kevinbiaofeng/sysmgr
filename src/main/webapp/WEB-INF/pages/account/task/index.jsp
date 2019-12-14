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
<title>任务管理</title>
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
										<label for="taskname">任务名称：</label>
									</td>
									<td class="form-editor">
										<input id="taskname" name="taskname" class="easyui-textbox" />
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
				<!-- <div class="panel-heading">
					<span class="panel-title"></span>
					<a href="javascript:void(0);" class="pull-right panel-toggle">
						<i class="iconfont">&#xe603;</i>
					</a>
				</div> -->
				<div class="panel-body" >
					
					<!-- 操作按钮区域 -->
					<div id="tb" style="display:none" class="opt-button-float">
						<div class="group-button opt-button-float">

							<button class="layui-btn layui-btn-sm layui-btn-normal" id="add" name="add">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-danger" id="remove" name="remove">
								<i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
							</button>

						<%--<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="nullityUp(1)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>冻结</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="nullityUp(0)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>解冻</span>
							</button>--%>

							<!-- <button class="layui-btn layui-btn-sm layui-btn-warm" id="edit" name="edit">
                                <i class="iconfont" data-icon="&#xe653;">&#xe653;</i><span>编辑</span>
                            </button> -->
							<!-- <button class="layui-btn layui-btn-sm" id="detail" name="detail">
                                <i class="iconfont" data-icon="&#xe67a;">&#xe67a;</i><span>详情</span>
                            </button> -->
							<!-- <button class="layui-btn layui-btn-sm layui-btn-danger" id="delete" name="delete">
                                <i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
                            </button> -->
						</div>
					</div>

					<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
					<table id="datagrid1" class="easyui-datagrid" title="任务信息"

						   data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
<%-- 							url: '${pageContext.request.contextPath}/account/task/queryTaskWithPage', --%>
							fit:false,
							method: 'GET',
							toolbar: '#tb',
							idField: 'taskid',
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
								<th data-options="field:'taskid',align:'center',fixed:false,width:30">任务标识</th>
								<th data-options="field:'taskname',align:'center',fixed:false,width:30">任务名称</th>
								<th data-options="field:'taskdescription',align:'center',fixed:false,width:30">任务描述</th>
								<th data-options="field:'tasktypeDesc',align:'center',fixed:false,width:30">任务类型</th>
								<th data-options="field:'usertypeDesc',align:'center',fixed:false,width:30">用户类型</th>
								<th data-options="field:'timetypeDesc',align:'center',fixed:false,width:30">时间类型</th>
								<th data-options="field:'taskawardDesc',align:'center',fixed:false,width:40">任务奖励</th>
								<th data-options="field:'innings',align:'center',fixed:false,width:30">完成目标数</th>
								<th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">冻结状态</th>
								<th data-options="field:'merchant',align:'center',fixed:false,width:60">商户号</th>
								<th data-options="field:'account',align:'center',fixed:false,width:30">操作账号</th>
								<th data-options="field:'createdDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">创建时间</th>
								<th data-options="field:'updatedDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">修改时间</th>
								<th data-options="field:'option',align:'center',fixed:false,width:30,formatter: function(value,row,index){return optortion(value,row,index)}">操作</th>

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

			//新增
			$("#add").unbind();
			$("#add").bind("click",function(){
				$('#dialog').dialog({
					title: '新增任务',
					href: '${pageContext.request.contextPath}/account/task/toTaskAddPage',
					width: 800,
					height: 400,
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
			});

			//删除
			$("#remove").unbind();
			$("#remove").bind("click",function(){
				removeTaskByids();
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
				url:'${pageContext.request.contextPath}/account/task/queryTaskWithPage',
				pageNumber:1,
				queryParams : {
					nullity : obj.nullity,
					taskname: obj.taskname
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
			var a = $.rowFormater('taskid',row,'edit',{edit: '编辑'});
			return a;
		}

		function optionEditEvent(id){
			//console.log(id);
			/*var row = $('#datagrid1').datagrid("getSelected");
			if(row == null){
				$.messager.alert('警告','请先选中行');
				return;
			}*/
			openEditWindown(id);
		}

		//打开 编辑 窗口
		function openEditWindown(id){
			$('#dialog').dialog({
				title: '修改任务',
				href: '${pageContext.request.contextPath}/account/task/toTaskEditPage',
				dialogParams: {taskid: id},
				width: 800,
				height: 400,
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

		function removeTaskByids(){
			var rows = $('#datagrid1').datagrid("getSelections");
			//console.log(rows);
			if(rows.length == 0){
				$.messager.alert('警告','请选择需要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要删除选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						//console.log(index);
						//console.log(obj.id);
						nmArr.push(o.taskid);
					});
					var ids = s = nmArr.join(",");
					/*var data = {
						configids : ids
					}*/
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/account/task/removeTaskByIds?taskids="+nmArr,
						type: 'DELETE',
						//dataType: 'json',
						//data: data,
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
									msg:'删除成功',
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

								//$.messager.alert('警告','删除成功');

							}else{
								$.messager.alert('警告','删除失败');
							}
						}
					});
				}
			});

		}

	</script>
</body>
</html>

