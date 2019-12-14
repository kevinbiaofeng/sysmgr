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
		             	<span class="heading-font1">编辑签到物品配置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" enctype="multipart/form-data" data-options="novalidate:true" >
							<input id="goodsid" name="goodsid" type="hidden" class="easyui-textbox" />
							<input id="resourceUrlParam" name="resourceUrl" type="hidden" class="easyui-textbox"
								   value="sign"/>
							<input id="field5" name="field5" type="hidden" class="easyui-textbox"/>
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
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
											   data-options="valueField:'id',textField:'text',
													   	data:[{id: 0,text: '游戏币'},{id: 2,text: '道具'}]" value="0"/>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td class="form-label">--%>
<%--										<label for="propertyid">道具：</label>--%>
<%--									</td>--%>
<%--									<td class="form-editor">--%>
<%--										<input id="propertyid" name="propertyid" class="easyui-combobox"--%>
<%--											   data-options="valueField:'id',textField:'text',--%>
<%--													   	data:[{id: 0,text: '无奖励'},{id: 306,text: '大喇叭'}]" value="0"/>--%>
<%--									</td>--%>
<%--								</tr>--%>
								<tr>
									<td class="form-label">
										<label id="desc" for="goodsnum">物品数量：</label>
									</td>
									<td class="form-editor">
										<input id="goodsnum" name="goodsnum" class="easyui-numberbox" data-options="required: true" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="resourceurl">物品图片：</label>
									</td>
									<td id="td1" class="form-editor">
										<input id="resourceurl" name="resourceurl" class="easyui-textbox" editable="false"/>
										<a id="showImg" target="_blank">查看</a> | <a id="deleteImg"
																					href="javascript:;">删除</a>
									</td>
									<td id="td2" class="form-editor" style="display: none">
										<input id="image" name="image" class="easyui-filebox"
											   data-options="buttonText: '选择图片',accept: 'image/gif,image/jpg,image/png'"/>
										<button type="button" class="btn btn-primary" id="upload" name="upload"
												onclick="uploadMethod()">
											<span aria-hidden="true">上传</span>
										</button>
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
					queryById();
				},
				error: function (resp) {
					$.messager.alert('警告', "没有查到上传文件的路径");
				}
			});



		});

		//ajax 加载form表单数据 查询并展示出来
		function queryById(){
			$.ajax({
				url: "${pageContext.request.contextPath}/uphold/sign/queryGamePackageGoodsById",
				type: 'GET',
				dataType: 'json',
				data: {goodsid: param.goodsid},
				success: function(resp){
					//console.log(resp);
					if(resp.success){
						//执行成功
						$('#taskedit-form').form("load",{
							goodsid: resp.data.goodsid,
							goodsnum: resp.data.goodsnum,
							packageid: resp.data.packageid,
							propertyid: resp.data.propertyid,
							// uploadUrl: resp.data.uploadUrl,
							resourceurl: resp.data.resourceurl,
							typeid: resp.data.typeid
						});
						$("#showImg").attr("href", $("#field5").val() + resp.data.resourceurl)
						<%--$("#showImg").attr("href","${pageContext.request.contextPath}/static" + resp.data.uploadUrl);--%>
					}else{
						$.messager.alert('警告','根据id查询失败');
					}
				}
			});
		}

		//上传方法
		function uploadMethod() {
			//获取表单所有name数据
			var formData = new FormData(document.getElementById("taskedit-form"));
			//验证文件是否为空
			// console.log("formData.resourceUrl:" + formData.get("resourceUrl"))
			// console.log("formData.image:" + formData.get("image").size)
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
						$.messager.alert('提示', "文件上传成功");
						$("#td2").hide();//隐藏
						$("#td1").show();//显示
						$("#resourceurl").textbox("setValue", resp.data.resourceurl);//属性赋值
						<%--$('#imgUrl').textbox({prompt: "${pageContext.request.contextPath}/static/Upload" + resp.data.imgUrl})//将值显示在文本框--%>
						<%--$("#showImg").attr("href", "${pageContext.request.contextPath}/static/Upload" + resp.data.imgUrl)//给该ID赋值--%>
						$('#resourceurl').textbox({prompt: resp.data.resourceurl})//将值显示在文本框
						$("#showImg").attr("href", $("#field5").val() + resp.data.resourceurl)//给该ID赋值
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

		//删除
		$("#deleteImg").unbind();
		$("#deleteImg").bind("click",function(){
			$("#resourceurl").textbox('setValue', '');
			$("#td1").hide();
			$("#td2").show();
		});

		//保存
		$("#save1").unbind();
		$("#save1").bind("click",function(){

			// 启用验证
			var isValid = $('#taskedit-form').form('enableValidation').form('validate');
			if(!isValid){
				$.messager.alert('警告','请先完善必输项');
				return;
			}

			// 所有字段有效
			var obj = $("#taskedit-form").serializeObject();
			//console.log(obj);
			if (obj.resourceurl == "") {
				$.messager.alert('警告', '请上传图片');
				return;
			}

			let files = $("#image").filebox('files');
			//console.log(files);
			let file = files[0];

			//构建参数传入controller
			var data = {
				goodsid: obj.goodsid,
				goodsnum: obj.goodsnum,
				packageid: obj.packageid,
				propertyid: obj.propertyid,
				typeid: obj.typeid
			};

			$('#taskedit-form').form('submit', {
				url: "${pageContext.request.contextPath}/uphold/sign/saveGamePackageGoods",
				onSubmit: function(){
					// do some check
					// return false to prevent submit;
				},
				success:function(resp){
					// console.log(resp);
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'编辑签到物品配置成功',
							icon: 'info',
							//width: 300,
							//top:200 , //与上边距的距离
							fn:function(){
								// 关闭窗口
								$('#dialog').dialog("close");
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