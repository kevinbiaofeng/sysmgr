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
		             	<span class="heading-font1">编辑游戏</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="kindid">选择模块：</label>
									</td>
									<td class="form-editor">
										<input id="kindid" name="kindid" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',required: true,disabled: true,
											   	url:'${pageContext.request.contextPath}/uphold/gamels/loadGameModuleData',method: 'GET'"/>
									</td>
									<td class="form-label">
										<label for="modulename">安装包名：</label>
									</td>
									<td class="form-editor">
										<input id="modulename" name="modulename" class="easyui-textbox" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="clientversion">模块版本号：</label>
									</td>
									<td class="form-editor">
										<input id="clientversion" name="clientversion" class="easyui-textbox" data-options="required: true"
											value="0.0.0.0" />
									</td>
									<td class="form-label">
										<label for="resversion">资源版本号：</label>
									</td>
									<td class="form-editor">
										<input id="resversion" name="resversion" class="easyui-numberbox" data-options="required: true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="sortid">游戏排序：</label>
									</td>
									<td class="form-editor">
										<input id="sortid" name="sortid" class="easyui-numberbox" data-options="required: true" />
									</td>
									<td class="form-label">
										<label for="nullity">禁用状态：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]" value="0"/>
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
		//console.log(param);

		//ajax 加载form表单数据
		$.ajax({
			url: "${pageContext.request.contextPath}/uphold/gamels/queryGamelsById",
			type: 'GET',
			dataType: 'json',
			data: {kindid: param.kindid},
			success: function(resp){
				//console.log(resp);
				if(resp.success){
					//执行成功
					$('#taskedit-form').form("load",{
						kindid: resp.data.kindid,
						clientversion: resp.data.clientversionDesc,
						kindid: resp.data.kindid,
						modulename: resp.data.modulename,
						nullity: resp.data.nullity,
						resversion: resp.data.resversion,
						sortid: resp.data.sortid

					});

				}else{
					$.messager.alert('警告','根据id查询注册赠送失败');
				}
			}
		});

		//保存
		$("#save").unbind();
		$("#save").bind("click",function(){

			// 启用验证
			var isValid = $('#taskedit-form').form('enableValidation').form('validate');
			if(!isValid){
				$.messager.alert('警告','请先完善必输项');
				return;
			}
			
			// 所有字段有效
			var obj = $("#taskedit-form").serializeObject();
			//console.log(obj);

			let clientversion = obj.clientversion;
			let f2 = reg(clientversion);
			//console.log(f2);
			if(!f2){
				$.messager.alert('警告','客户端版本格式不正确');
				return;
			}

			var kindid = $("#kindid").combobox("getValue");
			//console.log(kindname);

			//构建参数传入controller
			var data = {
				//kindname: kindname,
				clientversionDesc: obj.clientversion,
				kindid: kindid,
				modulename: obj.modulename,
				nullity: obj.nullity,
				resversion: obj.resversion,
				sortid: obj.sortid
			};
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/uphold/gamels/updateGamels",
                type: 'PUT',
                dataType: 'json',
                data: data,
                success: function(resp){
                	//{success: true, errors: Array(0), data: null, lastError: null}
                	//console.log(resp);
                	if(resp.success){
                		//执行成功
                		// 关闭窗口 
                		$('#dialog').dialog("close");
                		
                		// 刷新列表
                		//console.log ( $('#datagrid1') );
                		$('#datagrid1').datagrid('load');
                		
                	}else{
                		$.messager.alert('警告',resp.lastError);
                	}
                }
            });
			
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