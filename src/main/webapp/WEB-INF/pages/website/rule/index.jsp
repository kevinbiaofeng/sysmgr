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
<title>游戏规则</title>
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
					</div>
					<div class="panel-body">
						<form id="search-form" method="post">
							<table class="form-table" border="0" cellpadding="1" cellspacing="2">
								<tr>
									<td class="form-label">
										<label for="kindid">游戏名称：</label>
									</td>
									<td class="form-editor">
										<input id="kindid" name="kindid" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',
											   	url:'${pageContext.request.contextPath}/uphold/gamels/loadGameModuleData',method: 'GET'"/>
									</td>

									<td class="form-label">
										<label for="type">图片类型：</label>
									</td>
									<td class="form-editor">
										<input id="type" name="type" class="easyui-combobox" editable="false"
											   data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: 1,text: '横屏'},{id: 2,text: '竖屏'}]" />
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

							<button class="layui-btn layui-btn-sm layui-btn-normal" id="add" name="add">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-danger" id="remove" name="remove">
								<i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
							</button>

							<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="nullityUp(1)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>禁用</span>
							</button>
							<button class="layui-btn layui-btn-sm layui-btn-warm" onclick="nullityUp(0)">
								<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>启用</span>
							</button>
						</div>
					</div>

					<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
					<table id="datagrid1" class="easyui-datagrid" title="游戏规则"

						   data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							method: 'GET',
							fit:false,
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
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'id',align:'center',fixed:false,hidden:true">id</th>
							<th data-options="field:'kindid',align:'center',fixed:false,width:40">游戏标识</th>

							<th data-options="field:'kindname',align:'center',fixed:false,width:40">游戏名称</th>

							<th data-options="field:'ruleimg',align:'center',fixed:false,width:60,formatter: function(value,row,index){return showImge(value,row,index)}">游戏规则图片</th>
							<th data-options="field:'typeDesc',align:'center',fixed:false,width:30">图片类型</th>

							<th data-options="field:'sortid',align:'center',fixed:false,width:30">玩法排序</th>
							<th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">状态</th>
							<th data-options="field:'merchant',align:'center',fixed:false,width:30">商户号</th>

							<th data-options="field:'collectdate',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">创建时间</th>

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
					title: '新增游戏规则',
					href: '${pageContext.request.contextPath}/website/rule/toRuleAddPage',
					dialogParams: {field5: $("#field5").val()},
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
				removeByids();
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
				url: '${pageContext.request.contextPath}/website/rule/queryGameRuleWithPage',
				queryParams : {
					kindid: obj.kindid,
					type: obj.type
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

		//游戏规则 游戏规则图片
		function showImge(value,row,index){
			var img = "<a href='"+row.ruleimg+"' target='_blank'><img style='height: 50px;width: 100px;' src='"+row.ruleimg+"' /></a>";
			img = img.format(row.ruleimg,row.ruleimg);
			return img;
		}

		//操作按钮
		function optortion(value,row,index){
			var a = $.rowFormater('id',row,'edit',{edit: '编辑'});
			return a;
		}

		function optionEditEvent(id){
			openEditWindown(id);
		}

		//打开 编辑 窗口
		function openEditWindown(id){
			$('#dialog').dialog({
				title: '修改游戏规则',
				href: '${pageContext.request.contextPath}/website/rule/toRuleEditPage',
				dialogParams: {id: id, field5: $("#field5").val()},
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
		}

		//根据主键删除
		function removeByids(){
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
						nmArr.push(o.id);
					});
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/website/rule/removeGameRuleByIds?ids="+nmArr,
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
							}else{
								$.messager.alert('警告','删除失败');
							}
						}
					});
				}
			});

		}

		//批量操作 启用 禁用
		function nullityUp(nullity){

			var rows = $('#datagrid1').datagrid("getSelections");
			//console.log(rows);
			//console.log(nullity);
			if(rows.length == 0){
				$.messager.alert('警告','请选择要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要操作选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						//console.log(index);
						//console.log(obj.id);
						nmArr.push(o.id);
					});
					var ids = s = nmArr.join(",");
					var data = {
						nullity : nullity,
						ids : ids
					}
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/website/rule/batchNullityUpdate",
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

								// 刷新列表
								//console.log ( $('#datagrid1') );
								//$('#datagrid1').datagrid('load');

								var msg = '';
								if(nullity == 0){
									//$.messager.alert('警告','批量启用成功');
									msg = '批量启用成功';
								}else{
									//$.messager.alert('警告','批量禁用成功');
									msg = '批量禁用成功';
								}

								$.messager.alert({
									title:'提示',
									msg:msg,
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

