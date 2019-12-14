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
		             	<span class="heading-font1">新增兑换管理</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" data-options="novalidate:true" >
							<input id="configid" name="configid" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="configname">产品名称：</label>
									</td>
									<td class="form-editor">
										<input id="configname" name="configname" class="easyui-textbox" data-options="required:true"/>
									</td>
									<%--<td class="form-label">
										<label for="diamond">兑换钻石：</label>
									</td>
									<td class="form-editor">
										<input id="diamond" name="diamond" class="easyui-numberbox" data-options="required: true" />
									</td>--%>
									<td class="form-label">
										<label for="exchgold">赠送金币：</label>
									</td>
									<td class="form-editor">
										<input id="exchgold" name="exchgold" class="easyui-numberbox" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="imagetype">图标类型：</label>
									</td>
									<td class="form-editor">
										<input id="imagetype" name="imagetype" class="easyui-combobox"
											   data-options="
											   		valueField:'id',
											   		textField:'text',
													data:[
														{id: 1,text: '微量钻石图标'},{id: 2,text: '较少钻石图标'},
														{id: 3,text: '少量钻石图标'},{id: 4,text: '中等钻石图标'},
														{id: 5,text: '偏高钻石图标'},{id: 6,text: '多数钻石图标'},
														{id: 7,text: '大量钻石图标'},{id: 8,text: '超级钻石图标'}
													]" value="1" />
									</td>
									<td class="form-label">
										<label for="sortid">排序标识：</label>
									</td>
									<td class="form-editor">
										<input id="sortid" name="sortid" class="easyui-numberbox" data-options="required: true" />
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
			url: "${pageContext.request.contextPath}/invest/exchg/queryExchgById",
			type: 'GET',
			dataType: 'json',
			data: {configid: param.configid},
			success: function(resp){
				//console.log(resp);
				if(resp.success){
					//执行成功
					$('#taskedit-form').form("load",{
						configid: resp.data.configid,
						configname: resp.data.configname,
						//diamond: resp.data.diamond,
						exchgold: resp.data.exchgold,
						imagetype: resp.data.imagetype,
						sortid: resp.data.sortid

					});

				}else{
					$.messager.alert('警告','');
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

			//构建参数传入controller
			var data = {
				configid: obj.configid,
				configname: obj.configname,
				//diamond: obj.diamond,
				diamond: 0,
				exchgold: obj.exchgold,
				imagetype: obj.imagetype,
				sortid: obj.sortid
			};
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/invest/exchg/updateExchConfig",
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