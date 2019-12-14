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
<title>签到管理</title>
<%@ include file="/static/pub/include.jsp"%>
</head>

<body>

	<div id="search-panel" class="container-fluid">
		<div id="tt" class="easyui-tabs" style="width:100%;">
			<div title="签到礼包配置" style="padding:10px">

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
								<form id="search1-form" method="post">
									<table class="form-table" border="0" cellpadding="1" cellspacing="2">
										<tr>
											<td class="form-label">
												<label for="typeid">礼包类型：</label>
											</td>
											<td class="form-editor">
												<input id="typeid" name="typeid" class="easyui-combobox" editable="false"
													   data-options="panelHeight:'auto', valueField:'id',textField:'text',
													   	data:[{id: '',text: '全部', selected: true},{id: '0',text: '抽奖签到礼包'},{id: '1',text: '累计签到礼包'}]" />
											</td>

											<td class="button-group" colspan="2">
												<button type="button" class="btn btn-primary" id="search1" name="search1">
													<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
												</button>
												<button type="button" class="btn btn-warning" id="reset1" name="reset1">
													<span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
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
							<div id="tb1" style="display:none" class="opt-button-float">
								<div class="group-button opt-button-float">

									<button class="layui-btn layui-btn-sm layui-btn-normal" id="add1" name="add1">
										<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
									</button>
									<button class="layui-btn layui-btn-sm layui-btn-danger" id="remove1" name="remove1">
										<i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
									</button>

								</div>
							</div>

							<table id="datagrid1" class="easyui-datagrid" title="签到礼包配置"

								   data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit:false,
										method: 'GET',
										toolbar: '#tb1',
										idField: 'packageid',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

								<thead>
								<tr>
									<th data-options="field:'ck',checkbox:true"></th>
									<th data-options="field:'packageid',align:'center',fixed:false,hidden:true">packageid</th>
									<th data-options="field:'name',align:'center',fixed:false,width:30">礼包名称</th>
									<th data-options="field:'typeidDesc',align:'center',fixed:false,width:30">礼包类型</th>
									<th data-options="field:'sortid',align:'center',fixed:false,width:30">排序标识</th>
									<th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">状态</th>
									<th data-options="field:'describe',align:'center',fixed:false,width:30">礼包描述</th>
