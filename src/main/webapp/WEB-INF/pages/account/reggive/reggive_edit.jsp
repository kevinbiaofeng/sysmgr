<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增注册赠送</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" >
		             	<span class="heading-font1">注册赠送</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="reggiveaedit-form" method="post" data-options="novalidate:true" >
							<input id="configid" name="configid" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="merchant">商户号：</label>
									</td>
									<td class="form-editor">
										<input id="merchant" name="merchant" style="width: 200px;" class="easyui-textbox" data-options="required: true,disabled: true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="scorecount">注册赠送金币数：</label>
									</td>
									<td class="form-editor">
										<input id="scorecount" name="scorecount" class="easyui-numberbox" data-options="required: true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="upgradescorecount">升级赠送金币数：</label>
									</td>
									<td class="form-editor">
										<input id="upgradescorecount" name="upgradescorecount" class="easyui-numberbox" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="visitorscorecount">游客赠送金币数：</label>
									</td>
									<td class="form-editor">
										<input id="visitorscorecount" name="visitorscorecount" class="easyui-numberbox" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="withdrawratio">提现手续费比率：</label>
									</td>
									<td class="form-editor">
										<input id="withdrawratio" name="withdrawratio" class="easyui-numberbox" data-options="required:true"/>
										<font style="color: red">提现手续费比率:比如千分之20,就直接输入20</font>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="withdrawlowerlimit">提现手续费下限：</label>
									</td>
									<td class="form-editor">
										<input id="withdrawlowerlimit" name="withdrawlowerlimit" class="easyui-numberbox" data-options="required:false" value="0"/>
										<font style="color: red">提现手续费下限:最低手续费,如果为0没有下限</font>
									</td>

								</tr>
								<tr>
									<td class="form-label">
										<label for="withdrawupperlimit">提现手续费上限：</label>
									</td>
									<td class="form-editor">
										<input id="withdrawupperlimit" name="withdrawupperlimit" class="easyui-numberbox" data-options="required:false" value="0"/>
										<font style="color: red">提现手续费上限:最高手续费,如果为0没有上限</font>
									</td>
								</tr>

								<tr>
									<td class="form-label">
										<label for="withdrawscorelowerlimit">提现金额下限：</label>
									</td>
									<td class="form-editor">
										<input id="withdrawscorelowerlimit" name="withdrawscorelowerlimit" class="easyui-numberbox" data-options="required:false" value="100"/>
										<font style="color: red">提现金额下限:最低提现金额,如果为0没有最低</font>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="withdrawscoreupperlimit">提现金额上限：</label>
									</td>
									<td class="form-editor">
										<input id="withdrawscoreupperlimit" name="withdrawscoreupperlimit" class="easyui-numberbox" data-options="required:false" value="20000"/>
										<font style="color: red">提现金额上限:最高提现金额,如果为0没有上限</font>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="reservescorelowerlimit">平台保底金额：</label>
									</td>
									<td class="form-editor">
										<input id="reservescorelowerlimit" name="reservescorelowerlimit" class="easyui-numberbox" data-options="required:false" value="10"/>
										<font style="color: red">平台最低保留金额:平台保底金额,如果为0没有保底金额</font>
									</td>
								</tr>

								<tr>
									<td class="form-label">
										<label for="platformtype">平台类型：</label>
									</td>
									<td class="form-editor">
										<%--<input id="permission" name="permission" style="width: 60%" class="easyui-textbox" data-options="required:true"/>--%>
										<input id="platformtype" name="platformtype" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: 1,text:  'LUA'}
<%--										,{id: 2,text: 'U3D'},{id: 3,text: 'H5'}--%>
										]" value="1" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="registwebsite">注册站点：</label>
									</td>
									<td class="form-editor">
										<%--<input id="permission" name="permission" style="width: 60%" class="easyui-textbox" data-options="required:true"/>--%>
										<input id="registwebsite" name="registwebsite" class="easyui-textbox" data-options="required: false" />
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
			url: "${pageContext.request.contextPath}/account/reggive/queryReggiveById",
			type: 'GET',
			dataType: 'json',
			data: {configid: param.configid},
			success: function(resp){
				//console.log(resp);
				if(resp.success){
					//执行成功
					$('#reggiveaedit-form').form("load",{
						merchant: resp.data.merchant,
						configid: resp.data.configid,

						withdrawscoreupperlimit: resp.data.withdrawscoreupperlimit,
						withdrawscorelowerlimit: resp.data.withdrawscorelowerlimit,
						reservescorelowerlimit: resp.data.reservescorelowerlimit,

						withdrawupperlimit: resp.data.withdrawupperlimit,
						withdrawlowerlimit: resp.data.withdrawlowerlimit,
						withdrawratio: resp.data.withdrawratio,
						upgradescorecount: resp.data.upgradescorecount,
						visitorscorecount: resp.data.visitorscorecount,
						//diamondcount: resp.data.diamondcount,
						registwebsite: resp.data.registwebsite,
						platformtype: resp.data.platformtype,
						scorecount: resp.data.scorecount
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
			var isValid = $('#reggiveaedit-form').form('enableValidation').form('validate');
			if(!isValid){
				$.messager.alert('警告','请先完善必输项');
				return;
			}
			
			// 所有字段有效
			var obj = $("#reggiveaedit-form").serializeObject();
			//console.log(obj);

			//构建参数传入controller
			var data = {

				configid: obj.configid,

				withdrawscoreupperlimit: obj.withdrawscoreupperlimit < 0 ? 0 : obj.withdrawscoreupperlimit,
				withdrawscorelowerlimit: obj.withdrawscorelowerlimit < 0 ? 0 : obj.withdrawscorelowerlimit,
				reservescorelowerlimit: obj.reservescorelowerlimit < 0 ? 0 : obj.reservescorelowerlimit,

				withdrawratio: obj.withdrawratio,
				withdrawupperlimit: obj.withdrawupperlimit,
				withdrawlowerlimit: obj.withdrawlowerlimit,
				//diamondcount: obj.diamondcount,
				diamondcount: 0,
				upgradescorecount: obj.upgradescorecount,
				visitorscorecount: obj.visitorscorecount,
				registwebsite: obj.registwebsite,
				platformtype: obj.platformtype,
				scorecount: obj.scorecount
			};
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/account/reggive/updateReggive",
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