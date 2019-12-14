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
	<title>测试记录</title>
	<%@ include file="/static/pub/include.jsp"%>
</head>
<body>
	<h1>DataGrid - DetailView</h1>
	
	<table id="tt"></table>

	<table style="width: 100%;border: #00ee00 solid 1px;text-align:center;" align="center" valign="center">
		<tr>
			<td>昵称</td>
			<td>是否机器人</td>
			<td>椅子编号</td>
			<td>输赢积分</td>
			<td>服务费</td>
			<td>游戏时长(秒)</td>
			<td>插入时间</td>
		</tr>
		<tr>
			<td>88</td>
			<td>是</td>
			<td>0</td>
			<td>800</td>
			<td>40</td>
			<td>23</td>
			<td>2019-07-12 12:23:38</td>
		</tr>
	</table>

	<script type="text/javascript">

		var tableStr = "<table style='width: 100%;text-align:center;'><tr><td>昵称</td><td>是否机器人</td><td>椅子编号</td><td>输赢积分</td><td>服务费</td><td>游戏时长(秒)</td><td>插入时间</td></tr><tr><td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td></tr></table>";

		$(function(){
			$('#tt').datagrid({
				title:'DataGrid - DetailView',
				width:1000,
				height:500,
				remoteSort:false,
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				url: '${pageContext.request.contextPath}/static/data/test.json',
				method: 'GET',
				columns:[[
					{field:'itemid',title:'Item ID',width:80},
					{field:'productid',title:'Product ID',width:100,sortable:true},
					{field:'listprice',title:'List Price',width:80,align:'right',sortable:true},
					{field:'unitcost',title:'Unit Cost',width:80,align:'right',sortable:true},
					{field:'attr1',title:'Attribute',width:150,sortable:true},
					{field:'status',title:'Status',width:60,align:'center'}
				]],
				view: detailview,
				detailFormatter: function(rowIndex, rowData){
					/*return '<table><tr>' +
							'<td rowspan=2 style="border:0"><img src="images/' + rowData.itemid + '.png" style="height:50px;"></td>' +
							'<td style="border:0">' +
							'<p>Attribute: ' + rowData.attr1 + '</p>' +
							'<p>Status: ' + rowData.status + '</p>' +
							'</td>' +
							'</tr></table>';*/
					return tableStr;
				}
			});
		});
	</script>

</body>
</html>