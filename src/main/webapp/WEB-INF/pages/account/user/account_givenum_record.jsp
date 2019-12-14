<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新增管理员</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">赠送靓号</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">

                    <table id="datagrid2" class="easyui-datagrid" title="用户信息" style="width:100%;height:500px"

                           data-options="
							singleSelect: false,
							fitColumns: true,
							collapsible: true,
							multiSort: true,
							checkOnSelect: true,
							cache: false,
							url: '${pageContext.request.contextPath}/account/user/queryGivenumRecordWithPage?userid=' + ${userid},
							<%--queryParams: {nullity: 0},--%>
							method: 'GET',
							<%--toolbar: '#tb',--%>
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
                            <%--<th data-options="field:'ck',checkbox:true"></th>--%>
                            <!-- <th data-options="field:'aaaa',align:'center',fixed:false,hidden:true">编号1111</th> -->
                            <th data-options="field:'collectdate',align:'center',fixed:false,width:30,formatter:function(value,row,index){return $.dateFormatter(value)}">赠送时间</th>
                            <th data-options="field:'regameid',align:'center',fixed:false,width:30">赠送靓号</th>
                            <th data-options="field:'curgameid',align:'center',fixed:false,width:50">赠送前靓号</th>
                            <th data-options="field:'masterName',align:'center',fixed:false,width:30">赠送管理员</th>
                            <th data-options="field:'clientip',align:'center',fixed:false,width:30">赠送地址</th>
                            <th data-options="field:'reason',align:'center',fixed:false,width:30">赠送原因</th>
                            <%--<th data-options="field:'option',align:'center',fixed:false,width:180,formatter:function(value,row,index){return optortion(value,row,index)}">操作</th>--%>
                        </tr>
                        </thead>
                    </table>


                </div>
            </div>

            <div class="opt-button-float">
                <%--<button type="button" class="btn btn-primary" id="save" name="save">
                    <span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
                </button>--%>
                <button type="button" class="btn btn-default" id="cancel1" name="cancel1">
                    <span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
                </button>
            </div>

        </div>
    </div>
</div>



<script type="text/javascript">

    //加载参数
    //var param1 = $.getOpenDialogParam("dialog1");
    //console.log(param1);

    //ajax 加载form表单数据
    /*$.ajax({
        url: "${pageContext.request.contextPath}/account/user/queryAccountUserById",
        type: 'GET',
        dataType: 'json',
        data: {userId: param.userId},
        success: function(resp){
            //console.log(resp);
            //console.log(resp.data.nullity);
            //console.log(resp.data.nullity == 1);
            if(resp.success){
                //执行成功

                $('#accountGiveNum-form').form("load",{
                    userId: resp.data.userId,
                    nickName: resp.data.nickName,
                    gameId: resp.data.gameId
                });

                refreshGiveNum();

            }else{
                $.messager.alert('警告','');
            }
        }
    });*/

    //刷新靓号
    /*function refreshGiveNum(){
        console.log("刷新靓号");
        $.ajax({
            url: "${pageContext.request.contextPath}/account/user/freshenGiveNum",
            type: 'GET',
            dataType: 'json',
            async : false,//使用同步的方式,true为异步方式
            success: function(resp){
                console.log(resp);
                //console.log(resp.data.nullity);
                //console.log(resp.data.nullity == 1);
                if(resp.success){
                    //执行成功
                    var num = resp.data;
                    $('#giveNum').textbox('setValue', num);
                }else{
                    $.messager.alert('警告','');
                }
            }
        });
    }*/

    //保存
    /*$("#save").unbind();
    $("#save").bind("click",function(){

        // 启用验证
        var isValid = $('#accountGiveNum-form').form('enableValidation').form('validate');
        if(!isValid){
            $.messager.alert('警告','请先完善必输项');
            return;
        }

        // 所有字段有效
        var obj = $("#accountGiveNum-form").serializeObject();

        var data = {
            userId: obj.userId,
            reason: obj.reason,
            giveNum: obj.giveNum
        };
        console.log(obj);

        //提交到后台
        $.ajax({
            url: "${pageContext.request.contextPath}/account/user/giveNumAccountUser",
            type: 'PUT',
            dataType: 'json',
            data: data,
            success: function(resp){
                //{success: true, errors: Array(0), data: null, lastError: null}
                //console.log(resp);
                if(resp.success){
                    //执行成功
                    // 关闭窗口
                    $('#dialog').dialog("close");

                    // 刷新列表
                    //console.log ( $('#datagrid1') );
                    $('#datagrid1').datagrid('load');

                }else{
                    $.messager.alert('警告','保存管理员失败');
                }
            }
        });

    });*/

    //取消
    $("#cancel1").unbind();
    $("#cancel1").bind("click",function(){
        //alert("取消");
        $('#dialog1').dialog("close");
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