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
                <div class="panel-body">

                    <div class="easyui-tabs">
                        <div title="用户信息" style="padding:10px">

                            <form id="accountedit-form" method="post" data-options="novalidate:true" >
                                <input id="userId" name="userId" type="hidden" class="easyui-textbox" />
                                <table class="form-table" border="0" cellpadding="1" cellspacing="2" style="width: 100%;">

									<tr>
                                        <td class="form-label">
                                            <label for="imgFace">用户头像：</label>
                                        </td>
                                        <td class="form-editor">
                                            <img id="imgFace" style="border-width: 0px; width: 103px; height: 96px;" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="form-label">
                                            <label for="accounts1">用户账号：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="accounts1" name="accounts1" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="nickName1">用户昵称：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="nickName1" name="nickName1" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                        <%--<td class="form-label">
                                            <label for="gender">用户性别：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="gender" name="gender" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>--%>
                                        <td class="form-label">
                                            <label for="gameId">玩家ID：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="gameId" name="gameId" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                    </tr>


                                    <tr>
                                        <td class="form-label">
                                            <label for="insurePass">银行密码：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="insurePass" name="insurePass" class="easyui-passwordbox" data-options="required: false"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="nullity">用户状态：</label>
                                        </td>
                                        <td class="form-editor">
                                                <input id="nullity" name="nullity" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: 0,text: '启用'},{id: 1,text: '禁用'}]" />
                                        </td>
                                        <td class="form-label">
                                            <label for="moorMachine">锁定客户端：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="moorMachine" name="moorMachine" class="easyui-combobox" data-options="panelHeight:'auto', valueField:'id',textField:'text',data:[{id: 0,text: '不锁定'},{id: 1,text: '锁定'}]" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="form-label">
                                            <label for="compellation">真实姓名：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="compellation" name="compellation" class="easyui-textbox" data-options="required: false,disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="passPortId">身份证号：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="passPortId" name="passPortId" class="easyui-textbox" data-options="required: false,disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="underWrite">个性签名：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="underWrite" name="underWrite" class="easyui-textbox" data-options="required: false"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="form-label">
                                            <label for="webLogonTimes">网站登陆次数：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="webLogonTimes" name="webLogonTimes" class="easyui-textbox" data-options="disabled:true"/>次
                                        </td>
                                        <td class="form-label">
                                            <label for="gameLogonTimes">大厅登录次数：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="gameLogonTimes" name="gameLogonTimes" class="easyui-textbox" data-options="disabled:true"/>次
                                        </td>
                                        <td class="form-label">
                                            <label for="onLineTimeCount">在线时长共计：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="onLineTimeCount" name="onLineTimeCount" class="easyui-textbox" data-options="disabled:true"/>秒
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="form-label">
                                            <label for="playTimeCount">游戏时长共计：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="playTimeCount" name="playTimeCount" class="easyui-textbox" data-options="disabled:true"/>秒
                                        </td>
                                        <td class="form-label">
                                            <label for="lastLogonDate">最后登录时间：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="lastLogonDate" name="lastLogonDate" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="lastLogonIp">最后登录地址：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="lastLogonIp" name="lastLogonIp" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="form-label">
                                            <label for="lastLogonMachine">最后登录机器：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="lastLogonMachine" name="lastLogonMachine" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="registerDate">用户注册时间：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="registerDate" name="registerDate" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="registerIp">用户注册地址：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="registerIp" name="registerIp" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="form-label">
                                            <label for="registerMachine">用户注册机器：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="registerMachine" name="registerMachine" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="registerOrigin">用户注册来源：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="registerOrigin" name="registerOrigin" class="easyui-textbox" data-options="disabled:true"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="isTransfer">个人转账权限：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="isTransfer" name="isTransfer" class="easyui-combobox"
                                                   data-options="panelHeight:'auto', disabled:true, valueField:'id',textField:'text',data:[{id: 0,text: '启用'},{id: 64,text: '禁用'}]" />
                                        </td>
                                    </tr>

                                </table>

                            </form>

                            <div class="opt-button-float">
                            	<button type="button" class="btn btn-success" id="unlock" name="unlock">
                                     <span class="glyphicon glyphicon-ok" aria-hidden="true">解锁</span>
                                </button>
                                <button type="button" class="btn btn-primary" id="save" name="save">
                                     <span class="glyphicon glyphicon-ok" aria-hidden="true">保存</span>
                                </button>
                                <button type="button" class="btn btn-default" id="cancel" name="cancel">
				                    <span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
				                </button>
                            </div>

                        </div>
                        <div title="金币流水" style="padding:10px">

                            <form id="searchSerial-form" method="post">
                                <table class="form-table" border="0" cellpadding="1" cellspacing="2">
                                    <tr>
                                        <td class="form-label">
                                            <label for="collectdateStartStr">日期查询：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="collectdateStartStr" name="collectdateStartStr" class="easyui-datetimebox" editable="false"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="collectdateEndStr">至：</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="collectdateEndStr" name="collectdateEndStr" class="easyui-datetimebox" editable="false"/>
                                        </td>
                                        <td class="form-label">
                                            <label for="typeid">金币类型</label>
                                        </td>
                                        <td class="form-editor">
                                            <input id="typeid" name="typeid" class="easyui-combobox" editable="false"
                                                   data-options="valueField:'id',textField:'text',data:[
                                                        {id: 100,text: '全部'},
                                                        {id: 0,text: '后台赠送'},
                                                        {id: 1,text: '注册赠送'},
                                                        {id: 2,text: '主动转账'},
                                                        {id: 3,text: '接收转账'},
                                                        {id: 4,text: '购买道具'},
                                                        {id: 5,text: '兑换金币'},
                                                        {id: 6,text: '存入银行'},
                                                        {id: 7,text: '银行取出'},
                                                        {id: 8,text: '银行服务费'},
                                                        {id: 9,text: '领取返利'},
                                                        {id: 10,text: '代理赠送'},
                                                        {id: 11,text: '被代理赠送'},
                                                        {id: 12,text: '充值额外赠送'},
                                                        {id: 13,text: '每日分享'},
                                                        {id: 14,text: '签到'},
                                                        {id: 15,text: '比赛奖励'},
                                                        {id: 16,text: '绑定手机'},
                                                        ]" value="100"/>
                                        </td>
                                        <td class="button-group" colspan="2">
                                            <button type="button" class="btn btn-primary" onclick="searchSerial()">
                                                <span class="glyphicon glyphicon-search" aria-hidden="true">查询</span>
                                            </button>
                                            <%--<button type="button" class="btn btn-warning" onclick="resetSerial()">
                                                <span class="glyphicon glyphicon-repeat" aria-hidden="true">重置</span>
                                            </button>--%>
                                            <button type="button" class="btn btn-default" id="cancel" name="cancel">
							                    <span class="glyphicon glyphicon-off" aria-hidden="true">取消</span>
							                </button>
                                        </td>
                                    </tr>
                                </table>
                            </form>

                            <table id="datagrid3" class="easyui-datagrid"

                                   data-options="
                                        singleSelect: true,
                                        fitColumns: true,
                                        collapsible: true,
                                        multiSort: true,
                                        checkOnSelect: true,
                                        cache: false,
                                        fit:false,
