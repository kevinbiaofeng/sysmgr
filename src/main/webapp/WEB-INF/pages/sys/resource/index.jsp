<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>资源管理</title>
	<%@ include file="/static/pub/include.jsp"%>
</head>

<body>

<div id="search-panel" class="container-fluid">
	<!-- 查询条件信息 -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<!-- <div class="panel-heading">
                    <span class="panel-title">查询条件</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle">
                        <i class="iconfont">&#xe603;</i>
                    </a>
                </div> -->
				<!-- <div class="panel-body">
                    <form id="search-form" method="post">
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2">
                            <tr>
                                <td class="form-label">
                                    <label for="menuid">菜单ID：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="menuid" name="menuid" class="easyui-textbox" />
                                </td>
                                <td class="form-label">
                                    <label for="parentMenuid">父菜单ID：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="parentMenuid" name="parentMenuid" class="easyui-textbox" />
                                </td>
                                <td class="form-label">
                                    <label for="name">菜单名称：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="name" name="name" class="easyui-textbox" />
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
                </div> -->
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
						<button class="layui-btn layui-btn-sm layui-btn-normal" id="add" name="add">
							<i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
						</button>
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

				<!-- 右键按钮列表 -->
				<div style="display:none">
					<div id="mm" class="easyui-menu" data-options="onClick:menuHandler">
						<div id="addNode" name="addNode" data-options="iconCls:'icon-add'">新增</div>
						<div id="editNode" name="editNode" data-options="iconCls:'icon-edit'">编辑</div>
						<div id="deleteNode" name="deleteNode" data-options="iconCls:'icon-remove'">删除</div>
					</div>
				</div>

				<!-- grid列表区域   -->
				<table id="treegrid1" title="资源信息" style="width:100%;height:600px"

					   data-options="
							singleSelect: true,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							<%--url: '${pageContext.request.contextPath}/static/data/treegrid_data1.json',--%>
							url: '${pageContext.request.contextPath}/sys/resource/queryResourceTree',
							method: 'GET',
							idField: 'id',
							treeField: 'name',
							rownumbers: true,
							toolbar: '#tb',
							autoRowHeight: false,
							animate: true,
							state:'closed'
							">

					<thead>
					<tr>

						<!-- <th data-options="field:'ck',checkbox:true"></th> -->
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',align:'left',hidden:true,width:30">id</th>
						<th data-options="field:'parentMenuid',align:'left',hidden:true,width:30">id</th>
						<th data-options="field:'menuid',align:'left',hidden:true,width:30">id</th>
						<th data-options="field:'isLeaf',align:'left',hidden:true,width:30">id</th>
						<th data-options="field:'lev',align:'left',hidden:true,width:30">id</th>
						<th data-options="field:'name',align:'left',width:30">名称</th>
						<th data-options="field:'createdByName',align:'left',width:30">创建者</th>
						<th data-options="field:'createdDate',align:'center',width:40,fixed:false,formatter:function(value,row,index){return $.dateFormatter(value)}">创建日期</th>
						<th data-options="field:'updatedByName',align:'left',width:30">修改者</th>
						<th data-options="field:'updatedDate',align:'center',width:40,fixed:false,formatter:function(value,row,index){return $.dateFormatter(value)}">修改日期</th>

					</tr>
					</thead>
				</table>

			</div>
		</div>
	</div>

</div>


<!-- 弹出页面 -->
<div id="dialog"  class="easyui-dialog" closed="true"></div>


