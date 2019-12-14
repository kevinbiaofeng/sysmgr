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
<title>日志记录</title>
<%@ include file="/static/pub/include.jsp"%>
</head>

<body>
	<div id="search-panel" class="container-fluid">
		<!-- 查询条件信息 -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="panel-title">查询条件</span>
						<a href="javascript:void(0);" class="pull-right panel-toggle">
							<i class="iconfont">&#xe603;</i>
						</a>
					</div>
					<div class="panel-body">
						<form id="search-form" method="post">
							<table class="form-table" border="0" cellpadding="1" cellspacing="2">
								<tr>
									<td class="form-label">
										<label for="account">用户账号：</label>
									</td>
									<td class="form-editor">
										<input id="account" name="account" class="easyui-textbox" />
									</td>

									<td class="form-label">
										<label for="createdDateStartStr">操作时间：</label>
									</td>
									<td class="form-editor">
										<input id="createdDateStartStr" name="createdDateStartStr" class="easyui-datetimebox" editable="false"/>
										<label for="createdDateEndStr">至：</label>
										<input id="createdDateEndStr" name="createdDateEndStr" class="easyui-datetimebox" editable="false"/>
									</td>

									<td class="button-group" colspan="2">
										<button type="button" class="btn btn-primary" id="search" name="search">
											<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
										</button>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- datagrid 信息 -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel-body" >

					<!-- 操作按钮区域 -->
					<div id="tb" style="display:none" class="opt-button-float">
						<div class="group-button opt-button-float">

						</div>
					</div>

					<table id="datagrid1" class="easyui-datagrid" title="日志记录"

						   data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							fit:false,
							method: 'GET',
							toolbar: '#tb',
							idField: 'id',
							rownumbers: true,
							pagination: true,
							autoRowHeight: false,
							pageSize: 10,
							pageNumber: 1,
							pageList: [10,20,30,40,50,100,200,500,1000]
							">

						<thead>
						<tr>
							<th data-options="field:'id',align:'center',fixed:false,hidden:true">id</th>
							<th data-options="field:'createdDate',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">操作时间</th>
							<th data-options="field:'module',align:'center',fixed:false,width:30">操作模块</th>
							<th data-options="field:'account',align:'center',fixed:false,width:30">操作账号</th>
							<th data-options="field:'ip',align:'center',fixed:false,width:30">操作ip</th>
							<th data-options="field:'method',align:'center',fixed:false,width:30">请求方式</th>
							<th data-options="field:'params',align:'center',fixed:false,width:60">请求参数</th>
							<th data-options="field:'description',align:'center',fixed:false,width:100">描述</th>

						</tr>
						</thead>
					</table>

				</div>
			</div>
		</div>

	</div>
	
	
	<!-- 弹出页面 -->
	<div id="dialog" style="overflow-x: hidden" class="easyui-dialog" closed="true"></div>


	<script type="text/javascript">

		$(function($){

			//初始化日期
			$('#createdDateStartStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 00:00:00");
			$('#createdDateEndStr').datetimebox('setValue', $._dateFormatter(new Date()) + " 23:59:59");

			search();

			//查询
			$("#search").unbind();
			$("#search").click(function(){
				search();
			});

			//点击重置
			$("#reset").unbind();
			$("#reset").bind("click",function(){
				//$('#search-form').form('clear');
				//$('#isLocked').combobox('setValue','0');
				//$('#isDeleted').combobox('setValue','0');
				search();
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
			
		});

		//查询
		function search(){
			var obj = $("#search-form").serializeObject();
			$('#datagrid1').datagrid({
				url: '${pageContext.request.contextPath}/back/log/querySysLogWithPage',
				queryParams : {
					createdDateEndStr: obj.createdDateEndStr,
					createdDateStartStr: obj.createdDateStartStr,
					account: obj.account
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

	</script>
</body>
</html>