<%--                                        url: '${pageContext.request.contextPath}/account/user/queryRecordtreasureserialWithPage?userid=' + ${userId},--%>
                                        method: 'GET',
                                        idField: 'id',
                                        pagination: true,
                                        autoRowHeight: false,
                                        pageSize: 10,
                                        pageNumber: 1,
                                        pageList: [10,20,30,40,50,100,200,500,1000]
                                        ">

                                <thead>
                                <tr>
                                    <th data-options="field:'serialnumber',align:'center',fixed:false,width:40">流水号</th>
                                    <th data-options="field:'collectdate',align:'center',fixed:false,width:40,formatter:function(value,row,index){return $.dateFormatter(value)}">流水时间</th>
                                    <th data-options="field:'typeidDesc',align:'center',fixed:false,width:40">流水类型</th>
                                    <th data-options="field:'curScoreDesc',align:'center',fixed:false,width:40">操作前携带金币</th>
                                    <th data-options="field:'curinSureScoreDesc',align:'center',fixed:false,width:40">操作前银行金币</th>
                                    <th data-options="field:'changeScoreDesc',align:'center',width:40,fixed:false">金币变化</th>
                                    <th data-options="field:'clientip',align:'center',width:40,fixed:false">操作地址</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>

                </div>
            </div>



        </div>
    </div>
