<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看亲友</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">亲友信息</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">

                    <table id="datagrid2" class="easyui-datagrid" title="亲友信息"

                           data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							fit:false,
							url: '${pageContext.request.contextPath}/ffar/manage/queryGroupMemberWithPage?groupid='+${groupid},
							method: 'GET',
							idField: 'groupid',
							rownumbers: false,
							pagination: true,
							autoRowHeight: false,
							pageSize: 10,
							pageNumber: 1,
							pageList: [10,20,30,40,50,100,200,500,1000]
							">

                        <thead>
                        <tr>
                            <%--<th data-options="field:'ck',checkbox:true"></th>--%>
                            <th data-options="field:'groupid',align:'center',fixed:false,hidden:true">groupid</th>
                            <th data-options="field:'userid',align:'center',fixed:false,width:30">用户标识</th>
                            <th data-options="field:'gameid',align:'center',fixed:false,width:30">玩家ID</th>
                            <th data-options="field:'nickname',align:'center',fixed:false,width:30">昵称</th>
                            <th data-options="field:'battlecount',align:'center',fixed:false,width:30">约战次数</th>
                            <th data-options="field:'joindatetime',align:'center',fixed:false,width:60,formatter:function(value,row,index){return $.dateFormatter(value)}">加入时间</th>

                        </tr>
                        </thead>
                    </table>

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
    </div>
</div>



<script type="text/javascript">

    //加载参数
    var param = $.getOpenDialogParam("dialog");
    //console.log(param);

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