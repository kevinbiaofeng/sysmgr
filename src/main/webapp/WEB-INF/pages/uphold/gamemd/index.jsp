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
<title>游戏管理</title>
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
										<label for="gamename">模块名称：</label>
									</td>
									<td class="form-editor">
										<input id="gamename" name="gamename" class="easyui-textbox" />
									</td>

									<td class="button-group" colspan="2">
										<button type="button" class="btn btn-primary" id="search" name="search">
											<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
										</button>
										<%--<button type="button" class="btn btn-warning" id="reset" name="reset">
											<span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
										</button>--%>
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
						</div>
					</div>

					<table id="datagrid1" class="easyui-datagrid" title="游戏模块"

						   data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							url: '${pageContext.request.contextPath}/uphold/gamemd/queryGamemdWithPage',
							method: 'GET',
							fit:false,
							toolbar: '#tb',
							idField: 'gameid',
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
								<th data-options="field:'gameid',align:'center',fixed:false,width:30">模块标识</th>
								<th data-options="field:'gamename',align:'center',fixed:false,width:30">模块名称</th>
								<th data-options="field:'serverversionDesc',align:'center',fixed:false,width:30">服务端版本</th>
								<th data-options="field:'clientversionDesc',align:'center',fixed:false,width:30">客户端版本</th>
								<th data-options="field:'databasename',align:'center',fixed:false,width:30">数据库名</th>
								<th data-options="field:'databaseaddr',align:'center',fixed:false,width:30">数据库地址</th>
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
					title: '新增游戏模块',
					href: '${pageContext.request.contextPath}/uphold/gamemd/toGamemdAddPage',
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
				url: '${pageContext.request.contextPath}/uphold/gamemd/queryGamemdWithPage',
				queryParams : {
					gamename : obj.gamename
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
			var a = $.rowFormater('gameid',row,'edit',{edit: '编辑'});
			return a;
		}

		function optionEditEvent(id){
			openEditWindown(id);
		}

		//打开 编辑 窗口
		function openEditWindown(id){
			$('#dialog').dialog({
				title: '修改任务',
				href: '${pageContext.request.contextPath}/uphold/gamemd/toGamemdEditPage',
				dialogParams: {gameid: id},
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

		function removeByids(){
			var rows = $('#datagrid1').datagrid("getSelections");
			if(rows.length == 0){
				$.messager.alert('警告','请选择需要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要删除选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						nmArr.push(o.gameid);
					});
					var ids = s = nmArr.join(",");
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/uphold/gamemd/removeGamemdByIds?gameids="+nmArr,
						type: 'DELETE',
						success: function(resp){
							if(resp.success){
								//提示操作成功
								$.messager.alert({
									title:'提示',
									msg:'删除成功',
									icon: 'info',
									fn:function(){
										// 刷新列表
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

	</script>
</body>
</html>

