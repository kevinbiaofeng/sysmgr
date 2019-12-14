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
		             	<span class="heading-font1">编辑游戏规则</span>
		            </div>
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" enctype="multipart/form-data" data-options="novalidate:true" >
                            <input id="id" name="id" type="hidden" class="easyui-textbox" />
							<input id="resourceUrl" name="resourceUrl" type="hidden" class="easyui-textbox"
								   value="gameRule"/>
							<input id="field5" name="field5" type="hidden" class="easyui-textbox"/>
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="kindid1">游戏名称：</label>
									</td>
									<td class="form-editor">
										<input id="kindid1" name="kindid" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',required: true ,disabled: true,
											   	url:'${pageContext.request.contextPath}/uphold/gamels/loadGameModuleData',method: 'GET'"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="type1">图片类型：</label>
									</td>
									<td class="form-editor">
										<input id="type1" name="type" class="easyui-combobox" data-options="valueField:'id',textField:'text',required: true,
											data:[{id: 1,text: '横屏'}]" value="1" editable="false"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="sortid">排序：</label>
									</td>
									<td class="form-editor">
										<input id="sortid" name="sortid" class="easyui-numberbox" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="image">规则图片：</label>
									</td>

									<td id="td1" class="form-editor">
										<input id="ruleimg" name="ruleimg" class="easyui-textbox" />
										<a id="showImg" target="_blank">查看</a> | <a id="deleteImg" href="javascript:;">删除</a>
									</td>

									<td id="td2" class="form-editor" style="display: none">
										<input id="image" name="image" class="easyui-filebox"
											   data-options="required: true,buttonText: '选择图片',accept: 'image/png'" />
										<button type="button" class="btn btn-primary" id="upload" name="upload"
												onclick="uploadMethod()">
											<span aria-hidden="true">上传</span>
										</button>
										<span style="color: red">注：U3D玩法规则不在此处配置 (仅支持 png 格式图片)[大小：不大于2M] </span>
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

		//初始化加载
		$(function () {
			//查询图片上传前缀路径
			var configKey = "WebSiteConfig";
			$.ajax({
				method: "GET",
				url: "${pageContext.request.contextPath}/website/stand/queryConfigInfoByConfigKey?configKey=" + configKey,
				processData: false,
				contentType: false,
				crossDomain: true,
				success: function (resp) {
					$("#field5").val(resp.data.field5);//赋值
					queryGameRuleById();
				},
				error: function (resp) {
					$.messager.alert('警告', "没有查到上传文件的路径");
				}
			});
		});

		//上传方法
		function uploadMethod() {
			//获取表单所有name数据
			var formData = new FormData(document.getElementById("taskedit-form"));
			//验证文件是否为空
			console.log("formData.resourceUrl:" + formData.get("resourceUrl"))
			console.log("formData.image:" + formData.get("image").size)
			if (0 == formData.get("image").size) {
				$.messager.alert('警告', '请选择文件');
				return;
			}
			$.ajax({
				method: "POST",
				url: "${pageContext.request.contextPath}/uploadDownload/uploadImage",
				data: formData,
				processData: false,
				contentType: false,
				crossDomain: true,
				success: function (resp) {
					if (resp.success == false) {
						$.messager.alert('警告', resp.lastError);
					} else {
						$.messager.alert('提示', "上传文件成功");
						$("#td2").hide();//隐藏
						$("#td1").show();//显示
						$("#ruleimg").textbox("setValue", resp.data.ruleimg);//属性赋值
						$('#ruleimg').textbox({prompt: resp.data.ruleimg})//将值显示在文本框
						<%--$("#showImg").attr("href", "${pageContext.request.contextPath}/static/Upload" + resp.data.ruleimg)//给该ID赋值--%>
						$("#showImg").attr("href", $("#field5").val() + resp.data.ruleimg)//给该ID赋值
						<%--$('#image').filebox({prompt: "${pageContext.request.contextPath}/static/Upload" + resp.data.imgUrl})--%>
					}
				},

				error: function (resp) {
					$.messager.alert('警告', resp.lastError);
				}
			});
		};

		//加载参数
		var param = $.getOpenDialogParam("dialog");
		//console.log(param);

		//ajax 加载form表单数据
		function queryGameRuleById(){
			$.ajax({
				url: "${pageContext.request.contextPath}/website/rule/queryGameRuleById",
				type: 'GET',
				dataType: 'json',
				data: {id: param.id},
				success: function(resp){
					//console.log(resp);
					if(resp.success){
						//执行成功
						$('#taskedit-form').form("load",resp.data);

						$("#showImg").attr("href", $("#field5").val() + resp.data.ruleimg)

					}else{
						$.messager.alert('警告','根据id查询失败');
					}
				}
			});
		}

		//删除
		$("#deleteImg").unbind();
		$("#deleteImg").bind("click",function(){
			//清空input
			$("#ruleimg").textbox('setValue', '');
			$("#td1").hide();
			$("#td2").show();
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
			if (obj.ruleimg == "") {
				$.messager.alert('警告', '请上传图片');
				return;
			}
			//console.log(obj);

			//return;

			//提交到后台
			$('#taskedit-form').form('submit', {
				url: "${pageContext.request.contextPath}/website/rule/saveGameRule",
				onSubmit: function(param){

				},
				success:function(resp){
					//console.log(resp);
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'修改游戏规则成功',
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