<script type="text/javascript">

	//设置日期
	$(function($){

		//查询
		$("#search").unbind();
		$("#search").click(function(){
			//查询

		});

		//点击重置
		$("#reset").unbind();
		$("#reset").bind("click",function(){
			//重置
			$('#search-form').form('clear');

		});

		//右键点击事件
		$('#treegrid1').treegrid({

			// 为所有节点绑定右击响应事件
			onContextMenu: function(e,node){
				//console.log(e);
				//console.log(node);
				// 阻止浏览器默认的右键菜单事件
				e.preventDefault();
				// 显示右键菜单
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
				//console.log(node);
				var leaf = node.leaf;
				if(leaf){
					//true子节点
					/* var addNodeItem = $('#mm').menu('findItem','新增');
                    var editNodeItem = $('#mm').menu('findItem','编辑');
                    var delNodeItem = $('#mm').menu('findItem','删除'); */

					//console.log(addNodeItem == null);
					/* if(addNodeItem == null){
                        $('#mm').menu('appendItem', {
                            text: '新增',
                            iconCls: 'icon-add',
                            onclick: function(){
                                console.log(node);
                            }
                        });
                    } */
					var item = $('#mm').menu('findItem','删除');
					$('#mm').menu('enableItem', item.target);

				}else{
					//false 父节点
					//禁用删除按钮
					// 查找“打开”项并禁用它
					var item = $('#mm').menu('findItem','删除');
					$('#mm').menu('disableItem', item.target);


				}
			}

		});

		//新增
		$("#add").unbind();
		$("#add").bind("click",function(){
			//{parentMenuid: 0,level: 1,icon: '&#xe60e;'}
			openNewWindown(0,1,'&#xe60e;');
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

	function search(){
		var obj = $("#search-form").serializeObject();
		/* $('#datagrid1').datagrid('load',{
            id : obj.id,
            name : obj.name,
            ageBegin : obj.ageBegin,
            ageEnd : obj.ageEnd,
            birthdayBegin : obj.birthdayBegin,
            birthdayEnd : obj.birthdayEnd
        }); */
	}

	function menuHandler(item){
		var node = $('#treegrid1').treegrid('getSelected');
		console.log(node);
		if(node == null){
			$.messager.alert('警告','请先选择一个节点');
			return;
		}
		if("addNode" === item.id){
			//addNode
			//console.log("新增节点");
			addNodeFunc(node);
		}else if("editNode" === item.id){
			//editNode
			//console.log("编辑节点");
			editNodeFunc(node);
		}else if("deleteNode" === item.id){
			//deleteNode
			//console.log("删除节点");
			deleteNodeFunc(node);

		}

	}

	//删除节点
	function deleteNodeFunc(node){
		var ids = [];
		ids.push(node.id);
		//console.log(ids);
		deleteByIds(ids);
	}

	function deleteByIds(ids){
		$.messager.confirm('确认','您确认想要删除记录吗？',function(action){
			if(action){
				//提交到后台
				$.ajax({
					url: "${pageContext.request.contextPath}/sys/resource/deleteSysResource?ids=" + ids,
					type: 'DELETE',
					dataType: 'json',
					success: function(resp){
						//console.log(resp);
						if(resp.success){
							//执行成功
							// 刷新列表
							//console.log ( $('#datagrid1') );
							$('#treegrid1').treegrid('load');

						}
					}
				});
			}
		});
	}

	//新增节点
	function addNodeFunc(node){
		//console.log(node);
		openNewWindown(node.menuid,(node.lev + 1),'&#xe61a;');
	}

	//打开新增窗口
	function openNewWindown(parentMenuid,level,icon){
		if(level == 3){
			icon = "&#xe67a;";
		}
		$('#dialog').dialog({
			title: '新增菜单',
			href: '${pageContext.request.contextPath}/sys/resource/toResourceAddPage',
			dialogParams: {parentMenuid: parentMenuid ,level: level ,icon: icon },
			width: 800,
			height: 600,
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

	//编辑节点
	function editNodeFunc(node){
		//console.log(node);
		openEditWindown(node.id);
	}


	//打开 编辑 窗口
	function openEditWindown(id){
		$('#dialog').dialog({
			title: '编辑菜单',
			href: '${pageContext.request.contextPath}/sys/resource/toResourceEditPage',
			dialogParams: {id: id},
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
		});

		$('#dialog').dialog('open');
	}
</script>
</body>
</html>