</div>

<div id="unlock-dialog" class="easyui-dialog" closed="true">
	<div style="width: 100%;">
       	<input id="password" name="password" class="easyui-passwordbox" data-options="required:true" style="width: 100%;height: 50px;"/>
    </div>
</div>

<script type="text/javascript">

    //加载参数
    var param = $.getOpenDialogParam("dialog");

    //解锁
    $("#unlock").click(function(){
		$('#unlock-dialog').dialog({
            title: '请输入解锁密码',
            width: 400,
            height: 140,
            closed: false,
            cache: false,
            modal: true,
            collapsible: false,
            maximizable: false,
            resizable: false,
            shadow: true,
            buttons: [{
                text: '解锁',
                iconCls: 'icon-ok',
                handler: function () {
                	var pwd = $("#password").textbox('getValue');
                	if(pwd != null && pwd != ""){
                		$.get("${pageContext.request.contextPath}/account/user/unlockAccountInfo?userId= " + param.userId + "&pwd=" + pwd, function(data){
                			$("#password").passwordbox("setValue","");
                		    if(data.success){   //密码正确
                		    	$('#nickName1').textbox('setValue', data.data.nickName);
                		    	$('#accounts1').textbox('setValue', data.data.accounts);
                		    	$('#compellation').textbox('setValue', data.data.compellation);
                		    	$('#passPortId').textbox('setValue', data.data.passPortId);
                		    	$("#unlock").remove();
        	                 	$("#compellation").textbox("enable");
        	                 	$("#passPortId").textbox("enable");
        	                 	$("#save").attr("disabled", false);
        	                 	$("#unlock-dialog").dialog('close');
        	                 	$("#unlock-dialog").dialog("destroy");
                		    }else{
                		    	if(data.data == 0){
                		    		$("#unlock-dialog").dialog('close');
            	                 	$("#unlock-dialog").dialog("destroy");
            	                 	$("#unlock").remove();
            	                 	$.messager.alert('警告', data.lastError,function(){});
                		    	}else{
                		    		$.messager.alert('警告', data.lastError,function(){});
                		    	}
                		    }
               		  	});

                	}else{
                		$.messager.alert('警告','请输入密码！！！');
                	}
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $("#unlock-dialog").dialog('close');
                }
            }]
        }).window('center');
        $('#unlock-dialog').dialog('open');


    })


    /*function searchSerial(){
        var obj = $("#searchSerial-form").serializeObject();
        var data = {
            userid:param.userId,
            typeid: obj.typeid,
            collectdateStartStr: obj.collectdateStartStr,
            collectdateEndStr: obj.collectdateEndStr
        };
        $('#datagrid3').datagrid('load',data);
    };*/

    $(function(){
        searchSerial();
    });

    function searchSerial(){
        var obj = $("#searchSerial-form").serializeObject();
        $('#datagrid3').datagrid({
            url: '${pageContext.request.contextPath}/account/user/queryRecordtreasureserialWithPage',
            queryParams : {
                userid:param.userId,
                typeid: obj.typeid,
                collectdateStartStr: obj.collectdateStartStr,
                collectdateEndStr: obj.collectdateEndStr
            },
            view: myview,
            loadMsg: "数据加载中，请稍后！",
            emptyMsg: "未搜索到相关数据！"
        });
    };

    //重置
    function resetSerial(){
        $('#searchSerial-form').form('clear');
        searchSerial();
    }


    //ajax 加载form表单数据
    $.ajax({
        url: "${pageContext.request.contextPath}/account/user/queryAccountUserById",
        type: 'GET',
        dataType: 'json',
        data: {userId: param.userId},
        success: function(resp){
            if(resp.success){
                //执行成功
                $('#accountedit-form').form("load",{
                    userId: resp.data.userId,
                    nickName1: resp.data.nickName,
                    accounts1: resp.data.accounts,
                    gender: resp.data.genderDesc,
                    gameId: resp.data.gameId,
                    agentStatus: resp.data.agentStatus,
                    imgFace: resp.data.imgFace,
                    nullity: resp.data.nullity,
                    moorMachine: resp.data.moorMachine,
                    compellation: resp.data.compellation,
                    passPortId: resp.data.passPortId,
                    underWrite: resp.data.underWrite,
                    webLogonTimes: resp.data.webLogonTimes,
                    gameLogonTimes: resp.data.gameLogonTimes,
                    onLineTimeCount: resp.data.onLineTimeCount,
                    playTimeCount: resp.data.playTimeCount,
                    lastLogonDate: resp.data.lastLogonDateStr,
                    lastLogonIp: resp.data.lastLogonIp,
                    lastLogonMachine: resp.data.lastLogonMachine,
                    registerDate: resp.data.registerDateStr,
                    registerIp: resp.data.registerIp,
                    registerMachine: resp.data.registerMachine,
                    registerOrigin: resp.data.registerOriginDesc,
                    isTransfer: resp.data.userRight
                });
                $("#imgFace").attr("src",resp.data.imgFaceSrc);
                if(resp.data.unlock == 99){  //成功
                	$("#unlock").remove();
                	$("#compellation").textbox("enable");
                 	$("#passPortId").textbox("enable");
                }else if(resp.data.unlock == 0){  //锁定
                	$("#unlock").remove();
                }else{

                }
            }else{
                $.messager.alert('警告','');
            }
        }
    });

    //保存
    $("#save").unbind();
    $("#save").bind("click",function(){

        // 启用验证
        var isValid = $('#accountedit-form').form('enableValidation').form('validate');
        if(!isValid){
            $.messager.alert('警告','请先完善必输项');
            return;
        }

        // 所有字段有效
        var obj = $("#accountedit-form").serializeObject();

        var data = {
            userId: obj.userId,
            insurePass: obj.insurePass,
            nullity: obj.nullity,
            moorMachine: obj.moorMachine,
            compellation: obj.compellation,
            passPortId: obj.passPortId,
            underWrite: obj.underWrite,
            isTransfer: obj.isTransfer
        };

        //提交到后台
        $.ajax({
            url: "${pageContext.request.contextPath}/account/user/updateAccountUser",
            type: 'PUT',
            dataType: 'json',
            data: data,
            success: function(resp){
                if(resp.success){
                    // 关闭窗口
                    $('#dialog').dialog("close");

                    // 刷新列表
                    $('#datagrid1').datagrid('load');

                }else{
                    $.messager.alert('警告','保存管理员失败');
                }
            },
            view: myview,
            loadMsg: "数据加载中，请稍后！",
            emptyMsg: "未搜索到相关数据！"
        });

    });

    //取消
    $(".btn-default").unbind();
    $(".btn-default").bind("click",function(){
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

    /*function transferPermission(value,row,index){
        console.log("row.userId:"+row.userId)
        var transfer = "<input type='checkbox' id='transfer{0}' name='transfer' class='easyui-switchbutton' onText='开启' offText='禁用' />";
        var result = transfer.format(row.userId);
        return result;
    }*/

</script>

</body>
</html>