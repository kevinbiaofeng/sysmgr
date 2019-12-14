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
					<%--<div class="panel-heading" >
		             	<span class="heading-font1">编辑充值配置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>--%>
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" data-options="novalidate:true" >
							<input id="configid" name="configid" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="paytype1">充值类型：</label>
									</td>
									<td class="form-editor">
										<input id="paytype1" name="paytype" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[{id: 0,text: '普通充值'},{id: 1,text: '苹果内购'}],disabled: true" value="0" />
									</td>
									<td class="form-label">
										<label for="payidentity">首充类别：</label>
									</td>
									<td class="form-editor">
										<input id="payidentity" name="payidentity" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[{id: 0,text: '普通'},{id: 1,text: '首充'},{id: 2,text: '每日'},{id: 3,text: '热卖'},{id: 4,text: '人气'}]" value="0" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="payname">产品名称：</label>
									</td>
									<td class="form-editor">
										<input id="payname" name="payname" class="easyui-textbox" data-options="required:true"/>
									</td>
									<td class="form-label">
										<label for="payprice">产品价格：</label>
									</td>
									<td class="form-editor">
										<input id="payprice" name="payprice" class="easyui-numberbox" data-options="required: true,precision:2"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="scoretype">产品类别：</label>
									</td>
									<td class="form-editor">
										<input id="scoretype" name="scoretype" class="easyui-combobox" data-options="valueField:'id',textField:'text',disabled: true,
											data:[{id: 0,text: '游戏币'},{id: 1,text: '钻石'}]" value="0" />
									</td>
									<td class="form-label">
										<label for="score">赠送数值：</label>
									</td>
									<td class="form-editor">
										<input id="scoreText" name="scoreText" class="easyui-numberbox" data-options="required: true,precision:2" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="sortid">排序标识：</label>
									</td>
									<td class="form-editor">
										<input id="sortid" name="sortid" class="easyui-numberbox" data-options="required: true" />
									</td>
									<td class="form-label">
										<label for="imagetype">图标类型：</label>
									</td>
									<td class="form-editor">
										<input id="imagetype" name="imagetype" class="easyui-combobox"
											   data-options="
											   		valueField:'id',
											   		textField:'text',
													data:[
														{id: 1,text: '微量金币图标'},{id: 2,text: '较少金币图标'},
														{id: 3,text: '少量金币图标'},{id: 4,text: '中等金币图标'},
														{id: 5,text: '偏高金币图标'},{id: 6,text: '多数金币图标'},
														{id: 7,text: '大量金币图标'},{id: 8,text: '超级金币图标'}
													], panelHeight:'auto'" value="1" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="fristpresent">首冲额外赠送：</label>
									</td>
									<td class="form-editor">
										<input id="fristpresent" name="fristpresentText" class="easyui-numberbox" data-options="required: true,precision:2" value="0"/>
									</td>
									<td id="appid1" class="form-label" style="display: none">
										<label for="appleid">苹果内购标识：</label>
									</td>
									<td id="appid2" class="form-editor" style="display: none">
										<input id="appleid" name="appleid" class="easyui-textbox" data-options="required:false"/>
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
			url: "${pageContext.request.contextPath}/invest/setup/queryAppConfigById",
			type: 'GET',
			dataType: 'json',
			data: {configid: param.configid},
			success: function(resp){
				//console.log(resp);
				if(resp.success){
					//执行成功
					$('#taskedit-form').form("load",{
						configid: resp.data.configid,
						appleid: resp.data.appleid,
						fristpresentText: resp.data.fristpresentText,
						imagetype: resp.data.imagetype,
						payidentity: resp.data.payidentity,
						payname: resp.data.payname,
						payprice: resp.data.payprice,
						paytype: resp.data.paytype,
						scoreText: resp.data.score,
						scoretype: resp.data.scoretype,
						sortid: resp.data.sortid

					});

				}else{
					$.messager.alert('警告','根据id查询注册赠送失败');
				}
			}
		});

		$("#paytype1").combobox({
			onSelect : function(value){
				//console.log(value);
				if(value.id == 1){
					$("#appid1").show();
					$("#appid2").show();
				}else{
					$("#appid1").hide();
					$("#appid2").hide();
				}
			}
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

			var paytype = $('#paytype1').combobox('getValue');
			//console.log(paytype);

			if(paytype == '1'){
				if(appleid == '' || obj.appleid == null){
					$.messager.alert('警告','苹果产品标识不能为空');
					return;
				}
			}

			//构建参数传入controller
			var data = {
				configid: obj.configid,
				appleid: obj.appleid,
				fristpresentText: obj.fristpresentText,
				imagetype: obj.imagetype,
				payidentity: obj.payidentity,
				payname: obj.payname,
				payprice: obj.payprice,
				paytype: paytype,
				scoreText: obj.scoreText,
				scoretype: obj.scoretype,
				sortid: obj.sortid
			};
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/invest/setup/updateAppConfig",
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

						//清除之前勾选的行
						$('#datagrid1').datagrid('clearChecked');
                		
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