<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="heading-font1">编辑道具配置</span>
                </div>
                <div class="panel-body">
                    <form id="taskedit-form" method="post" enctype="multipart/form-data" data-options="novalidate:true">
                        <input id="id" name="id" type="hidden" class="easyui-textbox"/>
                        <input id="resourceUrl" name="resourceUrl" type="hidden" class="easyui-textbox"
                               value="dota"/>
                        <input id="field5" name="field5" type="hidden" class="easyui-textbox"/>
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
                            <tr>
                                <td class="form-label">
                                    <label for="name">道具名称：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="name" name="name" class="easyui-textbox"
                                           data-options="required: true,disabled: true" value="0"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="exchangetype">购买类型：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="exchangetype" name="exchangetype" class="easyui-combobox"
                                           data-options="valueField:'id',textField:'text',required: true,disabled: true,
											   	data:[{id: '0',text: '钻石购买'},{id: '1',text: '游戏币购买'}]" value="1"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="exchangeratio">购买价格：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="exchangeratio" name="exchangeratio" class="easyui-numberbox"
                                           data-options="required: true" value="0"/>
                                    <font style="color: red">钻石购买表示1钻石买多少个道具，游戏币购买表示1道具多少游戏币</font>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="buyresultsgold">购买赠送金币：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="buyresultsgold" name="buyresultsgold" class="easyui-numberbox"
                                           data-options="required: true" value="0"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="useresultsgold">使用赠送金币：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="useresultsgold" name="useresultsgold" class="easyui-numberbox"
                                           data-options="required: true" value="0"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="sortid">道具排序：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="sortid" name="sortid" class="easyui-numberbox"
                                           data-options="required: true" value="0"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="nullity">道具状态：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="nullity" name="nullity" class="easyui-combobox" data-options="valueField:'id',textField:'text',required: true,
											data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="image">道具图片：</label>
                                </td>
                                <td id="td1" class="form-editor">
                                    <input id="imgUrl" name="imgUrl" class="easyui-textbox" editable="false"/>
                                    <a id="showImg" target="_blank">查看</a> | <a id="deleteImg"
                                                                                href="javascript:;">删除</a>
                                </td>
                                <td id="td2" class="form-editor" style="display: none">
                                    <input id="image" name="image" class="easyui-filebox"
                                           data-options="buttonText: '选择图片',accept: 'image/gif,image/jpg,image/png'"/>
                                    <button type="button" class="btn btn-primary" id="upload" name="upload"
                                            onclick="uploadMethod()">
                                        <span aria-hidden="true">上传</span>
                                    </button>
                                    <span style="color: red">[大小：不大于2M] </span>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="regulationsinfo">使用说明：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="regulationsinfo" name="regulationsinfo" class="easyui-textbox"
                                           style="width: 80%;height:50px" data-options="required: true,multiline:true"/>
                                    <br/>
                                    <span style="color: red">使用说明字符长度尽量50字以内</span>
                                </td>
                            </tr>
                        </table>
                        <span style="color: red">注意：修改成功后需重启游戏服务端才可生效</span>
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

