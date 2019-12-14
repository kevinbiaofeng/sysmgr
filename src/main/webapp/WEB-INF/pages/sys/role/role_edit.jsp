<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增角色管理</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" >
		             	<span class="heading-font1">角色信息</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="roleedit-form" method="post" data-options="novalidate:true" >
		            		<input id="id" name="id" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="code">角色CODE：</label>
									</td>
									<td class="form-editor">
										<input id="code1" name="code" style="width: 60%" class="easyui-textbox" data-options="required: true,disabled: true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="name">角色名称：</label>
									</td>
									<td class="form-editor">
										<input id="name1" name="name" style="width: 60%" class="easyui-textbox" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="perm">角色权限：</label>
									</td>
									<td class="form-editor">
										<%--<input id="permission" name="permission" style="width: 60%" class="easyui-textbox" data-options="required:true"/>--%>
										<input id="perm" name="perm" class="easyui-combobox" name="perm[]" style="width:70%;"
											   data-options="
													url:'${pageContext.request.contextPath}/static/data/per.json',
													method:'get',
													valueField:'id',
													textField:'text',
													multiple:true,
													panelHeight:'auto'
													">
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
			url: "${pageContext.request.contextPath}/sys/role/querySysRoleById",
            type: 'GET',
            dataType: 'json',
            data: {id: param.id},
            success: function(resp){
            	//console.log(resp);
            	if(resp.success){
            		//执行成功
            		$('#roleedit-form').form("load",{
            			id: resp.data.id,
    					name: resp.data.name,
    					code: resp.data.code,
						perm: resp.data.perm
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
			var isValid = $('#roleedit-form').form('enableValidation').form('validate');
			if(!isValid){
				$.messager.alert('警告','请先完善必输项');
				return;
			}
			
			// 所有字段有效
			var obj = $("#roleedit-form").serializeObject();
			console.log(obj);

			var s = "";
			if(typeof(obj.perm)=='string'){
				s = obj.perm;
			}else{
				var nmArr = [];
				$.each(obj.perm,function(index,o){
					//console.log(index);
					//console.log(obj.id);
					nmArr.push(o);
				});

				s = nmArr.join(",");
			}

			//构建参数传入controller
			var data = {
				id: obj.id,
				name: obj.name,
				permission: s
			};
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/sys/role/updateSysRole",
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

						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'修改角色成功',
							icon: 'info',
							//width: 300,
							//top:200 , //与上边距的距离
							fn:function(){
								// 关闭窗口
								$('#dialog').dialog("close");
								// 刷新列表
								//console.log ( $('#datagrid1') );
								$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								$('#datagrid1').datagrid('clearChecked');
							}
						});
                		
                	}else{
                		$.messager.alert('警告','修改角色失败');
                	}
                }
            });
			
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