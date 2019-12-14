<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增</title>
</head>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/common/common.js"></script>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" enctype="multipart/form-data" data-options="novalidate:true" >
							<input id="bindid" name="bindid" type="hidden" class="easyui-textbox" />
							<input id="userid" name="userid" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="type">绑卡类型：</label>
									</td>
									<td class="form-editor">
										<input id="type" name="type" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',disabled: true,
											   		required: true,data:[{id: 1,text: '银行卡'},{id: 2,text: '支付宝'}]"
											   value="1"/>
									</td>
								</tr>
								<tr id="tr2">
									<td class="form-label">
										<label for="bankchoice">银行名称：</label>
									</td>
									<td class="form-editor">
										<input id="bankchoice" name="bankchoice" class="easyui-combobox" editable="f
										alse"
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
										<input id="bankcardid" name="bankcardid" style="width: 300px;" class="easyui-numberbox"
											   data-options="required: true" />
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
								<tr>
									<td class="form-label">
										<label for="nullity">是否启用：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',
											   		data:[
											   			{id: '0',text: '启用'},{id: '1',text: '禁用'}
											   		]" />
									</td>
								</tr>
								<tr>
									<%--<span style="color: red">
										启用此卡会使其他卡被禁用，禁用此卡会使其他某张卡被启用
									</span>--%>
										<label style="color: red">启用此卡会使其他卡被禁用，禁用此卡会使其他某张卡被启用</label>
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
		function regCard(data) {
			var regExp =  /^([1-9]{1})(\d{14}|\d{18})$/;
			return regExp.test(data)
		}


		var param1 = $.getOpenDialogParam("dialog1");
		console.log(param1);
		$("#bankcardid").blur(function(){
			debugger;
			var cardNumber = $("#bankcardid").val();
			if (cardNumber.length !=19){
				$.messager.alert('警告','卡号输入请控制在19位长度');
				return;
			}
			if (!isRealNum(cardNumber)){
				$.messager.alert('警告','验证卡号,不正确');
				return;
			}
		});

		//ajax 加载form表单数据
		$.ajax({
			url: "${pageContext.request.contextPath}/withdraw/bindcard/queryBindBankCardsById",
			type: 'GET',
			dataType: 'json',
			data: {bindid: param1.bindid, userid: param1.userid},
			success: function(resp){
				//console.log(resp);
				if(resp.success){
					//执行成功
					$('#taskedit-form').form("load",resp.data);
					//2代表支付宝
					if(resp.data.type == 2){
						$("#tr1").hide();
						$("#tr2").hide();
					}

				}else{
					$.messager.alert('警告','根据id查询失败');
				}
			}
		});
		/**
		 *判断是否是数字
		 *
		 **/
		function isRealNum(val){
			if(val === "" || val ==null){
				return false;
			}
			var reg=/^[0-9]*$/
			var pattern=new RegExp(reg);
			console.log(pattern.test("123"));
			if (pattern.test(val)){
				return true;
			}
		}

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
			let bankchoice = obj.bankchoice;
			if(bankchoice == 1){
				let bankname = obj.bankname;
				if(bankname == '' || bankname == null){
					$.messager.alert('警告','请完善银行信息');
					return ;
				}
			}
			debugger;
			var cardNumber = $("#bankcardid").val();
			if (cardNumber.length !=19){
				$.messager.alert('警告','卡号输入请控制在19位长度');
				return;
			}

			if (!isRealNum(cardNumber)){
				$.messager.alert('警告','验证卡号,不正确');
				return;
			}

			//提交到后台
			$('#taskedit-form').form('submit', {
				url: "${pageContext.request.contextPath}/withdraw/bindcard/saveBindBankCards",
				onSubmit: function(param){
					if(obj.bankchoice == 2){
						debugger;
						// param.bankname = '支付宝';
					}
				},
				success:function(resp){
					//console.log(resp);
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'修改用户卡号信息成功',
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