<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>金币赠送记录</title>
    <%@ include file="/static/pub/include.jsp" %>
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
                                    <input id="gameId" name="gameId" class="easyui-numberbox"/>
                                    <span style="color: red">*请输入数字，最大长度9位</span>
                                </td>

                                <td class="form-label">
                                    <label for="collectdateStartStr">日期查询：</label>
                                </td>
                                <td class="form-editor" colspan="3">
                                    <input id="collectdateStartStr" name="collectdateStartStr"
                                           class="easyui-datetimebox" editable="false"/>
                                    <label for="collectdateEndStr">至：</label>
                                    <input id="collectdateEndStr" name="collectdateEndStr" class="easyui-datetimebox"
                                           editable="false"/>
                                </td>

                                <td class="button-group" colspan="2">
                                    <button type="button" class="btn btn-primary" id="search" name="search">
                                        <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                                    </button>
                                    <button type="button" class="btn btn-warning" id="resetButton" name="resetButton">
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
            <div class="panel-body">

                <!-- 操作按钮区域 -->
                <div id="tb" style="display:none" class="opt-button-float">
                    <div class="group-button opt-button-float">
                    </div>
                </div>

                <table id="datagrid1" class="easyui-datagrid" title="金币赠送记录"

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
                        <th data-options="field:'recordid',align:'center',fixed:false,hidden:true">recordid</th>
                        <th data-options="field:'collectdate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">
                            	赠送时间
                        </th>
                        <th data-options="field:'gameId',align:'center',fixed:false,width:30">玩家ID</th>
                        <th data-options="field:'nickName',align:'center',fixed:false,width:30">玩家昵称</th>
                        <th data-options="field:'accounts',align:'center',fixed:false,width:30">会员名称</th>
                        <th data-options="field:'curgoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                            	赠送前身上金币
                        </th>
                        <th data-options="field:'addgoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                           	 	赠送金币
                        </th>
                        <th data-options="field:'allGoldDouble',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.numberFormatter(value)}">
                            	赠送后身上金币
                        </th>
                        <th data-options="field:'masterName',align:'center',fixed:false,width:30">操作管理员</th>
                        <th data-options="field:'clientip',align:'center',fixed:false,width:30"> 赠送地址</th>

                    </tr>
                    </thead>
                </table>

            </div>
        </div>
    </div>

</div>


<!-- 弹出页面 -->
<div id="dialog" class="easyui-dialog" closed="true"></div>


<script type="text/javascript">

    $(function ($) {
        $('#gameId').numberbox('textbox').attr('maxlength', 9);
        //初始化日期
        $('#collectdateStartStr').datetimebox('setValue', $.dateFormatter(getDate()));
        $('#collectdateEndStr').datetimebox('setValue', $.dateFormatter(new Date()));

        function getDate() {
            var date = new Date(); //当前日期
            var newDate = new Date();
            newDate.setDate(date.getDate() - 7);
            var time = newDate.getFullYear() + "-" + (newDate.getMonth() + 1) + "-" + newDate.getDate() + " " + newDate.getHours() + ":" + newDate.getMinutes() + ":" + newDate.getSeconds();
            return time;
        }

        //初始化查询
        search();

        //查询
        $("#search").unbind();
        $("#search").click(function () {
            search();
        });

        //点击重置
        $("#resetButton").unbind();
        $("#resetButton").bind("click", function () {
            $('#search-form').form('reset');
            search();
        });

        //收缩面板
        $('.panel-toggle').unbind();
        $('.panel-toggle').bind("click", function () {
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
    function search() {
        var obj = $("#search-form").serializeObject();
        $('#datagrid1').datagrid({
			url:'${pageContext.request.contextPath}/account/givegold/queryGivegoldWithPage',
			pageNumber:1,
			queryParams : {
				gameId: obj.gameId,
                collectdateEndStr: obj.collectdateEndStr,
                collectdateStartStr: obj.collectdateStartStr
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

