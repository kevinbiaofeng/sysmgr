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
		             	<span class="heading-font1">新增签到物品配置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskadd-form" method="post" enctype="multipart/form-data" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<input id="resourceUrlParam" name="resourceUrl" type="hidden" class="easyui-textbox"
									   value="sign"/>
								<input id="field5" name="field5" type="hidden" class="easyui-textbox"/>
								<tr>
									<td class="form-label">
										<label for="packageid">礼包名称：</label>
									</td>
									<td class="form-editor">
										<input id="packageid" name="packageid" class="easyui-combobox" editable="false"
											   data-options="valueField:'id',textField:'text',required: true,
											   	url:'${pageContext.request.contextPath}/uphold/sign/loadGamePackageData?nullity=0',method: 'GET'"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="typeid6">物品类型：</label>
									</td>
									<%--data:[{id: 0,text: '游戏币'},{id: 1,text: '钻石'},{id: 2,text: '道具'},{id: 3,text: '奖券'}]--%>
									<td class="form-editor">
										<input id="typeid6" name="typeid" class="easyui-combobox"
											   data-options="panelHeight:'auto', valueField:'id',textField:'text',
													   	data:[{id: 0,text: '游戏币'},{id: 2,text: '道具'}]" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="propertyid">道具：</label>
									</td>
									<td class="form-editor">
										<input id="propertyid" name="propertyid" class="easyui-combobox"
											   data-options="panelHeight:'auto', valueField:'id',textField:'text',
													   	data:[{id: 0,text: '无奖励'},{id: 306,text: '大喇叭'}]" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label id="desc" for="goodsnum">物品数量：</label>
									</td>
									<td class="form-editor">
										<input id="goodsnum" name="goodsnum" class="easyui-numberbox"  data-options="required: true" value="0"/>
									</td>
<%--									precision="2"--%>
								</tr>
								<tr>
									<td class="form-label">
										<label for="image">物品图片：</label>
									</td>
									<td class="form-editor">
										<input id="image" name="image" class="easyui-filebox"
											   data-options="required: true,buttonText: '选择图片',accept: 'image/gif,image/jpg,image/png'"/>
										<input id="resourceurl" name="resourceurl" type="hidden" data-options="required: true"/>
										<button type="button" class="btn btn-primary" id="upload" name="upload"
												onclick="uploadMethod()">
											<span aria-hidden="true">上传</span>
										</button>
										<a id="searchImage" target="_blank">查看</a>
										<span style="color: red">[大小：不大于2M] </span>
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
		//初始化
		$(function () {
			$("#searchImage").hide();//隐藏
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
				},
				error: function (resp) {
					$.messager.alert('警告', "没有查到上传文件的路径");
				}
			});
		});
		//上传方法
		function uploadMethod() {
			var formData = new FormData(document.getElementById("taskadd-form"));

			if (0 == formData.get("image").size) {
				$.messager.alert('警告', '请选择文件');
				return;
			}

			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/uploadDownload/uploadImage",
				data: formData,
				dataType: 'JSON',
				cache: false,
				processData: false,
				contentType: false,
				success: function (resp) {
					if (resp.success == false) {
						$.messager.alert('警告', resp.lastError);
					} else {
						$.messager.alert('提示', '上传文件成功');
						$("#resourceurl").val(resp.data.resourceurl);//属性赋值
						$("#upload").hide();//隐藏
						$("#searchImage").show();//显示
					}
				},
				error: function (resp) {
					$.messager.alert('警告', resp.lastError);
				}
			});
		};
		//查看图片
		$("#searchImage").unbind();
		$("#searchImage").bind("click", function () {
			$("#searchImage").attr("href", $("#field5").val() + $("#resourceurl").val())
		});

		$("#typeid6").combobox({
			onSelect : function(value){
				//console.log(value);
				if(value.id == 0){
					// 游戏币
					$("#desc").text("金币数量");
				}else if(value.id == 2){
					//  道具
					$("#desc").text("道具数量");
				}
			}
		});

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
			if (obj.resourceurl == "") {
				$.messager.alert('警告', '请上传图片');
				return;
			}

			let files = $("#image").filebox('files');

			let file = files[0];

			//构建参数传入controller
			var data = {
				goodsnum: obj.goodsnum,
				packageid: obj.packageid,
				propertyid: obj.propertyid,
				typeid: obj.typeid
			};

			$('#taskadd-form').form('submit', {
				url: "${pageContext.request.contextPath}/uphold/sign/saveGamePackageGoods",
				onSubmit: function(){

				},
				success:function(resp){
					console.log("206:" + resp)
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'新增签到物品配置成功',
							icon: 'info',
							fn:function(){
								// 关闭窗口
								$('#dialog').dialog("close");
								// 刷新列表
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

		function reg(value){
			var reg = /^[0-9]+(\.)+[0-9]+(\.)+[0-9]+(\.)+[0-9]$/;
			var fg = reg.test(value);
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