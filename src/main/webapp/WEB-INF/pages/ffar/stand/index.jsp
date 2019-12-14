<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>系统设置</title>
<%@ include file="/static/pub/include.jsp"%>
</head>

<body>

	<div id="search-panel" class="container-fluid">

		<div class="btn-group" role="group">
			<c:forEach items="${list}" var="ss">
				<button type="button" class="btn btn-default" onclick="loadIMGroupOptionInfo('${ss.optionname}')">${ss.optiontip}</button>
			</c:forEach>

			<hr style="color: #00ee00;height: 10px;"/>

			<div>

				<form id="taskedit-form" method="post" data-options="novalidate:true" >
					<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
						<tr>
							<td class="form-label">
								<label for="optionname">键名：</label>
							</td>
							<td class="form-editor">
								<input id="optionname" name="optionname" class="easyui-textbox" style="width: 80%;" data-options="required: true,disabled: true"/>
							</td>
						</tr>
						<tr>
							<td class="form-label">
								<label for="optionvalue">键值：</label>
							</td>
							<td class="form-editor">
								<input id="optionvalue" name="optionvalue" class="easyui-numberbox" style="width: 80%;" data-options="required: true"/>
							</td>
						</tr>
						<tr>
							<td class="form-label">
								<label for="optiontip">名称：</label>
							</td>
							<td class="form-editor">
								<input id="optiontip" name="optiontip" class="easyui-textbox" style="width: 80%;" data-options="required: true"/>
							</td>
						</tr>
						<tr>
							<td class="form-label">
								<label for="optiondescribe">描述：</label>
							</td>
							<td class="form-editor">
								<input id="optiondescribe" name="optiondescribe" style="width: 80%;height:50px" class="easyui-textbox"
									   data-options="required: true,multiline:true" disabled="false"/>
							</td>
						</tr>
					</table>
				</form>

				<div class="opt-button-float">
					<button type="button" class="btn btn-primary" id="save" name="save">
						<span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
					</button>
					<%--<button type="button" class="btn btn-default" id="cancel" name="cancel">
						<span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
					</button>--%>
				</div>
			</div>


			<%--<button type="button" class="btn btn-default" onclick="loadSysStatusInfo('StatusName')">注册服务0</button>
			<button type="button" class="btn btn-default">注册服务1</button>--%>


		</div>
		

		
	</div>
	
	
	<!-- 弹出页面 -->
	<div id="dialog"  class="easyui-dialog" closed="true"></div>


	<script type="text/javascript">

		//var abc = "<shiro:principal property='name'/>";
		//console.log("abc==========>" + abc);

		$(function($){
			
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

			var optionname = $('#optionname').textbox('getValue');
			//console.log(nodes);

			if(optionname == null || optionname == ''){
				$.messager.alert('警告','请先选择亲友圈配置');
				return;
			}


			if(optionname == "MaxMemberCount"){
				if(obj.optionvalue <= 0){
					$.messager.alert('警告','群组人数必须大于0');
					return;
				}
			}

			//构建参数传入controller
			var data = {
				optionname : optionname,
				optiontip: obj.optiontip,
				optiondescribe: obj.optiondescribe,
				optionvalue: obj.optionvalue
			};

			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/ffar/stand/updateIMGroupOption",
				type: 'PUT',
				dataType: 'json',
				data: data,
				success: function(resp){
					//{success: true, errors: Array(0), data: null, lastError: null}
					//console.log(resp);
					if(resp.success){
						//执行成功
						// 关闭窗口
						//$('#dialog').dialog("close");

						// 刷新列表
						//console.log ( $('#datagrid1') );
						//$('#datagrid1').datagrid('load');

						$.messager.alert('提示',"修改亲友圈配置成功");

					}else{
						$.messager.alert('警告',resp.lastError);
					}
				}
			});

		});

		//加载
		function loadIMGroupOptionInfo(optionname){
			//console.log(statusname);
			//ajax 加载form表单数据
			$.ajax({
				url: "${pageContext.request.contextPath}/ffar/stand/queryIMGroupOptionInfoByName",
				type: 'GET',
				dataType: 'json',
				data: {optionname: optionname},
				success: function(resp){
					//console.log(resp);
					if(resp.success){
						//执行成功
						$('#taskedit-form').form("load",resp.data);
					}else{
						$.messager.alert('警告','根据id查询失败');
					}
				}
			});
		}


	</script>
</body>
</html>

