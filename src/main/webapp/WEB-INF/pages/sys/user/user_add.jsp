<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增管理员</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" >
		             	<span class="heading-font1">新增用户</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="useradd-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" style="width: 100%;">
		            			<tr>
									<td class="form-label">
										<label for="name1">姓名：</label>
									</td>
									<td class="form-editor">
										<input id="name1" name="name" style="width: 70%" class="easyui-textbox" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="loginName1">登录名：</label>
									</td>
									<td class="form-editor">
										<input id="loginName1" name="loginName" style="width: 70%" class="easyui-textbox" data-options="required: true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="loginPassword">登录密码：</label>
									</td>
									<td class="form-editor">
										<input id="loginPassword" name="loginPassword" style="width: 70%" class="easyui-passwordbox" data-options="required:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="confirmPassword">确认密码：</label>
									</td>
									<td class="form-editor">
										<input id="confirmPassword" name="confirmPassword" style="width: 70%" class="easyui-passwordbox" data-options="required:true"/>
									</td>
								</tr>
								<tr id="mcom">
									<td class="form-label">
										<label for="commissionratio">抽水比例：</label>
									</td>
									<td class="form-editor">
										<input id="commissionratio" name="commissionratio" style="width: 70%" class="easyui-numberbox" data-options="required:true"/>
										<font color="red">抽水比例:千分之20,直接输入20</font>
									</td>
								</tr>
								<tr id="mph">
									<td class="form-label">
										<label for="phone">联系电话：</label>
									</td>
									<td class="form-editor">
										<input id="phone" name="phone" style="width: 70%" class="easyui-textbox" data-options="required:true,validType:'length[8,15]'"/>
									</td>
								</tr>
								<tr id="mem">
									<td class="form-label">
										<label for="mail">电子邮箱：</label>
									</td>
									<td class="form-editor">
										<input id="mail" name="mail" style="width: 70%" class="easyui-textbox" data-options="required:true,validType:'length[8,20]'"/>
									</td>
								</tr>
								<tr id="mqq">
									<td class="form-label">
										<label for="qqAccount">QQ号：</label>
									</td>
									<td class="form-editor">
										<input id="qqAccount" name="qqAccount" style="width: 70%" class="easyui-numberbox" data-options="required:true,validType:'length[5,15]'"/>
									</td>
								</tr>
								<tr id="mwa">
									<td class="form-label">
										<label for="wechatAccount">微信号：</label>
									</td>
									<td class="form-editor">
										<input id="wechatAccount" name="wechatAccount" style="width: 70%" class="easyui-textbox" data-options="required:true,validType:'length[3,20]'"/>
									</td>
								</tr>
								<tr id="msa">
									<td class="form-label">
										<label for="safePassword">安全密码：</label>
									</td>
									<td class="form-editor">
										<input id="safePassword" name="safePassword" style="width: 70%" class="easyui-textbox" data-options="required:true,validType:'length[6,15]'"/>
										<font color="red">用于查看用户联系方式</font>
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
					<button type="button" class="btn btn-warning" id="resetForm" name="resetForm">
						<span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
					</button>
					<button type="button" class="btn btn-default" id="cancel" name="cancel">
						<span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
					</button>
				</div>
				
			</div>
		</div>
	</div>
	
	
	
	<script type="text/javascript">
		$(function(){
			$.get("${pageContext.request.contextPath}/sys/user/queryCurrUserById", function(data){
				if(data.merchant != null){
					$("#mno").remove();
					$("#mkey").remove();
					$("#mcom").remove();
					$("#mph").remove();
					$("#mem").remove();
					$("#mqq").remove();
					$("#mwa").remove();
					$("#msa").remove();
				}
			});
		})
		
	
		//校验登录名
		$("#loginName1").textbox({
			onChange: function(newVal,oldVal) {
				var f = checkLoginName(newVal);
				//登录名   true 存在 -- false不存在
				if(f){
					$.messager.alert('警告','该登录名已经被占用');
					return;
				}
			}
		});
		
		/*
		* 校验登录名  true  存在  false 不存在
		*/
		function checkLoginName(loginName){
			var fg = false;
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/sys/user/checkLoginName",
                type: 'GET',
                dataType: 'json',
                data: {loginName: loginName},
                async: false,
                success: function(resp){
                	//console.log(resp);
                	if(resp.success){
                		fg = resp.data;
                	}
                }
            });
			return fg;
		}
	 
		//保存
		$("#save").unbind();
		$("#save").bind("click",function(){
			
			// 启用验证
			var isValid = $('#useradd-form').form('enableValidation').form('validate');
			if(!isValid){
				$.messager.alert('警告','请先完善必输项');
				return;
			}
			
			// 所有字段有效
			var obj = $("#useradd-form").serializeObject();
			//判断两次密码是否一致
			if(!(obj.loginPassword === obj.confirmPassword)){
				$.messager.alert('警告','两次密码不一致');
				return;
			}
			//校验登录名
			var f = checkLoginName(obj.loginName);
			//登录名   true 存在 -- false不存在
			if(f){
				$.messager.alert('警告','该登录名已经被占用');
				return;
			}
			//构建参数传入controller
			var data = {
				name: obj.name,
				loginName: obj.loginName,
				loginPassword: obj.loginPassword,
				confirmPassword: obj.confirmPassword,
				commissionratio: obj.commissionratio,
				phone: obj.phone,
				mail: obj.mail,
				qqAccount: obj.qqAccount,
				wechatAccount: obj.wechatAccount,
				safePassword: obj.safePassword
			};
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/sys/user/saveSysUser",
                type: 'POST',
                dataType: 'json',
                data: data,
                success: function(resp){
                	if(resp.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'创建用户成功',
							icon: 'info',
							fn:function(){
								// 关闭窗口
								$('#dialogAdd').dialog("destroy");
								// 刷新列表
								$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								$('#datagrid1').datagrid('clearChecked');
							}
						});
                		
                	}else{
                		$.messager.alert('警告','保存管理员失败');
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
			$('#dialogAdd').dialog("destroy");
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