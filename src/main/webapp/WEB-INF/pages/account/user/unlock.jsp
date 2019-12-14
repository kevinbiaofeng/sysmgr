<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>解锁</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-body">

                    <div class="easyui-tabs">
                        <div title="请输入密码" style="padding:10px">

                            <form id="accountedit-form" method="post" data-options="novalidate:true" >
                                <input id="userId" name="userId" type="hidden" class="easyui-textbox" />
                                <table class="form-table" border="0" cellpadding="1" cellspacing="2" style="width: 100%;">
                                    <tr>
                                        <td class="form-label">
                                            <label for="nickName">用户昵称：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="nickName" name="nickName" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                    </tr>

                                </table>

                            </form>

                            <div class="opt-button-float">
                            	<button type="button" class="btn btn-success" id="unlock" name="unlock">
                                     <span class="glyphicon glyphicon-ok" aria-hidden="true">解锁</span>
                                </button>
                                <button type="button" class="btn btn-primary" id="save" name="save" disabled="disabled">
                                     <span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
                                </button>
                                <button type="button" class="btn btn-default" id="cancel" name="cancel">
				                    <span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
				                </button>
                            </div>

                        </div>
                    </div>

                </div>
            </div>

            

        </div>
    </div>
</div>

<script type="text/javascript">

    //加载参数
    var param = $.getOpenDialogParam("dialog");
    console.log(param);

    //取消
    $(".btn-default").unbind();
    $(".btn-default").bind("click",function(){
        //alert("取消");
        $('#dialog').dialog("close");
    });

</script>

</body>
</html>