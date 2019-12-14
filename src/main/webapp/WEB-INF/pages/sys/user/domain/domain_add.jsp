<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增域名</title>
</head>
<body>
	<div style="padding: 10px;">
		<form id="form" method="post">
			<input name="userId" id="userId" type="hidden" >
	    	<table cellpadding="5">
	    		<tr height="30px">
	    			<td>域    名：</td>
	    			<td><input class="easyui-textbox" type="text" name="domainUrl" data-options="required:true,validType:'length[5,30]'"></input></td>
	    		</tr>
	    		<tr height="30px">
	    			<td>类    型：</td>
	    			<td>
	    				<input type="radio" class="easyui-validatebox" name="type" value="1" checked="checked"><span>推广</span></input>
	    				<input type="radio" class="easyui-validatebox" name="type" value="2"><span>API</span></input>
	    				<input type="radio" class="easyui-validatebox" name="type" value="3"><span>主域名</span></input>
	    			</td>
	    		</tr>
	    		<tr height="30px">
		    		<td>
						状态：
					</td>
					<td>
						<input id="status" name="status" class="easyui-combobox" editable="false" data-options="valueField:'id',textField:'text',data:[{id: 2,text: '草稿', selected:true},{id: 0,text: '启用'}]" />
					</td>
				</tr>
	    		<tr>
	    			<td>备    注：</td>
	    			<td>
	    				<input class="easyui-textbox" name="remark" data-options="multiline:true" style="height:100px;width:200px"></input>
	    			</td>
	    		</tr>
	    	</table>
	    	<div style="text-align:center;padding:5px;">
		    	<button type="button" class="easyui-linkbutton" id="save" name="save">
					<span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
				</button>
				<button type="button" class="easyui-linkbutton" id="cancle" name="cancle">
					<span class="glyphicon glyphicon glyphicon-remove" aria-hidden="true">关闭</span>
				</button>
		    </div>
	    </form>
	</div>
	
    
	
	<script type="text/javascript">
		$(function(){
			var param = $.getOpenDialogParam("addPanle");
			$("#userId").val(param.id);
		})
		
		$("#save").click(function(){
			// 启用验证
	        var isValid = $('#form').form('validate');
	        if(!isValid){
	            $.messager.alert('警告','请先完善必输项');
	            return;
	        }
			$.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/sys/merchant/domain/save',
                data: $('#form').serialize(),
                async: false,
                success: function (data) {
					$.messager.alert('提示','新增成功！');
                     $('#addPanle').dialog("destroy");
                     $('#datagrid2').datagrid('load');
                },
				error: function (data) {
					$.messager.alert('警告', data.responseJSON.lastError);
				}
            })
		})
			
		$("#cancle").click(function(){
			$('#addPanle').dialog("destroy");
		})
	</script>
</body>
</html>