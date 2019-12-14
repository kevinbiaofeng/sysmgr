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
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" enctype="multipart/form-data" data-options="novalidate:true" >
							<input id="orderids" name="orderids" type="hidden" class="easyui-textbox" value="${orderids}"/>
							<input id="reviewstatus" name="reviewstatus" type="hidden" class="easyui-textbox" value="${reviewstatus}" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="remark">备注：</label>
									</td>
									<td class="form-editor">
										<input id="remark" name="remark" class="easyui-textbox" style="width: 300px;height:100px;" data-options="required: false,multiline:true"/>
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
					<button type="button" class="btn btn-default" id="cancel1" name="cancel1">
						<span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
					</button>
				</div>
				
			</div>
		</div>
	</div>
	
	
	
	<script type="text/javascript">

		var param = $.getOpenDialogParam("dialog");
		//console.log(param);

		/*if(reviewstatus == 1){
			//审核通过
			//$("#remark").textbox('setText','审核通过');
		}else if(reviewstatus == 2){
			//审核不通过
			//$("#remark").textbox('setText','审核不通过');
		}*/

		//ajax 加载form表单数据
		<%--${pageContext.request.contextPath}--%>
		/*$.ajax({
			url: "/withdraw/bindcard/queryBindBankCardsById",
			type: 'GET',
			dataType: 'json',
			data: {bindid: param1.bindid},
			success: function(resp){
				//console.log(resp);
				if(resp.success){
					//执行成功
					$('#taskedit-form').form("load",resp.data);
				}else{
					$.messager.alert('警告','根据id查询失败');
				}
			}
		});*/

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

			//提交到后台
			$('#taskedit-form').form('submit', {
				url: "${pageContext.request.contextPath}/withdraw/record/updateCashOutOrders",
				onSubmit: function(param){
					// do some check
					// return false to prevent submit;
					//param.userid = param1.userid;
				},
				success:function(resp){
					//console.log(resp);
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'审核完成',
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
			});

		});

		
		//取消
		$("#cancel1").unbind();
		$("#cancel1").bind("click",function(){
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