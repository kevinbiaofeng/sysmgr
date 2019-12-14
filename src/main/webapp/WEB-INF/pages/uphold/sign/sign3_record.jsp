<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增</title>
</head>
<body>
	
	<div id="caseinfo-panel" class="container-fluid">
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
						<form id="search4-form" method="post">
							<table class="form-table" border="0" cellpadding="1" cellspacing="2">
								<tr>
									<td class="form-label">
										<label for="gameid">玩家ID：</label>
									</td>
									<td class="form-editor">
										<input id="gameid" name="gameid" class="easyui-numberbox" />
									</td>

									<td class="form-label">
										<label for="signtype">签到类型：</label>
									</td>
									<td class="form-editor">
										<input id="signtype" name="signtype" class="easyui-combobox"
											   data-options="valueField:'id',textField:'text',
													   	data:[{id: -1,text: '全部'},{id: 0,text: '每日签到'},{id: 1,text: '累计签到'}]" value="-1"/>
									</td>

									<td class="form-label">
										<label for="collectdateStart">日期查询test：</label>
									</td>
									<td class="form-editor">
										<input id="collectdateStart" name="collectdateStartStr" class="easyui-datetimebox" editable="false"/>
<%--										<input id="collectdateStartStr1" name="collectdateStartStr" type="hidden" class="easyui-textbox" />--%>
									</td>

									<td class="form-label">
										<label for="collectdateEnd">至：</label>
									</td>
									<td class="form-editor">
										<input id="collectdateEnd" name="collectdateEndStr" class="easyui-datetimebox" editable="false"/>
<%--										<input id="collectdateEndStr1" name="collectdateEndStr" type="hidden" class="easyui-textbox" />--%>
									</td>

									<td class="button-group" colspan="2">
										<button type="button" class="btn btn-primary" id="search4" name="search4">
											<span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
										</button>
										<%--<button type="button" class="btn btn-warning" id="reset" name="reset">
											<span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
										</button>--%>
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
					<div id="tb4" style="display:none" class="opt-button-float">
						<div class="group-button opt-button-float">

							<%--<button class="layui-btn layui-btn-sm layui-btn-normal" onclick="cleanOnLine()">
                            	<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>清除卡线</span>
                        	</button>--%>
							<%--<button class="layui-btn layui-btn-sm layui-btn-danger" id="delete" name="delete">
								<i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
							</button>--%>
							<!-- <button class="layui-btn layui-btn-sm layui-btn-warm" id="edit" name="edit">
                                <i class="iconfont" data-icon="&#xe653;">&#xe653;</i><span>编辑</span>
                            </button> -->
							<!-- <button class="layui-btn layui-btn-sm" id="detail" name="detail">
                                <i class="iconfont" data-icon="&#xe67a;">&#xe67a;</i><span>详情</span>
                            </button> -->
							<!-- <button class="layui-btn layui-btn-sm layui-btn-danger" id="delete" name="delete">
                                <i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
                            </button> -->
						</div>
					</div>

					<!-- datagrid1 queryParams: {isLocked : 0,isDeleted : 0}, -->
					<table id="datagrid4" class="easyui-datagrid" title="签到记录" style="width:100%;height:430px"

						   data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							url: '${pageContext.request.contextPath}/uphold/sign/queryRecordGameSignInWithPage',
							queryParams: {signtype : -1},
							method: 'GET',
							toolbar: '#tb4',
							idField: 'recordid',
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
							<th data-options="field:'recordid',align:'center',fixed:false,hidden:true">recordid</th>
							<th data-options="field:'collectdate',align:'center',fixed:false,width:50,formatter:function(value,row,index){return $.dateFormatter(value)}">记录时间</th>
							<th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
							<th data-options="field:'nickname',align:'center',fixed:false,width:30">用户昵称</th>
							<th data-options="field:'signtypeDesc',align:'center',fixed:false,width:30">签到类型</th>
							<th data-options="field:'packagename',align:'center',fixed:false,width:30">礼包名称</th>
							<th data-options="field:'packagegoods',align:'center',fixed:false,width:30">礼包物品</th>
							<th data-options="field:'probability',align:'center',fixed:false,width:30">签到抽奖获得礼包的概率</th>
							<th data-options="field:'needday',align:'center',fixed:false,width:30">累计签到所需天数</th>
							<th data-options="field:'totalday',align:'center',fixed:false,width:30">累计签到天数</th>
							<th data-options="field:'clinetip',align:'center',fixed:false,width:30">领取地址</th>

						</tr>
						</thead>
					</table>

				</div>
			</div>
		</div>

		<div class="opt-button-float">
			<%-- <button type="button" class="btn btn-primary" id="save" name="save">
                 <span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
             </button>--%>
			<button type="button" class="btn btn-default" id="cancel" name="cancel">
				<span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
			</button>
		</div>


	</div>
	
	
	
	<script type="text/javascript">

		//初始化日期
		/*$('#collectdateStart').datebox('setValue', $._dateFormatter(new Date()));
		$('#collectdateEnd').datebox('setValue', $._dateFormatter(new Date()));
		$('#collectdateStartStr').textbox('setValue', $._dateFormatterYmd(new Date()));
		$('#collectdateEndStr').textbox('setValue', $._dateFormatterYmd(new Date()));*/

		/*$('#collectdateEnd').datebox({
			onSelect: function(date){
				//var v = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate();
				var v = $._dateFormatterYmd(date);
				$('#collectdateEndStr1').textbox('setValue', v);
			}
		});

		$('#collectdateStart').datebox({
			onSelect: function(date){
				//var v = date.getFullYear()+""+(date.getMonth()+1)+""+date.getDate();
				var v = $._dateFormatterYmd(date);
				$('#collectdateStartStr1').textbox('setValue', v);
			}
		});*/

		$(function($){

			//$('#collectdateStart').datebox('setValue', $._dateFormatter(new Date()));
			//$('#collectdateEnd').datebox('setValue', $._dateFormatter(new Date()));
			//$('#collectdateStartStr1').textbox('setValue', "111111");
			//$('#collectdateStartStr1').textbox('setValue', $._dateFormatterYmd(new Date()));
			//$('#collectdateEndStr1').textbox('setValue', $._dateFormatterYmd(new Date()));

			//search4();

			//查询 签到记录
			$("#search4").unbind();
			$("#search4").click(function(){
				search4();
			});

		});

		//查询 签到记录
		function search4() {
			var obj = $("#search4-form").serializeObject();
			//console.log( obj );
			//var collectdateEndStr = "";
			/*var endStr = obj.collectdateEndStr;
			if(endStr == null || endStr == ""){
				var d = $('#collectdateEnd').datebox('getValue');
				obj['collectdateEndStr'] = $._dateFormatterYmd(d);
			}*/

			//var collectdateStartStr = "";
			/*var startStr = obj.collectdateStartStr;
			if(startStr == null || startStr == ""){
				var d = $('#collectdateStart').datebox('getValue');
				obj['collectdateStartStr'] = $._dateFormatterYmd(d);
			}*/

			$('#datagrid4').datagrid('load',{
				gameid : obj.gameid,
				signtype : obj.signtype,
				collectdateEndStr: obj.collectdateEndStr,
				collectdateStartStr: obj.collectdateStartStr
			});
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