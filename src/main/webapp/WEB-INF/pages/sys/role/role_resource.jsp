<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分配角色资源</title>
</head>
<body>

	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" >
		             	<span class="heading-font1">资源</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
						<%--url:'${pageContext.request.contextPath}/static/data/aa.json',--%>
						<ul id="roleResource">
							<span>加载中...</span>
						</ul>

							<%--<ul id="roleResource" class="easyui-tree"
								data-options="
								url:'${pageContext.request.contextPath}/sys/role/querySysRoleResource',
								method:'get',
								animate:true,
								checkbox:true">

							</ul>--%>


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

		$('#roleResource').tree({
			url:'${pageContext.request.contextPath}/sys/role/querySysRoleResource?id=' + param.id,
			method:'get',
			animate:true,
			checkbox:true
		});


		//保存
		$("#save").unbind();
		$("#save").bind("click",function(){

			var nodes = $('#roleResource').tree('getChecked');

			var nmArr = [];
			$.each(nodes,function(index,o){
				nmArr.push(o.id);
			});

			var nodes1 = $('#roleResource').tree('getChecked', ['indeterminate']);

			$.each(nodes1,function(index,o){
				nmArr.push(o.id);
			});

			s = nmArr.join(",");

			var data = {
				roleId: param.id,
				resourceIds: s
			};

			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/sys/role/saveSysRoleResource",
                type: 'POST',
                dataType: 'json',
                data: data,
                success: function(resp){
                	if(resp.success){
						//提示操作成功
						$.messager.alert({
							title:'提示',
							msg:'角色关联资源成功',
							icon: 'info',
							fn:function(){
								// 关闭窗口
								$('#dialog').dialog("close");
							}
						});

                	}else{
                		$.messager.alert('警告','修改角色失败');
                	}
                }
            });

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