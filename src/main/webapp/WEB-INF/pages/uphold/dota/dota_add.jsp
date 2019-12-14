<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新增</title>
</head>
<body>

<div id="caseinfo-panel" class="container-fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="heading-font1">新增道具信息</span>
                </div>
                <div class="panel-body">
                    <form id="taskadd-form" method="post" enctype="multipart/form-data" data-options="novalidate:true">
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
                            <input id="resourceUrl" name="resourceUrl" type="hidden" class="easyui-textbox"
                                   value="dota"/>
                            <input id="field5" name="field5" type="hidden" class="easyui-textbox"/>
                            <tr>
                                <td class="form-label">
                                    <label for="name">道具名称：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="name" name="name" class="easyui-textbox"
                                           data-options="required: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="exchangetype">购买类型：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="exchangetype" name="exchangetype" class="easyui-combobox"
                                           data-options="valueField:'id',textField:'text',required: true,editable:false,panelHeight:'auto',
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
                                    <input id="nullity" name="nullity" class="easyui-combobox" data-options="valueField:'id',textField:'text',required: true,editable:false,panelHeight:'auto',
											data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]" value="0"/>
                                </td>
                            </tr>

                            <tr>
                                <td class="form-label">
                                    <label for="image">道具图片：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="image" name="image" class="easyui-filebox"
                                           data-options="required: true,buttonText: '选择图片',accept: 'image/gif,image/jpg,image/png'"/>
                                    <%--<input id="image" name="image" type="file"/>--%>
                                    <input id="imgUrl" name="imgUrl" type="hidden" data-options="required: true"/>
                                    <button type="button" class="btn btn-primary" id="upload" name="upload"
                                            onclick="uploadMethod()">
                                        <span aria-hidden="true">上传</span>
                                    </button>
                                    <a id="searchImage" target="_blank">查看</a>
                                    <span style="color: red">[大小：不大于2M] </span>
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


<script type="text/javascript">

    //初始化
    $(function () {
        // alert("初始化按钮")
        $("#searchImage").hide();//隐藏
        // $("#deleteImage").hide();//隐藏

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
            },
            error: function (resp) {
                $.messager.alert('警告', "没有查到上传文件的路径");
            }
        });
    });

    //上传方法
    function uploadMethod() {
        //获取表单所有name数据
        var formData = new FormData(document.getElementById("taskadd-form"));

        //验证文件是否为空
        // console.log("formData.resourceUrl:" + formData.get("resourceUrl"))
        // console.log("formData.image:" + formData.get("image").size)
        if (0 == formData.get("image").size) {
            $.messager.alert('警告', '请选择文件');
            return;
        }

        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/uploadDownload/uploadImage",
            data: formData,
            dataType: 'JSON',
            cache: false,
            processData: false,
            contentType: false,
            // async: false,
            //crossDomain: true,
            success: function (resp) {
                debugger
                console.info("resp175:"+resp.lastError)
                console.info("resp176:"+resp.success)
                if (resp.success == false) {
                    $.messager.alert('警告', resp.lastError);
                } else {
                    $.messager.alert('提示', '上传文件成功');
                    $("#imgUrl").val(resp.data.imgUrl);//属性赋值
                    $("#upload").hide();//隐藏
                    $("#searchImage").show();//显示
                }
            },
            error: function (resp) {
                // debugger
                console.info("resp188:"+resp)
                $.messager.alert('警告', resp.lastError);
            }
        });
        /*$('#taskadd-form').form('submit', {
            url: "${pageContext.request.contextPath}/uploadDownload/uploadImage",
            async: false,
            onSubmit: function () {

            },
            success: function (data) {
                var o = JSON.parse(data);
                console.log(o.success);
                if (o.success == true) {
                    //提示操作成功
                    $.messager.alert({
                        title: '提示',
                        msg: '上传文件成功',
                    });
                } else {
                    $.messager.alert('警告', "1321");
                }
            }
        });*/
    };

    //查看图片
    $("#searchImage").unbind();
    $("#searchImage").bind("click", function () {
        // alert($("#imgUrl").val());
        <%--$("#searchImage").attr("href", "${pageContext.request.contextPath}/static/Upload" + $("#imgUrl").val())--%>
        $("#searchImage").attr("href", $("#field5").val() + $("#imgUrl").val())
    });

    //保存
    $("#save").unbind();
    $("#save").bind("click", function () {
        // 启用验证
        var isValid = $('#taskadd-form').form('enableValidation').form('validate');
        if (!isValid) {
            $.messager.alert('警告', '请先完善必输项');
            return;
        }
        // 所有字段有效
        var obj = $("#taskadd-form").serializeObject();
        if (obj.imgUrl == "") {
            $.messager.alert('警告', '请上传图片');
            return;
        }
        console.log("保存的时候：" + $("#imgUrl").val())
        console.log(obj);
        //提交到后台
        $('#taskadd-form').form('submit', {
            url: "${pageContext.request.contextPath}/uphold/dota/saveOrUpdateDota",
            success: function (data) {
                console.log(data);
                var o = JSON.parse(data);
                if (o.success) {
                    //提示操作成功
                    $.messager.alert({
                        title: '提示',
                        msg: '新增道具信息成功',
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

    });

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
        //清除之前勾选的行
        $('#datagrid1').datagrid('clearChecked');
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