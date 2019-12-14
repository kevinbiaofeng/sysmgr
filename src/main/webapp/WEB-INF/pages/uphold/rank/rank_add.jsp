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
		             	<span class="heading-font1">新增排行配置</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskadd-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="typeid">排行榜类型：</label>
									</td>
									<td class="form-editor">
										<input id="typeid" name="typeid" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',required: true,
													   	data:[
													   		{id: 2,text: '胜局排行榜'}
													   	]" value="2"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="ranktype">排行榜排名：</label>
									</td>
									<td class="form-editor">
										<input id="ranktype" name="ranktype" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',required: true,
													   	data:[
													   		{id: 1,text: '第一名'},{id: 2,text: '第二名'},{id: 3,text: '第三名'},
													   		{id: 4,text: '第4-10名'},{id: 5,text: '第11-20名'},{id: 6,text: '第21-50名'},
													   		{id: 7,text: '第51-100名'}
													   	]" value="1"/>
									</td>
								</tr>
								<%--<tr>
									<td class="form-label">
										<label for="diamond">奖励钻石数：</label>
									</td>
									<td class="form-editor">
										<input id="diamond" name="diamond" class="easyui-numberbox" data-options="required: true" value="0"/>
									</td>
								</tr>--%>
								<tr>
									<td class="form-label">
										<label for="gold">奖励金币数：</label>
									</td>
									<td class="form-editor">
										<input id="gold" name="gold" class="easyui-numberbox" data-options="required: true" value="0"/>
									</td>
								</tr>
								<%--<tr>
									<td class="form-label">
										<label for="awardticket">奖励奖券数：</label>
									</td>
									<td class="form-editor">
										<input id="awardticket" name="awardticket" class="easyui-numberbox" data-options="required: true" value="0"/>
									</td>
								</tr>--%>
		            		</table>
							<font style="color: red">注：奖励数值填写0时，客户端将不显示该奖励</font>
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

			//构建参数传入controller
			var data = {
				//awardticket: obj.awardticket,
				//diamond: obj.diamond,
				awardticket: 0,
				diamond: 0,
				gold: obj.gold,
				ranktype: obj.ranktype,
				typeid: obj.typeid
			};

			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/uphold/rank/saveRank",
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