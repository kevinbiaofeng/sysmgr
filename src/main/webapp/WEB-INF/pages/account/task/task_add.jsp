<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增注册赠送</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" >
		             	<span class="heading-font1">新增任务信息</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskadd-form" method="post" data-options="novalidate:true" >
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="kindid">所在游戏：</label>
									</td>
									<td class="form-editor">
										<input id="kindid" name="kindid" class="easyui-combobox"
											   data-options="
											   valueField:'id',
											   textField:'text',
											   url:'${pageContext.request.contextPath}/account/task/loadGameKindItemComboData?nullity=0',
											   method: 'GET'" value="0" />
									</td>
									<td class="form-label">
										<label for="serverid">所在房间：</label>
									</td>
									<td class="form-editor">
										<input id="serverid" name="serverid" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[{id: 0,text: '不限房间'}],method: 'GET'" value="0" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="taskname">任务名称：</label>
									</td>
									<td class="form-editor">
										<input id="taskname" name="taskname" class="easyui-textbox" data-options="required:true"/>
									</td>
									<td class="form-label">
										<label for="tasktype">任务类型：</label>
									</td>
									<td class="form-editor">
										<input id="tasktype" name="tasktype" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',
										data:[{id: 0,text: '其他任务'},{id: 1,text: '总局任务'},{id: 2,text: '胜局任务'},{id: 4,text: '首胜任务'},{id: 8,text: '连胜任务'}]" value="0" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="usertype">用户类型：</label>
									</td>
									<td class="form-editor">
										<input id="usertype" name="usertype" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[{id: 0,text: '所有用户'},{id: 1,text: '普通用户'},{id: 2,text: '会员用户'}]" value="0" />
									</td>
									<td class="form-label">
										<label for="timetype">时间类型：</label>
									</td>
									<td class="form-editor">
										<input id="timetype" name="timetype" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[{id: 1,text: '一次任务'},{id: 2,text: '每日任务'},{id: 4,text: '每周任务'},{id: 8,text: '每月任务'}]" value="1" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="nullity">冻结状态：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[{id: 0,text: '正常'},{id: 1,text: '冻结'}]" value="0" />
									</td>
									<td class="form-label">
										<label for="innings">完成目标数：</label>
									</td>
									<td class="form-editor">
										<input id="innings" name="innings" class="easyui-numberbox" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="gold">奖励金币：</label>
									</td>
									<td class="form-editor">
										<input id="gold" name="gold" class="easyui-numberbox" data-options="required: true" value="0"/>
									</td>
									<td class="form-label">
										<label for="diamond">奖励钻石：</label>
									</td>
									<td class="form-editor">
										<input id="diamond" name="diamond" class="easyui-numberbox" data-options="required: true" value="0"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="medal">奖励奖券：</label>
									</td>
									<td class="form-editor">
										<input id="medal" name="medal" class="easyui-numberbox" data-options="required: true" value="0"/>
									</td>
									<td class="form-label">
										<label for="taskdescription">任务描述：</label>
									</td>
									<td class="form-editor">
										<input id="taskdescription" name="taskdescription" class="easyui-textbox" data-options="required: true" />
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

		$("#kindid").combobox({
			onSelect : function(value){
				//console.log(value);
				var url = "${pageContext.request.contextPath}/account/task/loadGameRoomInfoComboData?kindid=" + value.id;
				$("#serverid").combobox('reload',url);
				//ajax 加载form表单数据
				/*$.ajax({
					url: "${pageContext.request.contextPath}/account/task/loadGameRoomInfoComboData",
					type: 'GET',
					dataType: 'json',
					async : false,
					data: {kindid : value.id},
					success: function(resp){
						//console.log(resp);
						$("#serverid").setData(resp);
					}
				});*/
			}
		});
	 
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
				diamond: obj.diamond,
				gold: obj.gold,
				innings: obj.innings,
				kindid: obj.kindid,
				medal: obj.medal,
				nullity: obj.nullity,
				serverid: obj.serverid,
				taskdescription: obj.taskdescription,
				taskname: obj.taskname,
				tasktype: obj.tasktype,
				timetype: obj.timetype,
				usertype: obj.usertype
			};
			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/account/task/saveTask",
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