<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>卡号信息</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">

                    <!-- 操作按钮区域 -->
                    <div id="tb1" style="display:none" class="opt-button-float">
                        <div class="group-button opt-button-float">

                            <button class="layui-btn layui-btn-sm layui-btn-normal" id="add" name="add">
                                <i class="iconfont" data-icon="&#xe649;">&#xe649;</i><span>新增</span>
                            </button>
                            <button class="layui-btn layui-btn-sm layui-btn-danger" id="remove" name="remove" style="display: none">
                                <i class="iconfont" data-icon="&#xe626;">&#xe626;</i><span>删除</span>
                            </button>
                        </div>
                    </div>

                    <table id="datagrid2" class="easyui-datagrid"

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
							idField: 'userId',
							rownumbers: false,
							pagination: true,
							autoRowHeight: false,
							pageSize: 10,
							pageNumber: 1,
							pageList: [10,20,30,40,50,100,200,500,1000]
							">

                        <thead>
                        <tr>
                            <th data-options="field:'ck',checkbox:true"></th>
                            <th data-options="field:'bindid',align:'center',fixed:false,hidden:true">bindid</th>
                            <th data-options="field:'userid',align:'center',fixed:false,width:30">用户标识</th>
                            <th data-options="field:'merchant',align:'center',fixed:false,width:50">商户号</th>
                            <th data-options="field:'typeNameDesc',align:'center',fixed:false,width:50">绑定类型</th>
                            <th data-options="field:'bankchoiceDesc',align:'center',fixed:false,width:50">银行名称</th>
                            <th data-options="field:'bankcardid',align:'center',fixed:false,width:50">卡号</th>
                            <th data-options="field:'bankname',align:'center',fixed:false,width:50">银行全称</th>
                            <th data-options="field:'idcardname',align:'center',fixed:false,width:50">真实姓名</th>
                            <th data-options="field:'nullityDesc',align:'center',fixed:false,width:50">是否启用</th>
                            <th data-options="field:'bindtime',align:'center',fixed:false,width:50,formatter:function(value,row,index){return $.dateFormatter(value)}">绑定时间</th>

                            <th data-options="field:'option1',align:'center',fixed:false,width:30,formatter:function(value,row,index){return optortion1(value,row,index)}">操作</th>

                        </tr>
                        </thead>
                    </table>

                </div>
            </div>

            <div class="opt-button-float">
                <button type="button" class="btn btn-default" id="cancel" name="cancel">
                    <span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
                </button>
            </div>

        </div>
    </div>
</div>

<!-- 弹出页面 -->
<div id="dialog1" style="overflow-x: hidden" class="easyui-dialog" closed="true"></div>

<script type="text/javascript">

    //加载参数
    var param = $.getOpenDialogParam("dialog");

    $(function($){
    	$('#datagrid2').datagrid({
    		url: '${pageContext.request.contextPath}/withdraw/bindcard/queryBindBankCardsWithPage?userid='+${userId},
			pageNumber:1,
			view: myview,
			loadMsg: "数据加载中，请稍后！",
			emptyMsg: "未搜索到相关数据！"
		});
    	
    	
        //添加
        $("#add").unbind();
        $("#add").bind("click",function(){
            $('#dialog1').dialog({
                title: '新增绑卡信息',
                href: '${pageContext.request.contextPath}/withdraw/bindcard/toBindcardAddPage',
                dialogParams: {userid : param.userId},
                width: 800,
                height: 400,
                closed: false,
                cache: false,
                modal: true,
                collapsible: true,
                maximizable: false,
                resizable: true,
                shadow: true,
                content: ''
            }).window('center');
        });

        //删除
        $("#remove").unbind();
        $("#remove").bind("click",function(){
            removeByids();
        });

    });

    /**
     * 按钮
     * @returns {*|*}
     */
    function optortion1(value,row,index){
        var b = "<button class='layui-btn layui-btn-xs layui-btn-warm' onclick='optionEditEvent({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
        b = b.format(row.bindid,"编辑");
        return b;
    }

    //编辑
    function optionEditEvent(id){
        openEditWindown(id);
    }

    //打开 编辑 窗口
    function openEditWindown(id){
        $('#dialog1').dialog({
            title: '编辑用户卡号信息',
            href: '${pageContext.request.contextPath}/withdraw/bindcard/toBindcardEditPage',
            dialogParams: {bindid: id},
            width: 1000,
            height: 500,
            closed: false,
            cache: false,
            modal: true,
            collapsible: true,
            maximizable: false,
            resizable: true,
            shadow: true,
            content: ''
        }).window('center');
    }


    //取消
    $("#cancel").unbind();
    $("#cancel").bind("click",function(){
        //alert("取消");
        $('#dialog').dialog("close");
    });

    //根据主键删除
    function removeByids(){
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
                    nmArr.push(o.bindid);
                });
                //提交到后台
                $.ajax({
                    url: "${pageContext.request.contextPath}/withdraw/bindcard/removeBindBankCardsByIds?ids="+nmArr,
                    type: 'DELETE',
                    success: function(resp){
                        if(resp.success){
                            //提示操作成功
                            $.messager.alert({
                                title:'提示',
                                msg:'删除成功',
                                icon: 'info',
                                fn:function(){
                                    // 刷新列表
                                    $('#datagrid2').datagrid('load');
                                    //清除之前勾选的行
                                    $('#datagrid2').datagrid('clearChecked');
                                }
                            });
                        }else{
                            $.messager.alert('警告','删除失败');
                        }
                    }
                });
            }
        });

    }


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