<script type="text/javascript">
    //上传方法
    function uploadMethod() {
        //获取表单所有name数据
        var formData = new FormData(document.getElementById("taskedit-form"));
        //验证文件是否为空
        console.log("formData.resourceUrl:" + formData.get("resourceUrl"))
        console.log("formData.image:" + formData.get("image").size)
        if (0 == formData.get("image").size) {
            $.messager.alert('警告', '请选择文件');
            return;
        }
        $.ajax({
            method: "POST",
            url: "${pageContext.request.contextPath}/uploadDownload/uploadImage",
            data: formData,
            processData: false,
            contentType: false,
            crossDomain: true,
            success: function (resp) {
                if (resp.success == false) {
                    $.messager.alert('警告', resp.lastError);
                } else {
                    $.messager.alert('提示', "文件上传成功");
                    $("#td2").hide();//隐藏
                    $("#td1").show();//显示
                    $("#imgUrl").textbox("setValue", resp.data.imgUrl);//属性赋值
                    <%--$('#imgUrl').textbox({prompt: "${pageContext.request.contextPath}/static/Upload" + resp.data.imgUrl})//将值显示在文本框--%>
                    <%--$("#showImg").attr("href", "${pageContext.request.contextPath}/static/Upload" + resp.data.imgUrl)//给该ID赋值--%>
                    $('#imgUrl').textbox({prompt: resp.data.imgUrl})//将值显示在文本框
                    $("#showImg").attr("href", $("#field5").val() + resp.data.imgUrl)//给该ID赋值
                    <%--$('#image').filebox({prompt: "${pageContext.request.contextPath}/static/Upload" + resp.data.imgUrl})--%>
                }
            },

            error: function (resp) {
                $.messager.alert('警告', resp.lastError);
            }
        });
    };

    //加载参数
    var param = $.getOpenDialogParam("dialog");

    //删除
    $("#deleteImg").unbind();
    $("#deleteImg").bind("click", function () {
        //获取路径地址
        /*var value = $("#imgUrl").textbox('getValue');
        alert("183_imgUrl:" + value)
        $.ajax({
            url: "
        ${pageContext.request.contextPath}/uploadDownload/deleteFile",
            type: 'GET',
            contentType: "application/json;charset=utf-8",
            // dataType: 'json',
            data: {imgUrl: value},
            success: function (resp) {
                if (resp.success) {
                    //执行成功
                    $.messager.alert('提示', resp.lastError);
                } else {
                    $.messager.alert('警告', resp.lastError);
                }
            }
        });*/
        //清空input
        $("#imgUrl").textbox('setValue', '');
        $("#td1").hide();
        $("#td2").show();
    });

    //初始化加载
    $(function () {
        //查询图片上传前缀路径
        var configKey = "WebSiteConfig";
        $.ajax({
            method: "GET",
            url: "${pageContext.request.contextPath}/website/stand/queryConfigInfoByConfigKey?configKey=" + configKey,
            processData: false,
            contentType: false,
            crossDomain: true,
            success: function (resp) {
                $("#field5").val(resp.data.field5);//赋值
                queryDotaById(resp.data.field5);
            },
            error: function (resp) {
                $.messager.alert('警告', "没有查到上传文件的路径");
            }
        });
    });

    //ajax 加载form表单数据
    function queryDotaById(field5) {
        $.ajax({
            url: "${pageContext.request.contextPath}/uphold/dota/queryDotaById",
            type: 'GET',
            dataType: 'json',
            data: {id: param.id, exchangetype: param.exchangetype},
            success: function (resp) {
                // alert(resp.data.imgUrl);
                if (resp.success) {
                    //执行成功
                    $('#taskedit-form').form("load", {
                        id: resp.data.id,
                        name: resp.data.name,
                        exchangetype: resp.data.exchangetype,
                        exchangeratio: resp.data.exchangeratio,
                        buyresultsgold: resp.data.buyresultsgold,
                        useresultsgold: resp.data.useresultsgold,
                        regulationsinfo: resp.data.regulationsinfo,
                        nullity: resp.data.nullity,
                        sortid: resp.data.sortid,
                        imgUrl: resp.data.imgUrl
                    });
                    <%--$("#showImg").attr("href", "${pageContext.request.contextPath}/static/Upload" + resp.data.imgUrl)//给该ID赋值--%>
                    $("#showImg").attr("href", field5 + resp.data.imgUrl)
                    // $("#showImg").attr("href", $("#field5").val() + resp.data.imgUrl)
                } else {
                    $.messager.alert('警告', '根据id查询失败');
                }
            }
        });
    }


    //保存
    $("#save").unbind();
    $("#save").bind("click", function () {
        saveUpdateDelete()
    });

    function saveUpdateDelete() {
        // 启用验证
        var isValid = $('#taskedit-form').form('enableValidation').form('validate');
        if (!isValid) {
            $.messager.alert('警告', '请先完善必输项');
            return;
        }

        // 所有字段有效
        var obj = $("#taskedit-form").serializeObject();
        if (obj.imgUrl == "") {
            $.messager.alert('警告', '请上传图片');
            return;
        }

        // 所有字段有效
        var obj = $("#taskedit-form").serializeObject();
        //提交到后台
        $('#taskedit-form').form('submit', {
            url: "${pageContext.request.contextPath}/uphold/dota/saveOrUpdateDota",
            success: function (resp) {
                console.log(resp);
                var o = JSON.parse(resp);
                if (o.success) {
                    //提示操作成功
                    $.messager.alert({
                        title: '提示',
                        msg: '修改道具信息成功',
                        icon: 'info',
                        fn: function () {
                            // 关闭窗口
                            $('#dialog').dialog("close");
                            // 刷新列表
                            //console.log ( $('#datagrid1') );
                            $('#datagrid1').datagrid('load');
                            //清除之前勾选的行
                            $('#datagrid1').datagrid('clearChecked');
                        }
                    });
                } else {
                    $.messager.alert('警告', o.lastError);
                }
            }
        });
    }

    function reg(value) {
        var reg = /^[0-9]+(\.)+[0-9]+(\.)+[0-9]+(\.)+[0-9]$/;
        var fg = reg.test(value);
        //console.log(fg);
        return fg;
    }

    //取消
    $("#cancel").unbind();
    $("#cancel").bind("click", function () {
        //alert("取消");
        $('#dialog').dialog("close");
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

</script>

</body>
</html>