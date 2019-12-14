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

                    <form id="accountGiveNum-form" method="post" data-options="novalidate:true" >
                        <input id="userId" name="userId" type="hidden" class="easyui-textbox" />
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
                            <tr>
                                <td class="form-label">
                                    <label for="nickName">用户昵称：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="nickName" name="nickName" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="gameId">玩家ID：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="gameId" name="gameId" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="giveNum">赠送靓号ID：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="giveNum" name="giveNum" class="easyui-numberbox" data-options="required:true,validType:'length[1,6]'"/>
                                    <button type="button" class="btn btn-primary" onclick="refreshGiveNum()">
                                        <span class="glyphicon glyphicon-refresh" aria-hidden="true">刷新靓号</span>
                                    </button>
                                    <button type="button" class="btn btn-primary" onclick="giveNumrecord()">
                                        <span class="glyphicon glyphicon-list-alt" aria-hidden="true">赠送记录</span>
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="reason">赠送原因：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="reason" name="reason" class="easyui-textbox" style="height:50px" data-options="required: true,multiline:true"/>
                                </td>
                            </tr>
                        </table>
                    </form>


                </div>
            </div>

            <div class="opt-button-float">
                <button type="button" class="btn btn-primary" id="save" name="save">
                    <span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
                </button>
                <button type="button" class="btn btn-default" id="cancel" name="cancel">
                    <span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
                </button>
            </div>

        </div>
    </div>
</div>

<!-- 弹出页面 -->
<div id="dialog1"  class="easyui-dialog" closed="true"></div>

<script type="text/javascript">

    //加载参数
    var param = $.getOpenDialogParam("dialog");
    console.log(param);

    //ajax 加载form表单数据
    $.ajax({
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
    });

    //刷新靓号
    function refreshGiveNum(){
        //console.log("刷新靓号");
        $.ajax({
            url: "${pageContext.request.contextPath}/account/user/freshenGiveNum",
            type: 'GET',
            dataType: 'json',
            async : false,//使用同步的方式,true为异步方式
            success: function(resp){
                //console.log(resp);
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
    }

    //保存
    $("#save").unbind();
    $("#save").bind("click",function(){

        // 启用验证
        var isValid = $('#accountGiveNum-form').form('enableValidation').form('validate');
        if(!isValid){
            $.messager.alert('警告','请先完善必输项');
            return;
        }

        // 所有字段有效
        var obj = $("#accountGiveNum-form").serializeObject();

        if(!(/^[0-9]+$/.test(obj.giveNum))){
            $.messager.alert('警告','赠送靓号ID必须是数字');
            return;
        }

        var data = {
            userId: obj.userId,
            reason: obj.reason,
            giveNum: obj.giveNum
        };
        //console.log(obj);

        //提交到后台
        $.ajax({
            url: "${pageContext.request.contextPath}/account/user/giveNumAccountUser",
            type: 'PUT',
            dataType: 'json',
            data: data,
            success: function(resp){
                //{success: true, errors: Array(0), data: null, lastError: null}
                console.log("resp:"+resp.success);
                // console.log("resp.data():"+resp.data);
                console.log("resp.errors:"+resp.errors);
                var list = resp.errors;
                var returnMsg;
                for(var i=0;i<list.length;i++){
                    returnMsg = list[i].error;
                }
                console.log("returnMsg:"+returnMsg);
                if(resp.success){
                    //执行成功
                    // 关闭窗口
                    //$('#dialog').dialog("close");

                    // 刷新列表
                    //console.log ( $('#datagrid1') );
                   // $('#datagrid1').datagrid('load');

                    // $.messager.alert('提示','赠送靓号成功');
                    $.messager.alert('提示', returnMsg);

                }else{
                    $.messager.alert('警告', returnMsg);
                }
            }
        });

    });

    //查看赠送记录
    function giveNumrecord(){
        //onsole.log(agentId);
        $('#dialog1').dialog({
            title: '查看下线',
            href: '${pageContext.request.contextPath}/account/user/toGivenumRecordPage?userid=' + param.userId,
            dialogParams: {userid : param.userId},
            width: 1000,
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
        });

        $('#dialog1').dialog('open');
    }

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