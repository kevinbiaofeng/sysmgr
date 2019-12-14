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
		             	<span class="heading-font1">编辑系统消息</span>
		                <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
		            </div>
		            <div class="panel-body">
		            	<form id="taskedit-form" method="post" data-options="novalidate:true" >
							<input id="id" name="id" type="hidden" class="easyui-textbox" />
		            		<table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
								<tr>
									<td class="form-label">
										<label for="messagestring">消息内容：</label>
									</td>
									<td class="form-editor">
										<input id="messagestring" name="messagestring" class="easyui-textbox"
											   style="width: 80%;height:50px" data-options="required: true,multiline:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="serverrange">消息范围：</label>
									</td>
									<td class="form-editor">
										<%--<input id="serverrange" name="serverrange" class="easyui-tree"
											   data-options="required: true,url:${pageContext.request.contextPath}/static/data/tree_data1.json"/>--%>
											<ul id="serverrange" class="easyui-tree">
											</ul>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="messagetype">消息类型：</label>
									</td>
									<td class="form-editor">
										<input id="messagetype" name="messagetype" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',data:[{id: 1,text: '游戏'},{id: 2,text: '房间'},{id: 3,text: '全部'}],required: true"
												value="3" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="starttime">开始时间：</label>
									</td>
									<td class="form-editor">
										<input id="starttime" name="starttime" editable="false" class="easyui-datetimebox" data-options="required: true" />
										<input id="starttimeStr" name="starttimeStr" class="easyui-textbox" type="hidden" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="concludetime">结束时间：</label>
									</td>
									<td class="form-editor">
										<input id="concludetime" name="concludetime" editable="false" class="easyui-datetimebox" data-options="required: true" />
										<input id="concludetimeStr" name="concludetimeStr" type="hidden" class="easyui-textbox" data-options="required: true" />
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="timerate">消息频率(秒)：</label>
									</td>
									<td class="form-editor">
										<input id="timerate" name="timerate" class="easyui-numberbox" data-options="required: true" value="60"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="collectnote">消息备注：</label>
									</td>
									<td class="form-editor">
										<input id="collectnote" name="collectnote" class="easyui-textbox"
											   style="width: 80%;height:50px" data-options="required: true,multiline:true"/>
									</td>
								</tr>
								<tr>
									<td class="form-label">
										<label for="nullity">状态：</label>
									</td>
									<td class="form-editor">
										<input id="nullity" name="nullity" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]" value="0"/>
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
		console.log(param);

		//url:'${pageContext.request.contextPath}/static/data/tree_data1.json',
		$('#serverrange').tree({
			url:'${pageContext.request.contextPath}/uphold/sysmsg/loadGameIndoData?id=' + param.id,
			method:'GET',
			animate:true,
			checkbox:true
		});

		//ajax 加载form表单数据
		$.ajax({
			url: "${pageContext.request.contextPath}/uphold/sysmsg/querySysmsgById",
			type: 'GET',
			dataType: 'json',
			data: {id: param.id},
			success: function(resp){
				//console.log(resp);
				if(resp.success){
					//执行成功
					$('#taskedit-form').form("load",{

						id : resp.data.id,
						messagestring: resp.data.messagestring,
						messagetype: resp.data.messagetype,
						starttimeStr: resp.data.starttimeStr,
						concludetimeStr: resp.data.concludetimeStr,
						timerate: resp.data.timerate,
						collectnote: resp.data.collectnote,
						nullity: resp.data.nullity

					});

					/*
					starttime: resp.data.starttime,
					concludetime: resp.data.concludetime,
					 */
					$('#starttime').datetimebox('setValue',$.dateFormatter(resp.data.starttime));
					$('#concludetime').datetimebox('setValue',$.dateFormatter(resp.data.concludetime));
				}else{
					$.messager.alert('警告','根据id查询失败');
				}
			}
		});


		//设置日期
		$('#starttime').datetimebox({
			stopFirstChangeEvent: true,
			onChange: function(){
				var date = $(this).datetimebox('getValue');
				var v = $.dateFormatterYmd(date);
				$('#starttimeStr').textbox('setValue', v);
				//var v = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate();
				//var v = $._dateFormatterYmd(date);
				//$('#starttimeStr').textbox('setValue', v);
			}
		});

		$('#concludetime').datetimebox({
			stopFirstChangeEvent: true,
			onChange: function(){
				var date = $(this).datetimebox('getValue');
				var v = $.dateFormatterYmd(date);
				$('#concludetimeStr').textbox('setValue', v);
				//console.log(date);
				//var v = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate();
				//var v = $._dateFormatterYmd(date);
				//$('#concludetimeStr').textbox('setValue', v);
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

			var nodes = $('#serverrange').tree('getChecked');
			//console.log(nodes);

			if(nodes.length <= 0){
				$.messager.alert('警告','请先选择房间');
				return;
			}

			var nmArr = [];
			$.each(nodes,function(index,o){
				//console.log(index);
				//console.log(obj.id);
				nmArr.push(o.id);
			});
			var serverrange = nmArr.join(",");

			//构建参数传入controller
			var data = {
				id : obj.id,
				messagestring: obj.messagestring,
				messagetype: obj.messagetype,
				starttimeStr: obj.starttimeStr,
				concludetimeStr: obj.concludetimeStr,
				timerate: obj.timerate,
				collectnote: obj.collectnote,
				serverrange: serverrange,
				nullity: obj.nullity
			};

			//console.log(data);
			//提交到后台
			$.ajax({
				url: "${pageContext.request.contextPath}/uphold/sysmsg/updateSysmsg",
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