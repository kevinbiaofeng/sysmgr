<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑域名</title>
</head>
<body>
	<div style="padding: 10px;">
		<form id="form" method="post">
			<input name="id" type="hidden">
	    	<table>
	    		<tr height="30px">
	    			<td>域    名：</td>
	    			<td><input class="easyui-textbox" type="text" name="domainUrl" data-options="required:true,validType:'length[5,30]'"></input></td>
	    		</tr>
	    		<tr height="30px">
	    			<td>类    型：</td>
	    			<td>
	    				<input type="radio" class="easyui-validatebox" name="type" value="1"><span>推广</span></input>
	    				<input type="radio" class="easyui-validatebox" name="type" value="2"><span>API</span></input>
	    				<input type="radio" class="easyui-validatebox" name="type" value="3"><span>主域名</span></input>
	    			</td>
	    		</tr>
	    		<tr height="40px">
		    		<td>
						状态：
					</td>
					<td>
						<input id="status" name="status" class="easyui-combobox" editable="false" data-options="valueField:'id',textField:'text',data:[{id: 2,text: '草稿'},{id: 0,text: '启用'},{id: 1,text: '删除'}]" />
					</td>
				</tr>
	    		<tr>
	    			<td>备    注：</td>
	    			<td>
	    				<input class="easyui-textbox" name="remark" id="remark" data-options="multiline:true" style="height:100px;width:200px"></input>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<button type="button" class="easyui-linkbutton" id="save" name="save">
				<span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
			</button>
			<button type="button" class="easyui-linkbutton" id="cancle" name="cancle">
				<span class="glyphicon glyphicon glyphicon-remove" aria-hidden="true">关闭</span>
			</button>
	    </div>
	</div>
    
	
	<script type="text/javascript">
		$(function(){
			var param = $.getOpenDialogParam("editPanle");
			$("#id").val(param.id);
			
			$.ajax({
		        url: "${pageContext.request.contextPath}/sys/merchant/domain/queryMerchantDomainById",
		        type: 'GET',
		        dataType: 'json',
		        data: {id: param.id},
		        success: function(resp){
		            if(resp.success){
		                //执行成功
		                $('#form').form("load",{
		                	domainUrl: resp.data.domainUrl,
		                	type: resp.data.type,
		                	remark: resp.data.remark,
		                	id: resp.data.id
		                });
		                $('#status').combobox('setValue', resp.data.status);
		            }else{
		                $.messager.alert('警告','未搜索到结果！');
		            }
		        }
		    });
		})
		
		$("#save").click(function(){
			$.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/sys/merchant/domain/save',
                data: $('#form').serialize(),
                async: false,                    
                success: function (data) {
					$.messager.alert('提示','新增成功！');
                     $('#editPanle').dialog("destroy");
                     $('#datagrid2').datagrid('load');
                },
				error: function (data) {
					$.messager.alert('警告', data.responseJSON.lastError);
				}
            })
		})
			
		$("#cancle").click(function(){
			$('#editPanle').dialog("destroy");
		})
	</script>
	
</body>
</html>