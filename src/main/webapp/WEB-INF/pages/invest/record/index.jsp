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
<title>充值记录</title>
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
										<label for="gameId">玩家ID：</label>
									</td>
									<td class="form-editor">
										<input id="gameId" name="gameId" class="easyui-numberbox" />
										<span style="color: red">*请输入数字，最大长度9位</span>
									</td>

									<td class="form-label">
										<label for="orderstatus">状态：</label>
									</td>
									<td class="form-editor">
										<input id="orderstatus" name="orderstatus" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[{id: -1,text: '全部'},{id: 0,text: '未支付'},{id: 1,text: '已支付'}]" value="-1"/>
									</td>

									<td class="form-label">
										<label for="shareid">类型：</label>
									</td>
									<td class="form-editor">
										<input id="shareid" name="shareid" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', valueField:'id',textField:'text',
											data:[
												{id: 0,text: '全部充值'},{id: 101,text: '手机微信充值'},{id: 102,text: 'H5微信充值'},
												{id: 201,text: '手机支付宝充值'},{id: 301,text: '手机零钱充值'},{id: 302,text: '竣付通微信充值'},
												{id: 303,text: '竣付通支付宝充值'},{id: 800,text: '手机苹果充值'}
											]" value="0"/>
									</td>


									<td class="form-label">
										<label for="orderdateStartStr">日期查询：</label>
									</td>
									<td class="form-editor">
										<input id="orderdateStartStr" name="orderdateStartStr" class="easyui-datetimebox" editable="false"/>
										<label for="orderdateEndStr">至：</label>
										<input id="orderdateEndStr" name="orderdateEndStr" class="easyui-datetimebox" editable="false"/>
									</td>

									<td class="button-group" colspan="2">
										<button type="button" class="btn btn-primary" id="search" name="search">
											<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
										</button>
										<button type="button" class="btn btn-warning" id="reset" name="reset">
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
				<!-- <div class="panel-heading">
					<span class="panel-title"></span>
					<a href="javascript:void(0);" class="pull-right panel-toggle">
						<i class="iconfont">&#xe603;</i>
					</a>
				</div> -->
				<div class="panel-body" >
					
					<!-- 操作按钮区域 -->
					<div id="tb" style="display:none" class="opt-button-float">
						<div class="group-button opt-button-float">

						</div>
					</div>

					<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
					<table id="datagrid1" class="easyui-datagrid" title="充值记录信息"

						   data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							fit:false,
							queryParams: {orderstatus : -1,shareid : 0},
							method: 'GET',
							toolbar: '#tb',
							idField: 'onlineid',
							rownumbers: true,
							pagination: true,
							autoRowHeight: false,
							pageSize: 10,
							pageNumber: 1,
							pageList: [10,20,30,40,50,100,200,500,1000]
							">

						<thead>
							<tr>
								<%--<th data-options="field:'ck',checkbox:true"></th>--%>
								<!-- <th data-options="field:'aaaa',align:'center',fixed:false,hidden:true">编号1111</th> -->
								<%--<th data-options="field:'id',align:'center',fixed:false,hidden:true,width:30">主键</th>--%>
								<th data-options="field:'onlineid',align:'center',fixed:false,hidden:true">id</th>
								<th data-options="field:'userid',align:'center',fixed:false,hidden:true">userid</th>
								<th data-options="field:'orderdate',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">订单时间</th>
								<th data-options="field:'orderid',align:'center',fixed:false,width:100">订单号码</th>
								<th data-options="field:'shareidDesc',align:'center',fixed:false,width:30">充值类型</th>
								<th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
								<th data-options="field:'nickname',align:'center',fixed:false,width:30,formatter:function(value,row,index){return linkNickName(value,row,index)}">玩家昵称</th>
								<th data-options="field:'amount',align:'center',fixed:false,width:30">支付金额</th>
								<th data-options="field:'scoretypeDesc',align:'center',fixed:false,width:40">充值货币类型</th>
								<th data-options="field:'beforeAllScore',align:'center',fixed:false,width:30">充值前数值</th>
								<th data-options="field:'scoreDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">充值数值</th>
								<th data-options="field:'otherpresentDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">额外赠送</th>
								<th data-options="field:'afterAllScore',align:'center',fixed:false,width:30">充值后数值</th>
								<th data-options="field:'orderstatusDesc',align:'center',fixed:false,width:30">充值状态</th>
								<th data-options="field:'orderaddress',align:'center',fixed:false,width:40">订单地址</th>

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

		//var abc = "<shiro:principal property='name'/>";
		//console.log("abc==========>" + abc);

		//设置日期
		/*$('#orderdateEnd').datebox({
			onSelect: function(date){
				//var v = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate();
				var v = $._dateFormatterYmd(date);
				$('#orderdateEndStr').textbox('setValue', v);
			}
		});

		$('#orderdateStart').datebox({
			onSelect: function(date){
				//var v = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate();
				var v = $._dateFormatterYmd(date);
				$('#orderdateStartStr').textbox('setValue', v);
			}
		});*/

		$(function($){
			$('#gameId').numberbox('textbox').attr('maxlength', 9);
			search();
			//查询
			$("#search").unbind();
			$("#search").click(function(){
				search();
			});

			//点击重置
			$("#reset").unbind();
			$("#reset").bind("click",function(){
				$('#search-form').form('clear');
				$('#orderstatus').combobox('setValue','-1');
				$('#shareid').combobox('setValue','0');
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
				url:'${pageContext.request.contextPath}/invest/record/queryRecordWithPage',
				pageNumber:1,
				queryParams : {
					gameid : obj.gameId,
					orderstatus: obj.orderstatus,
					shareid: obj.shareid,
					orderdateStartStr: obj.orderdateStartStr,
					orderdateEndStr: obj.orderdateEndStr
				},
				view: myview,
				loadMsg: "数据加载中，请稍后！",
				emptyMsg: "未搜索到相关数据！"
			});
		}

		$(window).resize(function(){
			$('#datagrid1').datagrid('resize');
		});

		//给昵称 添加链接
		function linkNickName(value,row,index){
			//console.log(row);
			var nickName = "<a href='javascript:;' onclick='openAccountInfo({0})' style='color: #00ee00'>{1}</a>";
			var result = nickName.format(row.userid,row.nickname);
			return result;
		}

		//打开项目详情
		function openAccountInfo(userId){
			$('#dialog').dialog({
				title: '编辑管理员',
				href: '${pageContext.request.contextPath}/account/user/toAccountEditPage?userId='+userId,
				dialogParams: {userId : userId},
				width: 1000,
				height: 520,
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
			});

			$('#dialog').dialog('open');

		}



	</script>
</body>
</html>

