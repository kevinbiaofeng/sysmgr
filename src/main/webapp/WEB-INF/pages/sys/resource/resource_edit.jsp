<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑菜单</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" >
		             	<span class="heading-font1">菜单信息</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="resourceedit-form" method="post" data-options="novalidate:true" >
		            		<input id="id" name="id" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
		            			<tr>
		            				<td class="form-label">
										<label for="level1">等级：</label>
									</td>
									<td class="form-editor">
										<input id="level1" name="level" class="easyui-combobox" style="width: 70%" data-options="required:true,valueField:'id',textField:'text',readonly:true,data:[{id: 1,text: '一级'},{id: 2,text: '二级'},{id: 3,text: '三级'}]"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="icon1">菜单图标：</label>
									</td>
									<td class="form-editor">
										<input id="icon1" name="icon" class="easyui-textbox" style="width: 85%" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="parentMenuid1">父菜单ID：</label>
									</td>
									<td class="form-editor">
										<input id="parentMenuid1" name="parentMenuid" style="width: 70%" class="easyui-textbox" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="menuid1">菜单ID：</label>
									</td>
									<td class="form-editor">
										<input id="menuid1" name="menuid" class="easyui-textbox" style="width: 70%" data-options="required:true,readonly:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="name1">菜单名称：</label>
									</td>
									<td class="form-editor">
										<input id="name1" name="name" class="easyui-textbox" style="width: 70%" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="url">请求地址：</label>
									</td>
									<td class="form-editor">
										<input id="url" name="url" class="easyui-textbox" style="width: 70%" data-options="required:false"/>
									</td>
									
								</tr>
								<tr>
									<td class="form-label">
										<label for="sort">排序：</label>
									</td>
									<td class="form-editor">
										<input id="sort" name="sort" class="easyui-numberbox" style="width: 70%" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="description">描述：</label>
									</td>
									<td class="form-editor">
										<input id="description" name="description" class="easyui-textbox" style="width: 70%;height: 100px;" data-options="required:false,multiline:true"/>
									</td>
								</tr>
		            		</table>
		            	</form>
		            </div>
				</div>
				
				<div class="opt-button-float">
					<button type="button" class="btn btn-primary" id="save" name="save">
						<span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
					</button>
					<button type="button" class="btn btn-default" id="cancel" name="cancel">
						<span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
					</button>
				</div>
				
			</div>
		</div>
	</div>
	
	
	
	<script type="text/javascript">
		
		//加载参数
		var param = $.getOpenDialogParam("dialog");
		
		//ajax 加载form表单数据
		$.ajax({
			url: "${pageContext.request.contextPath}/sys/resource/querySysResourceById",
            type: 'GET',
            dataType: 'json',
            data: {id: param.id},
            success: function(resp){
            	//console.log(resp);
            	if(resp.success){
            		var obj = resp.data;
            		//执行成功
            		$('#resourceedit-form').form("load",{
            			id: obj.id,
            			level: obj.lev,
            			icon: obj.icon,
            			menuid: obj.menuid,
    					parentMenuid: obj.parentMenuid,
    					name: obj.name,
    					url: obj.url,
    					description: obj.description,
    					sort: obj.sort
            		});
            		
            	}else{
            		$.messager.alert('警告','根据id查询角色失败');
            	}
            }
         });
		
		//保存
		$("#save").unbind();
		$("#save").bind("click",function(){
			// 启用验证
			var isValid = $('#resourceedit-form').form('enableValidation').form('validate');
			//alert(validate);
			if(isValid){
				// 所有字段有效
				var obj = $("#resourceedit-form").serializeObject();
				//console.log(obj);
				var data = {
					id: obj.id,
					menuid: obj.menuid,
					parentMenuid: obj.parentMenuid,
					name: obj.name,
					code: obj.menuid,
					icon: obj.icon,
					description: obj.description,
					url: obj.url,
					lev: obj.level,
					sort: obj.sort
				};
				//console.log(data);
				//提交到后台
				$.ajax({
					url: "${pageContext.request.contextPath}/sys/resource/updateResource",
	                type: 'PUT',
	                dataType: 'json',
	                data: data,
	                success: function(resp){
	                	if(resp.success){
	                		//执行成功
	                		// 关闭窗口 
	                		$('#dialog').dialog("close");
	                		// 刷新列表
	                		//console.log ( $('#datagrid1') );
	                		$('#treegrid1').treegrid('load');
	                		
	                	}else{
	                		$.messager.alert('警告','保存菜单失败');
	                	}
	                }
	            });
			}
			
		});
		
		//取消
		$("#cancel").unbind();
		$("#cancel").bind("click",function(){
			//alert("取消");
			$('#dialog').dialog("close");
			
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
		
	</script>
	
</body>
</html>