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
		             	<span class="heading-font1">编辑站点配置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" enctype="multipart/form-data" data-options="novalidate:true" >
							<input id="configid" name="configid" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="configkey">键名：</label>
									</td>
									<td class="form-editor">
										<input id="configkey" name="configkey" class="easyui-textbox" style="width: 350px;" data-options="required:true,disabled: true"/>
									</td>
									<td class="form-label">
										<label for="configname">名称：</label>
									</td>
									<td class="form-editor">
										<input id="configname" name="configname" class="easyui-textbox" style="width: 350px;" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="configstring">字段说明：</label>
									</td>
									<td colspan="3" class="form-editor">
										<input id="configstring" name="configstring" class="easyui-textbox" style="width: 840px;height: 80px;"
											   data-options="required: true,multiline:true" disabled="false"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="sortid">排序：</label>
									</td>
									<td class="form-editor">
										<input id="sortid" name="sortid" class="easyui-numberbox" style="width: 350px;" data-options="required: true" />
									</td>
									<td class="form-label">
										<label for="field1">字段1：</label>
									</td>
									<td class="form-editor">
										<input id="field1" name="field1" class="easyui-textbox" style="width: 350px;" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="field2">字段2：</label>
									</td>
									<td class="form-editor">
										<input id="field2" name="field2" class="easyui-textbox" style="width: 350px;" />
									</td>
									<td class="form-label">
										<label for="field3">字段3：</label>
									</td>
									<td class="form-editor">
										<input id="field3" name="field3" class="easyui-textbox" style="width: 350px;" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="field4">字段4：</label>
									</td>
									<td class="form-editor">
										<input id="field4" name="field4" class="easyui-textbox" style="width: 350px;" />
									</td>
									<td class="form-label">
										<label for="field5">字段5：</label>
									</td>
									<td class="form-editor">
										<input id="field5" name="field5" class="easyui-textbox" style="width: 350px;" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="field6">字段6：</label>
									</td>
									<td class="form-editor">
										<input id="field6" name="field6" class="easyui-textbox" style="width: 350px;" />
									</td>
									<td class="form-label">
										<label for="field7">字段7：</label>
									</td>
									<td class="form-editor">
										<input id="field7" name="field7" class="easyui-textbox" style="width: 350px;" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="field8">字段8：</label>
									</td>
									<td class="form-editor">
										<input id="field8" name="field8" class="easyui-textbox" style="width: 350px;" />
									</td>
									<td class="form-label">
										<label for="field9">字段9：</label>
									</td>
									<td class="form-editor">
										<input id="field9" name="field9" class="easyui-textbox" style="width: 350px;" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="field10">字段10：</label>
									</td>
									<td class="form-editor">
										<input id="field10" name="field10" class="easyui-textbox" style="width: 350px;" />
									</td>
									<td class="form-label">
										<label for="field11">字段11：</label>
									</td>
									<td class="form-editor">
										<input id="field11" name="field11" class="easyui-textbox" style="width: 350px;" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="field12">字段12：</label>
									</td>
									<td class="form-editor">
										<input id="field12" name="field12" class="easyui-textbox" style="width: 350px;" />
									</td>
									<td class="form-label">
										<label for="field13">字段13：</label>
									</td>
									<td class="form-editor">
										<input id="field13" name="field13" class="easyui-textbox" style="width: 350px;" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="uploadUrl">二维码图片：</label>
									</td>

									<td id="td1" class="form-editor">
										<input id="uploadUrl" name="uploadUrl" class="easyui-textbox" />
										<a id="showImg" target="_blank">查看</a> | <a id="deleteImg" href="javascript:;">删除</a>
									</td>

									<!-- data-options="label:'产品图片:',buttonText:'浏览',prompt:'仅支持jpg、gif、png等格式的图片',required:true"> -->
									<td id="td2" class="form-editor" style="display: none">
										<input id="image" name="image" class="easyui-filebox"
											   data-options="buttonText: '选择图片',accept: 'image/gif,image/jpeg,image/png'"/>
										<font style="color: red">[大小：不大于1M] </font>
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
			url: "${pageContext.request.contextPath}/website/stand/queryConfigInfoById",
			type: 'GET',
			dataType: 'json',
			data: {configid: param.configid},
			success: function(resp){
				// console.log(resp);
				if(resp.success){
					//执行成功
					$('#taskedit-form').form("load",resp.data);

					$("#showImg").attr("href","${pageContext.request.contextPath}/static" + resp.data.uploadUrl);

				}else{
					$.messager.alert('警告','根据id查询失败');
				}
			}
		});

		//删除
		$("#deleteImg").unbind();
		$("#deleteImg").bind("click",function(){
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
			//console.log(obj);

			//return;

			//let serverversion = obj.serverversion;
			//let f1 = reg(serverversion);
			//console.log(f1);


			//构建参数传入controller
			/*var data = {
				clientexename: obj.clientexename,
				clientversionDesc: obj.clientversion,
				databaseaddr: obj.databaseaddr,
				databasename: obj.databasename,
				gameid: obj.gameid,
				gamename: obj.gamename,
				serverdllname: obj.serverdllname,
				serverversionDesc: obj.serverversion
			};*/
			//var data = obj;


			$('#taskedit-form').form('submit', {
				url: "${pageContext.request.contextPath}/website/stand/saveConfigInfo",
				onSubmit: function(){
					// do some check
					// return false to prevent submit;
				},
				success:function(resp){
					//console.log(resp);
					var o = JSON.parse(resp);
					if(o.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'编辑站点配置成功',
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


			//console.log(data);
			//提交到后台
			/*$.ajax({
				url: "/website/stand/updateConfigInfo",
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
            });*/
			
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