<%--									<th data-options="field:'platformkindDesc',align:'center',fixed:false,width:30">平台</th>--%>
									<th data-options="field:'collectdate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">配置时间</th>
									<th data-options="field:'merchant',align:'center',fixed:false,width:60">商户号</th>
									<th data-options="field:'account',align:'center',fixed:false,width:30">操作账号</th>
									<th data-options="field:'createdDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">创建时间</th>
									<th data-options="field:'updatedDate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">修改时间</th>
									<th data-options="field:'option1',align:'center',fixed:false,width:30,formatter: function(value,row,index){return optortion1(value,row,index)}">操作</th>

								</tr>
								</thead>
							</table>

						</div>
					</div>
				</div>

			</div>
			<div title="签到物品配置" style="padding:10px">

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
								<form id="search2-form" method="post">
									<table class="form-table" border="0" cellpadding="1" cellspacing="2">
										<input id="field5" name="field5" type="hidden" class="easyui-textbox"/>
										<tr>
											<td class="form-label">
												<label for="typeid1">物品类型：</label>
											</td>
											<td class="form-editor">
												<input id="typeid1" name="typeid" class="easyui-combobox" editable="false"
													   data-options="panelHeight:'auto', valueField:'id',textField:'text',
													   	data:[{id: '',text: '全部', selected: true},{id: '0',text: '游戏币'},{id: '2',text: '道具'}]" />
											</td>

											<td class="button-group" colspan="2">
												<button type="button" class="btn btn-primary" id="search2" name="search2">
													<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
												</button>
												<button type="button" class="btn btn-warning" id="reset2" name="reset2">
													<span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
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
							<div id="tb2" style="display:none" class="opt-button-float">
								<div class="group-button opt-button-float">

									<button class="layui-btn layui-btn-sm layui-btn-normal" id="add2" name="add2">
										<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
									</button>
									<button class="layui-btn layui-btn-sm layui-btn-danger" id="remove2" name="remove2">
										<i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
									</button>

								</div>
							</div>

							<table id="datagrid2" class="easyui-datagrid" title="签到物品配置" style="width:100%;"

								   data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit:false,
										method: 'GET',
										toolbar: '#tb2',
										idField: 'goodsid',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

								<thead>
								<tr>
									<th data-options="field:'ck',checkbox:true"></th>
									<th data-options="field:'goodsid',align:'center',fixed:false,hidden:true">goodsid</th>
									<th data-options="field:'packageName',align:'center',fixed:false,width:30">礼包名称</th>
									<th data-options="field:'typeidDesc',align:'center',fixed:false,width:30">物品类型</th>
									<th data-options="field:'goodsnum',align:'center',fixed:false,width:30">物品数量</th>
									<th data-options="field:'resourceurl',align:'center',fixed:false,width:30,formatter: function(value,row,index){return showImge(value,row,index)}">物品图片</th>
									<th data-options="field:'collectdate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">配置时间</th>

									<th data-options="field:'option1',align:'center',fixed:false,width:30,formatter: function(value,row,index){return optortion2(value,row,index)}">操作</th>

								</tr>
								</thead>
							</table>

						</div>
					</div>
				</div>

			</div>
			<div title="签到配置" style="padding:10px">

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
								<form id="search3-form" method="post">
									<table class="form-table" border="0" cellpadding="1" cellspacing="2">
										<tr>
											<td class="form-label">
												<label for="typeid3">签到类型：</label>
											</td>
											<td class="form-editor">
												<input id="typeid3" name="typeid" class="easyui-combobox" editable="false"
													   data-options="panelHeight:'auto', valueField:'id',textField:'text',
													   	data:[{id: '',text: '全部', selected: true},{id: '0',text: '每日签到'},{id: '1',text: '累计签到'}]" />
											</td>

											<td class="button-group" colspan="2">
												<button type="button" class="btn btn-primary" id="search3" name="search3">
													<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
												</button>
												<button type="button" class="btn btn-warning" id="reset3" name="reset3">
													<span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
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
							<div id="tb3" style="display:none" class="opt-button-float">
								<div class="group-button opt-button-float">

									<button class="layui-btn layui-btn-sm layui-btn-normal" id="add3" name="add3">
										<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
									</button>
									<button class="layui-btn layui-btn-sm layui-btn-danger" id="remove3" name="remove3">
										<i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
									</button>
									<button class="layui-btn layui-btn-sm layui-btn-normal" id="signrecord3" name="signrecord3">
										<i class="iconfont" data-icon="&#xe6bc;">&#xe6bc;</i><span>签到记录</span>
									</button>

								</div>
							</div>

							<table id="datagrid3" class="easyui-datagrid" title="签到配置"

								   data-options="
										singleSelect: false,
										fitColumns: true,
										collapsible: true,
										multiSort: true,
										checkOnSelect: true,
										cache: false,
										fit:false,
										method: 'GET',
										toolbar: '#tb3',
										idField: 'signid',
										rownumbers: true,
										pagination: true,
										autoRowHeight: false,
										pageSize: 10,
										pageNumber: 1,
										pageList: [10,20,30,40,50,100,200,500,1000]
										">

								<thead>
								<tr>
									<th data-options="field:'ck',checkbox:true"></th>
									<th data-options="field:'signid',align:'center',fixed:false,hidden:true">signid</th>
									<th data-options="field:'typeidDesc',align:'center',fixed:false,width:30">签到类型</th>
									<th data-options="field:'packageName',align:'center',fixed:false,width:30">签到获得礼包</th>
									<th data-options="field:'probabilityDesc',align:'center',fixed:false,width:30">签到抽奖获得礼包的概率</th>
									<th data-options="field:'needday',align:'center',fixed:false,width:30">累计签到所需天数</th>
									<th data-options="field:'sortid',align:'center',fixed:false,width:30">排序标识</th>
									<th data-options="field:'nullityDesc',align:'center',fixed:false,width:30">状态</th>
									<th data-options="field:'collectdate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">配置时间</th>

									<th data-options="field:'option3',align:'center',fixed:false,width:30,formatter: function(value,row,index){return optortion3(value,row,index)}">操作</th>

								</tr>
								</thead>
							</table>

						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	
	
	<!-- 弹出页面 -->
	<div id="dialog" style="overflow-x: hidden" class="easyui-dialog" closed="true"></div>


	<script type="text/javascript">

		$('#tt').tabs({
			onSelect:function(title,index){
				if(index != 0){
					var p = $("#datagrid2").datagrid('getPanel');
					p.panel('resize',{
						height: 450
					});

					var p3 = $("#datagrid3").datagrid('getPanel');
					p3.panel('resize',{
						height: 450
					});
				}
			}
		});

		$(function($){
			//查询图片上传前缀路径
			var configKey = "WebSiteConfig";
			$.ajax({
				method: "GET",
				url: "${pageContext.request.contextPath}/website/stand/queryConfigInfoByConfigKey?configKey=" + configKey,
				processData: false,
				contentType: false,
				crossDomain: true,
				success: function (resp) {
					console.log("resp.data.field5-------------"+ resp.data.field5)
					$("#field5").val(resp.data.field5);//赋值
					search1();
					search3();
				},
				error: function (resp) {
					$.messager.alert('警告', "没有查到上传文件的路径");
				}
			});

			// search1();
			// search3();
			/**
			 * 签到礼包配置*******************************************************************************
			 */
			//查询 签到礼包配置
			$("#search1").unbind();
			$("#search1").click(function(){
				search1();
			});

			//点击重置 签到礼包配置
			$("#reset1").unbind();
			$("#reset1").bind("click",function(){
				$('#search1-form').form('reset');
				search1();
			});

			//打开新增签到礼包配置
			//新增
			$("#add1").unbind();
			$("#add1").bind("click",function(){
				$('#dialog').dialog({
					title: '新增签到礼包配置',
					href: '${pageContext.request.contextPath}/uphold/sign/toSign1AddPage',
					width: 800,
					height: 400,
					closed: false,
					cache: false,
					modal: true,
					collapsible: true,
					maximizable: false,
					resizable: true,
					shadow: true,
					left: 150,
					top:50,
					content: ''
				}).window("center");
			});

			//删除 签到礼包配置
			$("#remove1").unbind();
			$("#remove1").bind("click",function(){
				remove1Byids();
			});

			/**
			 * 签到物品配置*******************************************************************************
			 */
			//查询 签到礼包配置
			$("#search2").unbind();
			$("#search2").click(function(){
				search2();
			});

			//点击重置 签到礼包配置
			$("#reset2").unbind();
			$("#reset2").bind("click",function(){
				$('#search2-form').form('reset');
				//$('#isLocked').combobox('setValue','0');
				//$('#isDeleted').combobox('setValue','0');
				search2();
			});

			//打开新增 签到物品配置
			//新增
			$("#add2").unbind();
			$("#add2").bind("click",function(){
				$('#dialog').dialog({
					title: '新增签到物品配置',
					href: '${pageContext.request.contextPath}/uphold/sign/toSign2AddPage',
					width: 800,
					height: 400,
					closed: false,
					cache: false,
					modal: true,
					collapsible: true,
					maximizable: false,
					resizable: true,
					shadow: true,
					left: 150,
					top:50,
					content: ''
				}).window("center");
			});

			//删除 签到物品配置
			$("#remove2").unbind();
			$("#remove2").bind("click",function(){
				remove2Byids();
			});

			/**
			 * 签到配置*******************************************************************************
			 */
			//查询 签到配置
			$("#search3").unbind();
			$("#search3").click(function(){
				search3();
			});

			//点击重置 签到配置
			$("#reset3").unbind();
			$("#reset3").bind("click",function(){
				$('#search3-form').form('reset');
				//$('#isLocked').combobox('setValue','0');
				//$('#isDeleted').combobox('setValue','0');
				search3();
			});

			//打开新增 签到配置
			//新增
			$("#add3").unbind();
			$("#add3").bind("click",function(){
				$('#dialog').dialog({
					title: '新增签到配置',
					href: '${pageContext.request.contextPath}/uphold/sign/toSign3AddPage',
					width: 800,
					height: 400,
					closed: false,
					cache: false,
					modal: true,
					collapsible: true,
					maximizable: false,
					resizable: true,
					shadow: true,
					left: 150,
					top:50,
					content: ''
				}).window("center");
			});

			//删除 签到配置
			$("#remove3").unbind();
			$("#remove3").bind("click",function(){
				remove3Byids();
			});

			//签到记录 签到配置
			$("#signrecord3").unbind();
			$("#signrecord3").bind("click",function(){
				$('#dialog').dialog({
					title: '签到记录',
					href: '${pageContext.request.contextPath}/uphold/sign/toSign3RecordPage',
					width: 1100,
					height: 500,
					closed: false,
					cache: false,
					modal: true,
					collapsible: true,
					maximizable: false,
					resizable: true,
					shadow: true,
					left: 150,
					top:50,
					content: ''
				}).window("center");
			});

		});

		/**
		 * 签到礼包配置--------------------------------------------------------------------------------------------------------------
		 */
		//查询 签到礼包配置
		function search1() {
			var obj = $("#search1-form").serializeObject();
			// console.log( obj.typeid );
			$('#datagrid1').datagrid({
				url: '${pageContext.request.contextPath}/uphold/sign/queryGamePackageWithPage',
				queryParams: {
					typeid : obj.typeid
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
			search2();
		}

		//操作按钮 签到礼包配置
		function optortion1(value,row,index){
			var a = "<button class='layui-btn layui-btn-xs layui-btn-warm' onclick='optionEditEvent1({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			a = a.format(row.packageid,'编辑');
			return a;
		}

		function optionEditEvent1(id){
			openEditWindown1(id);
		}

		//打开 编辑 窗口
		function openEditWindown1(id){
			$('#dialog').dialog({
				title: '编辑签到礼包配置',
				href: '${pageContext.request.contextPath}/uphold/sign/toSign1EditPage',
				dialogParams: {packageid: id},
				width: 800,
				height: 400,
				closed: false,
				cache: false,
				modal: true,
				collapsible: true,
				maximizable: false,
				resizable: true,
				shadow: true,
				left: 150,
				top:50,
				content: ''
			}).window("center");
		}

		//删除 签到礼包配置
		function remove1Byids(){
			var rows = $('#datagrid1').datagrid("getSelections");
			//console.log(rows);
			if(rows.length == 0){
				$.messager.alert('警告','请选择需要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要删除选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						//console.log(index);
						//console.log(obj.id);
						nmArr.push(o.packageid);
					});
					//console.log(nmArr);
					//var ids = s = nmArr.join(",");
					/*var data = {
						configids : ids
					}*/
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/uphold/sign/removeGamePackageByIds?packageids="+nmArr,
						type: 'DELETE',
						//dataType: 'json',
						//data: data,
						success: function(resp){
							//{success: true, errors: Array(0), data: null, lastError: null}
							//console.log(resp);
							if(resp.success){
								//执行成功
								// 关闭窗口
								//$('#dialog').dialog("close");

								//提示操作成功
								$.messager.alert({
									title:'提示',
									msg:'删除签到礼包配置成功',
									icon: 'info',
									//width: 300,
									//top:200 , //与上边距的距离
									fn:function(){
										// 刷新列表
										//console.log ( $('#datagrid1') );
										$('#datagrid1').datagrid('load');
										//清除之前勾选的行
										$('#datagrid1').datagrid('clearChecked');
									}
								});

								// 刷新列表
								//console.log ( $('#datagrid1') );
								//$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								//$('#datagrid1').datagrid('clearChecked');

								//$.messager.alert('警告','删除成功');

							}else{
								$.messager.alert('警告','删除签到礼包配置失败');
							}
						}
					});
				}
			});
		}


		/**
		 * 签到物品配置----------------------------------------------------------------------------------------
		 */
		//查询 签到物品配置
		function search2() {
			var obj = $("#search2-form").serializeObject();
			//console.log( obj );
			$('#datagrid2').datagrid({
				url: '${pageContext.request.contextPath}/uphold/sign/queryGamePackageGoodsWithPage',
				queryParams: {
					typeid : obj.typeid
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"

			});
		}

		//操作按钮 签到物品配置
		function optortion2(value,row,index){
			var a = "<button class='layui-btn layui-btn-xs layui-btn-warm' onclick='optionEditEvent2({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			a = a.format(row.goodsid,'编辑');
			return a;
		}

		//签到物品配置 显示物品图片
		function showImge(value,row,index){
			// console.log("value:"+value)
			// console.log("row:"+row)
			// console.log("field5-------------"+ $("#field5").val()+row.resourceurl)
			// <a href="https://www.baidu.com" target="_blank">百度</a>
			<%--var img = "<a href='${pageContext.request.contextPath}/static/Upload{0}' target='_blank'><img style='height: 50px;width: 100px;' src='${pageContext.request.contextPath}/static/Upload{1}' /></a>";--%>
			var img = "<a href='"+$("#field5").val()+row.resourceurl+"' target='_blank'><img style='height: 50px;width: 100px;' src='"+$("#field5").val()+row.resourceurl+"' /></a>";
			img = img.format(row.resourceurl,row.resourceurl);
			return img;
		}

		//编辑 签到物品配置
		function optionEditEvent2(id){
			openEditWindown2(id);
		}

		//打开 编辑 窗口
		function openEditWindown2(id){
			$('#dialog').dialog({
				title: '编辑签到礼包配置',
				href: '${pageContext.request.contextPath}/uphold/sign/toSign2EditPage',
				dialogParams: {goodsid: id},
				width: 800,
				height: 400,
				closed: false,
				cache: false,
				modal: true,
				collapsible: true,
				maximizable: false,
				resizable: true,
				shadow: true,
				left: 150,
				top:50,
				content: ''
			}).window("center");
		}

		//删除 签到物品配置
		function remove2Byids(){
			var rows = $('#datagrid2').datagrid("getSelections");
			//console.log(rows);
			if(rows.length == 0){
				$.messager.alert('警告','请选择需要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要删除选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						//console.log(index);
						//console.log(obj.id);
						nmArr.push(o.goodsid);
					});
					//console.log(nmArr);
					//var ids = s = nmArr.join(",");
					/*var data = {
						configids : ids
					}*/
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/uphold/sign/removeGamePackageGoodsByIds?goodsids="+nmArr,
						type: 'DELETE',
						//dataType: 'json',
						//data: data,
						success: function(resp){
							//{success: true, errors: Array(0), data: null, lastError: null}
							//console.log(resp);
							if(resp.success){
								//执行成功
								// 关闭窗口
								//$('#dialog').dialog("close");

								//提示操作成功
								$.messager.alert({
									title:'提示',
									msg:'删除签到物品配置成功',
									icon: 'info',
									//width: 300,
									//top:200 , //与上边距的距离
									fn:function(){
										// 刷新列表
										//console.log ( $('#datagrid1') );
										$('#datagrid2').datagrid('load');
										//清除之前勾选的行
										$('#datagrid2').datagrid('clearChecked');
									}
								});

								// 刷新列表
								//console.log ( $('#datagrid1') );
								//$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								//$('#datagrid1').datagrid('clearChecked');

								//$.messager.alert('警告','删除成功');

							}else{
								$.messager.alert('警告','删除签到物品配置失败');
							}
						}
					});
				}
			});
		}

		/**
		 * 签到配置-----------------------------------------------------------------------
		 */
		//查询 签到配置
		function search3() {
			var obj = $("#search3-form").serializeObject();
			$('#datagrid3').datagrid({
				url: '${pageContext.request.contextPath}/uphold/sign/queryGameSignInWithPage',
				queryParams: {
					typeid : obj.typeid
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		//操作按钮 签到配置
		function optortion3(value,row,index){
			var a = "<button class='layui-btn layui-btn-xs layui-btn-warm' onclick='optionEditEvent3({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
			a = a.format(row.signid,'编辑');
			return a;
		}

		//编辑 签到配置
		function optionEditEvent3(id){
			openEditWindown3(id);
		}

		//打开 编辑 窗口
		function openEditWindown3(id){
			$('#dialog').dialog({
				title: '编辑签到礼包配置',
				href: '${pageContext.request.contextPath}/uphold/sign/toSign3EditPage',
				dialogParams: {signid: id},
				width: 800,
				height: 400,
				closed: false,
				cache: false,
				modal: true,
				collapsible: true,
				maximizable: false,
				resizable: true,
				shadow: true,
				left: 150,
				top:50,
				content: ''
			}).window("center");
		}

		//删除 签到配置
		function remove3Byids(){
			var rows = $('#datagrid3').datagrid("getSelections");
			//console.log(rows);
			if(rows.length == 0){
				$.messager.alert('警告','请选择需要操作的记录');
				return;
			}

			$.messager.confirm('确认',"确定要删除选中的记录吗",function(r){
				if (r){
					//拼接主键
					var nmArr = [];
					$.each(rows,function(index,o){
						//console.log(index);
						//console.log(obj.id);
						nmArr.push(o.signid);
					});
					//console.log(nmArr);
					//var ids = s = nmArr.join(",");
					/*var data = {
						configids : ids
					}*/
					//提交到后台
					$.ajax({
						url: "${pageContext.request.contextPath}/uphold/sign/removeGameSignInByIds?signids="+nmArr,
						type: 'DELETE',
						//dataType: 'json',
						//data: data,
						success: function(resp){
							//{success: true, errors: Array(0), data: null, lastError: null}
							//console.log(resp);
							if(resp.success){
								//执行成功
								// 关闭窗口
								//$('#dialog').dialog("close");

								//提示操作成功
								$.messager.alert({
									title:'提示',
									msg:'删除签到配置成功',
									icon: 'info',
									//width: 300,
									//top:200 , //与上边距的距离
									fn:function(){
										// 刷新列表
										//console.log ( $('#datagrid1') );
										$('#datagrid3').datagrid('load');
										//清除之前勾选的行
										$('#datagrid3').datagrid('clearChecked');
									}
								});

								// 刷新列表
								//console.log ( $('#datagrid1') );
								//$('#datagrid1').datagrid('load');
								//清除之前勾选的行
								//$('#datagrid1').datagrid('clearChecked');

								//$.messager.alert('警告','删除成功');

							}else{
								$.messager.alert('警告','删除签到配置失败');
							}
						}
					});
				}
			});
		}
	</script>
</body>
</html>

