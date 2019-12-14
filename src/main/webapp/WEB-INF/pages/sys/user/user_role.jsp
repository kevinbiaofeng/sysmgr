<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员角色</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
		            <div class="panel-body">
						<div class="easyui-panel">
							<div id="dl" class="easyui-datalist" style="width:100%;"
								 data-options="
									url: '${pageContext.request.contextPath}/sys/role/queryRoleDataList',
									queryParams: {isDeleted : 0},
									method: 'get',
									checkbox: true,
									selectOnCheck: true,
									singleSelect: false,
									valueField: 'id',
									textField: 'name'
									">

							</div>
						</div>
						<div style="height: 30px;min-height: 30px;padding: 5px;">
							<font color="blue"><b>当前用户所属角色:</b></font> <span id="currRole"></span>
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
		var param = $.getOpenDialogParam("dialogRole");

		$(function($){
			//查询当前用户所属角色
			$.ajax({
				url: "${pageContext.request.contextPath}/sys/user/queryCurrUserRole",
				type: 'GET',
				dataType: 'json',
				data: {userId: String(param.id)},
				success: function(resp){
					console.log(resp);
					var arr = resp;

					var nmArr = [];
					$.each(arr,function(index,obj){
						nmArr.push(obj.name);
					});
					var s = nmArr.join(",");
					$("#currRole").text(s);
				}
			});
		});

		$("#save").unbind();
		$("#save").bind("click",function(){
			var arr = $('#dl').datalist('getSelections');
			console.log(arr);

			var roleIds = [];
			//循环遍历数组
			$.each(arr,function(index,obj){
				roleIds.push(obj.id);
			});

			var data = {
				userId: String(param.id),
				roleIds: roleIds.join(",")
			};

			$.messager.progress({
				title:'',
				msg:'处理中...',
				text:'处理中...'
			});

			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/sys/user/saveSysUserRole",
				type: 'POST',
				dataType: 'json',
				data: data,
				success: function(resp){
					$.messager.progress('close');
					if(resp.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'用户角色关联成功',
							icon: 'info',
							fn:function(){
								// 关闭窗口
								$('#dialogRole').dialog("destroy");
							}
						});

					}else{
						$.messager.alert('警告','保存用户角色失败');
					}
				}
			});

		});
		
		//取消
		$("#cancel").unbind();
		$("#cancel").bind("click",function(){
			$('#dialogRole').dialog("destroy");
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