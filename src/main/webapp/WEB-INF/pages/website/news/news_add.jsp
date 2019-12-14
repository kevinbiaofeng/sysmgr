<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" >
		             	<span class="heading-font1">新增公告信息</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskadd-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="platformtype1">平台类型：</label>
									</td>
									<td class="form-editor">
										<input id="platformtype1" name="platformtype" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',required: true,
											   		data:[
											   			{id: 1,text: 'LUA'}
<%--											   			,{id: 2,text: '三端'},{id: 3,text: 'U3D'},{id: 4,text: 'H5'}--%>
											   		]" value="1"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="noticetitle">新闻标题：</label>
									</td>
									<td class="form-editor">
										<input id="noticetitle" name="noticetitle" class="easyui-textbox" style="width: 350px;" data-options="required: true,validType:'length[1,20]'" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="publisher">新闻来源：</label>
									</td>
									<td class="form-editor">
										<input id="publisher" name="publisher" class="easyui-textbox" style="width: 350px;" data-options="required: true,validType:'length[1,20]'" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="sortid">新闻排序：</label>
									</td>
									<td class="form-editor">
										<input id="sortid" name="sortid" class="easyui-numberbox" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="nullity">状态：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',required: true,
											   		data:[
											   			{id: 0,text: '启用'},{id: 1,text: '禁用'}
											   		]" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="ishot">热门：</label>
									</td>
									<td class="form-editor">
										<input id="ishot" name="ishot" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',required: true,
											   		data:[
											   			{id: 0,text: '不热门'},{id: 1,text: '热门'}
											   		]" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="istop">置顶：</label>
									</td>
									<td class="form-editor">
										<input id="istop" name="istop" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',required: true,
											   		data:[
											   			{id: 0,text: '不置顶'},{id: 1,text: '置顶'}
											   		]" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="mobliecontent">手机内容：</label>
									</td>
									<td class="form-editor">
										<input id="mobliecontent" name="mobliecontent" class="easyui-textbox" style="width: 350px;height: 80px;"
											   data-options="required: true,multiline:true" />
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

		//保存
		$("#save").unbind();
		$("#save").bind("click",function(){

			// 启用验证
			var isValid = $('#taskadd-form').form('enableValidation').form('validate');
			if(!isValid){
				$.messager.alert('警告','请先完善必输项');
				return;
			}
			
			// 所有字段有效
			var obj = $("#taskadd-form").serializeObject();
			//console.log(obj);

			var data = obj;

			//return;

			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/website/news/saveSystemNotice",
				type: 'POST',
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
							msg:'新增新闻公告成功',
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
						$.messager.alert('警告',resp.lastError);
					}
				}
			});

			//提交到后台
			/*$('#taskadd-form').form('submit', {
				url: "${pageContext.request.contextPath}/website/news/saveSystemNotice",
				onSubmit: function(param){
					// do some check
					// return false to prevent submit;
				},
				success:function(resp){
					//console.log(resp);
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'新增公告成功',
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
						$.messager.alert('警告',o.lastError);
					}
				}
			});*/
			
		});

		function reg(value){
			var reg = /^[0-9]+(\.)+[0-9]+(\.)+[0-9]+(\.)+[0-9]$/;
			var fg = reg.test(value);
			//console.log(fg);
			return fg;
		}
		
		//重置
		$("#resetForm").unbind();
		$("#resetForm").bind("click",function(){
			//alert("重置");
			$('#useradd-form').form('clear');
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