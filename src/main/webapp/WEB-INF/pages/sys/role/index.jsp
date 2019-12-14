<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>角色管理</title>
<%@ include file="/static/pub/include.jsp"%>
<style type="text/css">
.datagrid-cell, .datagrid-cell-group, .datagrid-header-rownumber, .datagrid-cell-rownumber{
	height: 40px;
	margin-top: 10px;
}
.datagrid-view{
	min-height: 80px;
}
</style>
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
										<label for="name">角色名称：</label>
									</td>
									<td class="form-editor">
										<input id="name" name="name" class="easyui-textbox" />
									</td>
									<td class="form-label">
										<label for="code">角色CODE：</label>
									</td>
									<td class="form-editor">
										<input id="code" name="code" class="easyui-textbox" />
									</td>
									<td class="form-label">
										<label for="isDeleted">是否删除：</label>
									</td>
									<td class="form-editor">
										<input id="isDeleted" name="isDeleted" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: 0,text: '正常'},{id: 1,text: '删除'}]" value="0"/>
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
						</div>
					</div>
					
					<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
					<table id="datagrid1" title="系统角色" 
					
						data-options="
							singleSelect: true,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							queryParams: {isDeleted : 0},
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
								<th data-options="field:'id',align:'center',fixed:false,hidden:true,width:30">主键</th>
								<th data-options="field:'code',align:'center',fixed:false,width:30,sortable:true">角色code</th>
								<th data-options="field:'name',align:'center',fixed:false,width:30">角色名称</th>
								<th data-options="field:'createdByName',align:'center',fixed:false,width:30">创建者</th>
								<th data-options="field:'createdDate',align:'center',width:40,fixed:false,formatter:function(value,row,index){return $.dateFormatter(value)}">创建日期</th>
								<th data-options="field:'updatedDate',align:'center',width:40,fixed:false,formatter:function(value,row,index){return $.dateFormatter(value)}">修改日期</th>
								<th data-options="field:'isDeleted',align:'center',fixed:false,width:30,formatter:function(value,row,index){return dictionaryDeleted(value,row,index)}">是否删除</th>
								<th data-options="field:'option',align:'center',width:30,fixed:false,formatter: function(value,row,index){return optortion(value,row,index)}">操作</th>
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
		
		//设置日期
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
				    title: '新增角色', 
				    href: '${pageContext.request.contextPath}/sys/role/toRoleAddPage',  
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
				},"open");
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
		
		//数据加载成功时触发
		$('#datagrid1').datagrid({
			onLoadSuccess:function(data){
				//要判断或者执行的代码
				var arr = data.rows;
				$.each(arr,function(i,o){
					//是否删除
					$('#deleted'+o.id).switchbutton({
						checked: o.isDeleted == '0',
						onChange: function(checked){
							checkedStatus(o.id,checked);
						}
					});
					
				});
			}
		});

		//操作按钮
		function optortion(value,row,index){
			var a = $.rowFormater('id',row,'edit',{edit: '编辑'});
			var b = "<button class='layui-btn layui-btn-xs' onclick='optionRoleResourceEvent({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			b = b.format(row.id,"分配资源");
			return a + " |" + b;
		}
		
		/**
		id 主键
		checkedType  1 是否锁定 2 是否删除 
		checked true 选中 false不选中
		*/
		function checkedStatus(id,checked){
			var data = {id: id,checked: checked};
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/sys/role/checkedStatus",
                type: 'POST',
                dataType: 'json',
                data: data,
                success: function(resp){
                	if(resp.success){
                		$('#datagrid1').datagrid('load');
                		
                	}
                }
            });
		}
		
		function dictionaryDeleted(value,row,index){
			var deleted = "<input type='checkbox' id='deleted{0}' name='deleted' class='easyui-switchbutton' onText='正常' offText='删除' />";
			var result = deleted.format(row.id);
			return result;
		}
		
		function search(){
			var obj = $("#search-form").serializeObject();
			$('#datagrid1').datagrid({
				url: '${pageContext.request.contextPath}/sys/role/querySysRoleWithPage',
				queryParams : {
					name : obj.name,
					code : obj.code,
					isDeleted : obj.isDeleted
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});
		
		function optionEditEvent(id){
			openEditWindown(id);
		}
		
		//打开 编辑 窗口
		function openEditWindown(id){
			$('#dialog').dialog({    
			    title: '编辑管理员', 
			    href: '${pageContext.request.contextPath}/sys/role/toRoleEditPage', 
			    dialogParams: {id: id},
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
			},"open");
			
		}


		function optionRoleResourceEvent(id){
			openResourceWindown(id);
		}

		//打开 资源 窗口
		function openResourceWindown(id){
			$('#dialog').dialog({
				title: '分配资源',
				href: '${pageContext.request.contextPath}/sys/role/toRoleResourcePage',
				dialogParams: {id: id},
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
			},"open");
		}
	</script>
</body>
</html>








