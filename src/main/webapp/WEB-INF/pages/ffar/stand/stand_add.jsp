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
		             	<span class="heading-font1">新增亲友圈配置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskadd-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="optionname">键名：</label>
									</td>
									<td class="form-editor">
										<input id="optionname" name="optionname" class="easyui-textbox" style="width: 350px;" data-options="required:true"/>
									</td>
									<td class="form-label">
										<label for="optionvalue">键值：</label>
									</td>
									<td class="form-editor">
										<input id="optionvalue" name="optionvalue" class="easyui-numberbox" style="width: 350px;" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="optiontip">名称：</label>
									</td>
									<td class="form-editor">
										<input id="optiontip" name="optiontip" class="easyui-textbox" style="width: 350px;" data-options="required:true"/>
									</td>
									<td class="form-label">
										<label for="sortid">排序：</label>
									</td>
									<td class="form-editor">
										<input id="sortid" name="sortid" class="easyui-numberbox" style="width: 350px;" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="optiondescribe">描述：</label>
									</td>
									<td colspan="3" class="form-editor">
										<input id="optiondescribe" name="optiondescribe" class="easyui-textbox" style="width: 840px;height: 80px;"
											   data-options="required: true,multiline:true" />
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
			var data = obj;
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/ffar/stand/saveIMGroupOption",
                type: 'POST',
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