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
		            	<form id="taskadd-form" method="post" enctype="multipart/form-data" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="type">绑定类型：</label>
									</td>
									<td class="form-editor">
										<input id="type" name="type" class="easyui-combobox" editable="false"
											   data-options="panelHeight:'auto', valueField:'id',textField:'text',
											   		required: true,data:[{id: 1,text: '银行卡'},{id: 2,text: '支付宝'}]"
											   value="1"/>
									</td>
								</tr>
								<tr id="tr2">
									<td class="form-label">
										<label for="bankchoice">银行名称：</label>
									</td>
									<td class="form-editor">
										<input id="bankchoice" name="bankchoice" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',
											   		required: true, url:'${pageContext.request.contextPath}/withdraw/bindcard/getBankNameMap',method: 'GET'"
											   value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="idcardname">真实姓名：</label>
									</td>
									<td class="form-editor">
										<input id="idcardname" name="idcardname" style="width: 300px;" class="easyui-textbox" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="bankcardid">卡号：</label>
									</td>
									<td class="form-editor">
										<input id="bankcardid" name="bankcardid" style="width: 300px;" class="easyui-numberbox" data-options="required: true" />
									</td>
								</tr>
								<tr id="tr1">
									<td class="form-label">
										<label for="bankname">银行全名：</label>
									</td>
									<td class="form-editor">
										<input id="bankname" name="bankname" style="width: 300px;" class="easyui-textbox"
											   data-options="required: false,prompt:'xxx银行xx省xx市xx支行'" />
										<%--<font style="color: red">银行名称: xxx银行xx省xx市xx支行</font>--%>
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

		var param1 = $.getOpenDialogParam("dialog1");
		//console.log(param1);

		//选择类型
		$("#type").combobox({
			onSelect : function(value){
				//console.log(value);
				if(value.id == 1){
					//$("#bankname").textbox('setValue','');
					$("#tr1").show();
					$("#tr2").show();
				}else{
					//$("#bankname").textbox('setValue','支付宝');
					$("#tr1").hide();
					$("#tr2").hide();
					$("#bankchoice").combobox('setValue','0');
					$("#bankname").textbox('setValue','');
				}
			}
		});

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
			let type = obj.type;
			if(type == 1){
				let bankname = obj.bankname;
				if(bankname == '' || bankname == null){
					$.messager.alert('警告','请完善银行信息');
					return ;
				}
			}

			//提交到后台
			$('#taskadd-form').form('submit', {
				url: "${pageContext.request.contextPath}/withdraw/bindcard/saveBindBankCards",
				onSubmit: function(param){
					// debugger
					// do some check
					// return false to prevent submit;
					param.userid = param1.userid;
					/*if(obj.bankchoice == 2){
						$("#bankname").val('支付宝')
						// param.bankname = '支付宝';
						console.log("银行名称："+$("#bankname").val)
					}*/
				},
				success:function(resp){
					//console.log(resp);
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'新增用户卡号信息成功',
							icon: 'info',
							//width: 300,
							//top:200 , //与上边距的距离
							fn:function(){
								// 关闭窗口
								$('#dialog1').dialog("close");
								// 刷新列表
								//console.log ( $('#datagrid1') );
								$('#datagrid2').datagrid('load');
								//清除之前勾选的行
								$('#datagrid2').datagrid('clearChecked');
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
			$('#dialog1').dialog("close");
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