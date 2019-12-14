<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户域名管理</title>
<style type="text/css">
	.datagrid-view {
	    min-height: 60px;
	}
</style>
</head>
<body>

    <table id="datagrid2" class="easyui-datagrid" toolbar="#td"
				data-options="
					singleSelect: true,
					fitColumns: true,
					nowrap:false,
					collapsible: true,
					multiSort: true,
					checkOnSelect: true,
					cache: false,
					fit:false,
					method: 'GET',
					idField: 'id',
					rownumbers: false,
					pagination: true,
					autoRowHeight: false,
					pageSize: 6,
					pageNumber: 1,
					pageList: [6]
					">
		<thead>
			<tr>
				<th data-options="field:'domainId',hidden:true">域名</th>
				<th data-options="field:'domainUrl',width:100,align:'center'">域名</th>
				<th data-options="field:'type',width:80,align:'center',formatter:typeFormatter">类型</th>
				<th data-options="field:'status',width:80,align:'center',formatter:statusFormatter">状态</th>
				<th data-options="field:'createUserName',width:60,align:'center'">创建人</th>
				<th data-options="field:'createDate',width:120,align:'center',formatter:function(value,row,index){return $.dateFormatter(value)}">创建日期</th>
				<th data-options="field:'updateUserName',width:60,align:'center'">修改人</th>
				<th data-options="field:'updateDate',width:120,align:'center',formatter:function(value,row,index){return $.dateFormatter(value)}">修改时间</th>
				<th data-options="field:'option',width:150,align:'center',formatter:opertion">操作</th>
			</tr>
		</thead>
	</table>
	
	<script type="text/javascript" language="javascript">
		function typeFormatter(value, row, index){
			if(value==1){
				return '推广';
			}else if(value==2){
				return 'API';
			}else if(value==3){
				return '主域名';
			}
		}
		
		function statusFormatter(value, row, index){
			if(value==1){
				return '删除';
			}else if(value==0){
				return '运行';
			}else{
				return '草稿';
			}
		}
	
		//操作按钮
		function opertion(value, row, index){
			var a = "<button id='editDomain' class='layui-btn layui-btn-xs layui-btn-warm' onclick='optionEditDomain({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			a = a.format(row.id, "编辑");
			var b = "<button id='delDomain' class='layui-btn layui-btn-xs' onclick='optionDelDomain({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			b = b.format(row.id,"删除");
			return a + b;
		}
		
		function optionEditDomain(id){
			$('<div></div>').dialog({
				id: 'editPanle',
			    title: '编辑', 
			    href: '${pageContext.request.contextPath}/sys/user/merchant/toDomainEditPage', 
			    dialogParams: {id: id},
			    width: 400,
			    height: 310,
			    closed: false,
			    cache: false,
			    modal: true,
			    collapsible: true,
			    maximizable: false,
			    resizable: true,
			    onClose : function() {
                    $(this).dialog('destroy');
                },
			    shadow: true,
			    content: ''
			}).window('center');
		}
		
		function optionDelDomain(id){
			$.messager.confirm('确认',"确定要删除选中的记录吗",function(r){
				if (r){
					var domainId = id;
					$.ajax({
						url: "${pageContext.request.contextPath}/sys/merchant/domain/deleteMerchantDomainById",
						type: "POST",
						data: {id:domainId},
						success: function(resp){
							if(resp.success){
								$.messager.alert({
									title:'提示',
									msg:'删除成功',
									icon: 'info',
									fn:function(){
										$('#datagrid2').datagrid('load');
										$('#datagrid2').datagrid('clearChecked');
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
		
		//添加toolbar工具栏
		$(function(){
			var param = $.getOpenDialogParam("newDialog");
			$("#datagrid2").datagrid({
				url:'${pageContext.request.contextPath}/sys/merchant/domain/list',
				pageNumber:1,
				queryParams : {
					userId : param.id
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！",
				toolbar:[{
					iconCls:'icon-add',
					text:'新增',
					onClick:function(){
						$("<div></div>").dialog({
							id: 'addPanle',
							title: '新增',
							href: '${pageContext.request.contextPath}/sys/user/merchant/toDomainAddPage',
							dialogParams: {id : param.id},
							width: 400,
							height: 300,
							closed: false,
							cache: false,
							modal: true,
							collapsible: true,
							maximizable: false,
							resizable: true,
							shadow: true,
							onClose : function() {
			                    $(this).dialog('destroy');
			                },
							content: ''
						}).window('center');
					}
				}]
			})
		})
	</script>
	
</body>
</html>