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
                    <span class="heading-font1">编辑广告信息</span>
                </div>
                <div class="panel-body">
                    <form id="taskedit-form" method="post" enctype="multipart/form-data" data-options="novalidate:true">
                        <input id="id" name="id" type="hidden" class="easyui-textbox"/>
                        <input id="resourceUrl" name="resourceUrl" type="hidden" class="easyui-textbox"
                               value="advert"/>
                        <input id="field5" name="field5" type="hidden" class="easyui-textbox"/>
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
                            <tr>
                                <td class="form-label">
                                    <label for="remark">广告位描述：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="remark" name="remark" class="easyui-textbox"
                                           data-options="required: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="sortid">广告位排序：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="sortid" name="sortid" class="easyui-numberbox"
                                           data-options="required: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="platformtype1">平台类型：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="platformtype1" name="platformtype" class="easyui-combobox"
                                           editable="false"
                                           data-options="valueField:'id',textField:'text',required: true,
											   		data:[
											   			{id: 1,text: '三端'}
<%--											   			,{id: 2,text: 'LUA'},--%>
<%--											   			{id: 3,text: 'U3D'},{id: 4,text: 'H5'}--%>
											   		]" value="1"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="type1">广告位类型：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="type1" name="type" class="easyui-combobox" editable="false"
                                           data-options="valueField:'id',textField:'text',required: true,disabled: true,
											   		data:[
											   			{id: 0,text: '首页轮播广告'},{id: 1,text: '新闻公告广告'},
											   			{id: 2,text: '联系我们广告'},{id: 3,text: 'H5竖版弹出广告'},
											   			{id: 4,text: '手机横版弹出广告'},{id: 5,text: '游戏下载广告'},
											   			{id: 6,text: '活动公告'}
											   		]" value="0"/>
                                </td>
                            </tr>
                            <tr id="lku">
                                <td class="form-label">
                                    <label for="linkurl1">广告位链接：</label>
                                </td>
                                <td id="lk1" class="form-editor">
                                    <input id="linkurl1" name="linkurl1" class="easyui-textbox"
                                           data-options="required: false"/>
                                </td>
                                <td id="lk2" class="form-editor">
                                    <input id="linkurl2" name="linkurl2" class="easyui-combobox" editable="false"
                                           data-options="valueField:'id',textField:'text',required: false,
											   		data:[
											   			{id: 'ad_to_createroom_action',text: '游戏房间界面'},
											   			{id: 'ad_to_video_action',text: '战绩回放界面'},
											   			{id: 'ad_to_identify_action',text: '实名认证界面'},
											   			{id: 'ad_to_shop_action',text: '商城界面'},
											   			{id: 'ad_to_spread_action',text: '推广界面'}
											   		]" value="ad_to_createroom_action"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="image">广告位图片：</label>
                                </td>

                                <td id="td1" class="form-editor">
                                    <input id="resourcePath" name="resourceurl" class="easyui-textbox"/>
                                    <a id="showImg" target="_blank">查看</a> | <a id="deleteImg"
                                                                                href="javascript:;">删除</a>
                                </td>

                                <td id="td2" class="form-editor" style="display: none">
                                    <input id="image" name="image" class="easyui-filebox"
                                           data-options="buttonText: '选择图片',accept: 'image/gif,image/jpeg,image/png'"/>
                                    <button type="button" class="btn btn-primary" id="upload" name="upload"
                                            onclick="uploadMethod()">
                                        <span aria-hidden="true">上传</span>
                                    </button>
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

    //加载参数
    var param = $.getOpenDialogParam("dialog");
    //console.log(param);

    //初始化
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
                queryAdsById();
            },
            error: function (resp) {
                $.messager.alert('警告', "没有查到上传文件的路径");
            }
        });
    });

    //ajax 加载form表单数据
    function queryAdsById(){
        $.ajax({
            url: "${pageContext.request.contextPath}/website/advert/queryAdsById",
            type: 'GET',
            dataType: 'json',
            data: {id: param.id},
            success: function (resp) {
                //console.log(resp);
                if (resp.success) {
                    var obj = resp.data;
                    //console.log(obj);
                    if (obj.type == 3) {
                        $("#lk2").show();
                        $("#lk1").hide();
                        obj.linkurl2 = obj.linkurl;

                    } else {
                        $("#lk1").show();
                        $("#lk2").hide();
                        obj.linkurl1 = obj.linkurl;
                    }
                    //执行成功
                    $('#taskedit-form').form("load", resp.data);

                    <%--$("#showImg").attr("href", "${pageContext.request.contextPath}/static/Upload" + resp.data.resourceurl)--%>
                    $("#showImg").attr("href", $("#field5").val() + resp.data.resourceurl)

                } else {
                    $.messager.alert('警告', '根据id查询失败');
                }
            }
        });
    }


    //上传方法
    function uploadMethod() {
        //获取表单所有name数据
        var formData = new FormData(document.getElementById("taskedit-form"));
        //验证文件是否为空
        // console.log("formData.resourceUrl:" + formData.get("resourceUrl"))
        // console.log("formData.image:" + formData.get("image").size)
        /*if (0 == formData.get("image").size) {
            $.messager.alert('警告', '请选择文件');
            return;
        }*/
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
                    $.messager.alert('提示', "上传文件成功");
                    $("#td2").hide();//隐藏
                    $("#td1").show();//显示
                    $("#resourcePath").textbox("setValue", resp.data.resourceurl);//属性赋值
                    $('#resourcePath').textbox({prompt: "${pageContext.request.contextPath}/static/Upload" + resp.data.resourceurl})//将值显示在文本框
                    <%--$("#showImg").attr("href", "${pageContext.request.contextPath}/static/Upload" + resp.data.resourceurl)//给该ID赋值--%>
                    $("#showImg").attr("href", $("#field5").val() + resp.data.resourceurl)//给该ID赋值
                    <%--$('#image').filebox({prompt: "${pageContext.request.contextPath}/static/Upload" + resp.data.resourceurl})--%>
                }
            },

            error: function (resp) {
                $.messager.alert('警告', resp.lastError);
            }
        });
    };

    //删除
    $("#deleteImg").unbind();
    $("#deleteImg").bind("click", function () {
		//清空input
		$("#resourcePath").textbox('setValue', '');
        $("#td1").hide();
        $("#td2").show();
    });

    //选择类型
    $("#type1").combobox({
        onSelect: function (value) {
            //console.log(value);
            if (value.id == 6) {
                $("#act").show();
            } else {
                $("#act").hide();
                //$("#lku").show();
                //|| value.id == 4
                if (value.id == 3) {
                    $("#lk1").hide();
                    $("#lk2").show();
                } else {
                    $("#lk2").hide();
                    $("#lk1").show();
                }
            }
        }
    });

    //选择类型
    /*$("#activity").combobox({
        onSelect : function(value){
            //console.log(value);
            if(value.id == 0){
                $("#lku").show();
            }else{
                $("#lku").hide();
            }
        }
    });*/

    //保存
    $("#save").unbind();
    $("#save").bind("click", function () {

        // 启用验证
        var isValid = $('#taskedit-form').form('enableValidation').form('validate');
        if (!isValid) {
            $.messager.alert('警告', '请先完善必输项');
            return;
        }

        // 所有字段有效
        var obj = $("#taskedit-form").serializeObject();
		if (obj.resourceurl == "") {
			$.messager.alert('警告', '请上传图片');
			return;
		}
        //console.log(obj);


        //提交到后台
        $('#taskedit-form').form('submit', {
            url: "${pageContext.request.contextPath}/website/advert/saveAds",
            onSubmit: function (param) {
                var t = $("#type1").combobox('getValue');
                if (t == 3) {
                    param.linkurl = obj.linkurl2
                } else {
                    param.linkurl = obj.linkurl1
                }
            },
            success: function (resp) {
                //console.log(resp);
                var o = JSON.parse(resp);
                if (o.success) {
                    //提示操作成功
                    $.messager.alert({
                        title: '提示',
                        msg: '修改广告信息成功',
                        icon: 'info',
                        //width: 300,
                        //top:200 , //与上边距的距离
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

    //重置
    $("#resetForm").unbind();
    $("#resetForm").bind("click", function () {
        //alert("重置");
        $('#useradd-form').form('clear');
    });

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