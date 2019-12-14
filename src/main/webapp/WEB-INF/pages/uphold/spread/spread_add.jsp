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
		             	<span class="heading-font1">新增推广类型设置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskadd-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="gradeid">级别：</label>
									</td>
									<!--
									1 实习生 2 学员 3 会员 4 高级会员 5 代理 6 区域经理 7 总代理 8 经理特助 9 监事 10 总监 11 经理 12 合伙人 13 股东 14 董事长
									-->
									<td class="form-editor">
										<input id="gradeid" name="gradeid" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',required: true,
											   	data:[
											   	{id: 1,text: '实习生'},{id: 2,text: '学员'},{id: 3,text: '会员'},
											   	{id: 4,text: '高级会员'},{id: 5,text: '代理'},{id: 6,text: '区域经理'},
											   	{id: 7,text: '总代理'},{id: 8,text: '经理特助'},{id: 9,text: '监事'},
											   	{id: 10,text: '总监'},{id: 11,text: '经理'},{id: 12,text: '合伙人'},
											   	{id: 13,text: '股东'},{id: 14,text: '董事长'}
											   	]"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="teamkpi1">团队业绩：</label>
									</td>
									<td class="form-editor">
										<input id="teamkpi1" name="teamkpistart" class="easyui-numberbox" data-options="required: true"/>
										-
										<input id="teamkpi2" name="teamkpiend" class="easyui-numberbox" data-options="required: true"/>
										<font style="color: red">团队业绩直接输入数字:比如5万,输入50000</font>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="percentage">提成比例：</label>
									</td>
									<td class="form-editor">
										<input id="percentage" name="percentage" class="easyui-numberbox" data-options="required: true"/>
										<font style="color: red">提成比例:比如万分之60,就直接输入60</font>
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

			var gradename = $("#gradeid").combobox("getText");
           	//console.log(gradename);

			//构建参数传入controller
			var data = {
                gradeid: obj.gradeid,
                gradename: gradename,
                percentage: obj.percentage,
                teamkpiend: obj.teamkpiend,
                teamkpistart: obj.teamkpistart
			};

			//console.log(data);

			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/uphold/spread/saveSpreadType",
                type: 'POST',
                dataType: 'json',
                data: data,
                success: function(resp){
                	//{success: true, errors: Array(0), data: null, lastError: null}
                	//console.log(resp);
                	if(resp.success){

						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'新增推广设置成功',
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

                		//执行成功
                		// 关闭窗口 
                		//$('#dialog').dialog("close");
                		
                		// 刷新列表
                		//console.log ( $('#datagrid1') );
                		//$('#datagrid1').datagrid('load');
                		
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