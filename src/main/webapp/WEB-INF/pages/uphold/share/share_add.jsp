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
		             	<span class="heading-font1">新增每日分享配置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskadd-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="daysharelmt">每日分享次数：</label>
									</td>
									<td class="form-editor">
										<input id="daysharelmt" name="daysharelmt" class="easyui-numberbox" data-options="required: true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="timesharegold">每日分享获得金币：</label>
									</td>
									<td class="form-editor">
										<input id="timesharegold" name="timesharegold" class="easyui-numberbox" data-options="required: true"/>
									</td>
								</tr>
								<%--<tr>
									<td class="form-label">
										<label for="timesharediamond">每日分享获得钻石：</label>
									</td>
									<td class="form-editor">
										<input id="timesharediamond" name="timesharediamond" class="easyui-numberbox" data-options="required: true"/>
									</td>
								</tr>--%>
								<tr>
									<td class="form-label">
										<label for="nullity">启用禁用：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',
													   	data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]" value="0"/>
									</td>
								</tr>
		            		</table>

		            	</form>
		            </div>
				</div>
				
				<div class="opt-button-float">
					<button type="button" class="btn btn-primary" id="save1" name="save1">
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
		$("#save1").unbind();
		$("#save1").bind("click",function(){

			// 启用验证
			var isValid = $('#taskadd-form').form('enableValidation').form('validate');
			if(!isValid){
				$.messager.alert('警告','请先完善必输项');
				return;
			}
			
			// 所有字段有效
			var obj = $("#taskadd-form").serializeObject();
			//console.log(obj);

			//构建参数传入controller
			var data = {
				daysharelmt: obj.daysharelmt,
				nullity: obj.nullity,
				timesharegold: obj.timesharegold,
				timesharediamond: 0
			};

			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/uphold/share/saveShareConfig",
                type: 'POST',
                dataType: 'json',
                data: data,
                success: function(resp){
                	//{success: true, errors: Array(0), data: null, lastError: null}
                	//console.log(resp);
                	if(resp.success){

						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'新增每日分享配置成功',
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

                		//执行成功
                		// 关闭窗口 
                		//$('#dialog').dialog("close");
                		
                		// 刷新列表
                		//console.log ( $('#datagrid1') );
                		//$('#datagrid1').datagrid('load');
                		
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