<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>充值金币</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form id="accountGiveGold-form" method="post" data-options="novalidate:true">
                        <input id="deposiWithdrawalState" name="deposiWithdrawalState" type="hidden"/>
                        <input id="userId" name="userId" type="hidden" class="easyui-numberbox"/>
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
                            <tr>
                                <td class="form-label">
                                    <label for="accounts2">用户名称：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="accounts2" name="accounts" class="easyui-textbox" readonly
                                           data-options="required:true, disabled: false"/>
                                    <input class="layui-btn layui-btn-sm layui-btn-normal" type="button"
                                           onclick="userList()" value="选择用户" />
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="nickName2">用户昵称：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="nickName2" name="nickName" class="easyui-textbox" readonly
                                           data-options="required:true,disabled: false"/>
                                </td>
                            </tr>
                            <%--<tr>
                                <td class="form-label">
                                    <label for="gameId2">玩家ID：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="gameId2" name="gameId" class="easyui-numberbox" readonly
                                           data-options="required:true,disabled: false"/>
                                </td>
                            </tr>--%>
                            <tr>
                                <td class="form-label">
                                    <label for="giveGold1">金币数：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="giveGold1" name="giveGold" class="easyui-numberbox"
                                           data-options="required:true,validType:'length[1,8]'"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="reason">备注：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="reason" name="reason" class="easyui-textbox" style="height:50px"
                                           data-options="required: true,multiline:true"/>
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
<div id="dialog1" style="overflow-x: hidden" class="easyui-dialog" closed="true"></div>

<script type="text/javascript">

    //加载参数
    var param = $.getOpenDialogParam("dialog");
    // console.log(param.deposiWithdrawalState);

    //保存
    $("#save").unbind();
    $("#save").bind("click", function () {
        // 启用验证
        var isValid = $('#accountGiveGold-form').form('enableValidation').form('validate');
        if (!isValid) {
            $.messager.alert('警告', '请先完善必输项');
            return;
        }
        // 所有字段有效
        var obj = $("#accountGiveGold-form").serializeObject();

        var data = {
            // accounts: obj.accounts,
            // nickName: obj.nickName,
            userId: obj.userId,
            giveGold: obj.giveGold,
            reason: obj.reason,
            // gameId: obj.gameId,
            deposiWithdrawalState: param.deposiWithdrawalState
        };
        console.log(obj);

        //提交到后台
        $.ajax({
            url: "${pageContext.request.contextPath}/account/user/giveGoldAccountUser",
            type: 'PUT',
            dataType: 'json',
            data: data,
            success: function (resp) {
                //{success: true, errors: Array(0), data: null, lastError: null}
                //console.log(resp);
                if (resp.success) {
                    $.messager.alert('提示', '操作金币成功');
                    //执行成功
                    // 关闭窗口
                    $('#dialog').dialog("close");

                    // 刷新列表
                    //console.log ( $('#datagrid1') );
                    $('#datagrid1').datagrid('load');

                } else {
                    $.messager.alert('警告', resp.lastError);
                }
            }
        });

    });

    //用户列表（存提）
    function userList() {
        $('#dialog1').dialog({
            title: '用户列表',
            href: '${pageContext.request.contextPath}/invest/depositWithdrawals/toUserList',
            width: 1000,
            fitColumns: false,
            height: 600,
            closed: false,
            cache: false,
            // modal: true,
            collapsible: true,
            maximizable: false,
            resizable: true,
            shadow: true,
            content: ''
        }).window('center');
    };

    //取消
    $(".btn-default").unbind();
    $(".btn-default").bind("click", function () {
        //alert("取消");
        $('#dialog').dialog("close");
    });

</script>

</body>
</html>