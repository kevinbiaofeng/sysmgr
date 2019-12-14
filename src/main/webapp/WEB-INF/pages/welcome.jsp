<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>欢迎页</title>
    <%@ include file="/static/pub/include.jsp" %>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/font-awesome/css/font-awesome.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/layui/css/layui.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/jqadmin.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/main.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/jqadmin/css/font/iconfont.css" type="text/css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/style.css" type="text/css"></link>
    <link rel="icon" href="${pageContext.request.contextPath}/asset/logo.png" type="image/x-icon"/></link>

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/jqadmin/js/main.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/echarts/echarts.min.js"></script>

    <script>var $ContextPath = '<c:url value="/"/>';</script>

</head>

<body>

<div id="search-panel" class="container-fluid">

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">全局统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    <form id="global-form" method="post" data-options="novalidate:true" >
                        <table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
                            <tr>
                                <td colspan="4"><h3 style="font-weight: lighter;font-size: large;color: #26d7d9">用户统计</h3></td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="totalAccount">用户总数：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalAccount" name="totalAccount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayNews">今日新增：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayNews" name="todayNews" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayActive">今日活跃：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayActive" name="todayActive" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="yesterdayNews">昨日新增：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="yesterdayNews" name="yesterdayNews" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4"><h3 style="font-weight: lighter;font-size: large;color: #26d7d9">金币统计</h3></td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="totalScore">身上金币总量：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalScore" name="totalScore" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="totalInsureScore">保险柜总量：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalInsureScore" name="totalInsureScore" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="totalAllScore">保险柜+身上总量：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalAllScore" name="totalAllScore" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayPayScore">今日充值金币：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayPayScore" name="todayPayScore" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="todayPayScoreAmount">今日充值金币金额：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayPayScoreAmount" name="todayPayScoreAmount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4"><h3 style="font-weight: lighter;font-size: large;color: #26d7d9">充值统计</h3></td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="totalAmount">总充值金额：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalAmount" name="totalAmount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayAmount">今日充值金额：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayAmount" name="todayAmount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="yesterdayAmount">昨日充值金额：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="yesterdayAmount" name="yesterdayAmount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4"><h3 style="font-weight: lighter;font-size: large;color: #26d7d9">服务费统计</h3></td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="totalRevenue">游戏总服务费：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalRevenue" name="totalRevenue" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayRevenue">今日游戏服务费：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayRevenue" name="todayRevenue" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="totalInsureRevenue">银行总服务费：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalInsureRevenue" name="totalInsureRevenue" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayInsureRevenue">今日银行服务费：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayInsureRevenue" name="todayInsureRevenue" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="totalAllRevenue">银行+游戏总服务费：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalAllRevenue" name="totalAllRevenue" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4"><h3 style="font-weight: lighter;font-size: large;color: #26d7d9">损耗统计</h3></td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="totalWaste">损耗总量：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalWaste" name="totalWaste" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayWaste">今日损耗：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayWaste" name="todayWaste" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="yesterdayWaste">昨日损耗：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="yesterdayWaste" name="yesterdayWaste" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4"><h3 style="font-weight: lighter;font-size: large;color: #26d7d9">开房统计</h3></td>
                            </tr>
                            <tr>
                                <td class="form-label">
                                    <label for="totalRoomCount">房间模式总次数：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="totalRoomCount" name="totalRoomCount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="todayRoomCount">房间模式今日次数：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="todayRoomCount" name="todayRoomCount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                                <td class="form-label">
                                    <label for="yesterdayRoomCount">房间模式昨日次数：</label>
                                </td>
                                <td class="form-editor">
                                    <input id="yesterdayRoomCount" name="yesterdayRoomCount" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">每日统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    <div id="everyday" class="easyui-tabs" style="width:100%;height:550px">
                        <div title="注册统计" style="padding:10px">
                            <div id="dailyRegistMain"  style="width: 100%;height:450px;"></div>
                        </div>
                        <div title="充值统计" style="padding:10px">
                            <div id="dailyPayMain"  style="width: 1250px;height:450px;"></div>
                        </div>
                        <div title="金币统计" style="padding:10px">
                            <div id="dailyGoldMain" style="width: 1250px;height:450px;"></div>
                        </div>
                        <div title="服务费统计" style="padding:10px">
                            <div id="dailyRevenueMain" style="width: 1250px;height:450px;"></div>
                        </div>
                        <div title="损耗统计" style="padding:10px">
                            <div id="dailyWasteMain" style="width: 1250px;height:450px;"></div>
                        </div>
                        <div title="开房统计" style="padding:10px">
                            <div id="dailyOpenRoomMain" style="width: 1250px;height:450px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">在线统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    <div style="width: 100%;height: 600px;">
                        <div id="userOnLineMain"  style="width: 100%;height:500px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading" >
                    <span class="heading-font1">分布统计</span>
                    <a href="javascript:void(0);" class="pull-right panel-toggle"><i class="iconfont">&#xe603;</i></a>
                </div>
                <div class="panel-body">
                    <div style="width: 100%;height: 700px;">
                        <div>
                            <form id="distribute-form" method="post" data-options="novalidate:true" >
                                <table class="form-table" border="0" cellpadding="1" cellspacing="2" width="100%">
                                    <td class="form-label">
                                        <label for="totalDistributeScore">总计身上金币数：</label>
                                    </td>
                                    <td class="form-editor">
                                        <input id="totalDistributeScore" name="totalDistributeScore" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                    </td>
                                    <td class="form-label">
                                        <label for="totalDistributeInsureScore">总计银行金币数：</label>
                                    </td>
                                    <td class="form-editor">
                                        <input id="totalDistributeInsureScore" name="totalDistributeInsureScore" class="easyui-textbox" data-options="required:true,disabled: true"/>
                                    </td>
                                </table>
                            </form>
                        </div>
                        <div>
                            <div id="distributeGoldMain"  style="width: 100%;height:500px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/static/app/index/everyday.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/app/index/online.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/app/index/distribute.js"></script>

<script type="text/javascript">

    $(function($){

        //向后台发起求情
        //ajax 加载form表单数据
        $.ajax({
            url: "${pageContext.request.contextPath}/api/index/queryIndexFront",
            type: 'GET',
            dataType: 'json',
           // data: {id: param.id},
            success: function(resp){
                //console.log(resp);
                //全局统计
                $('#global-form').form("load",resp.globalGroup);
                //每日统计图表
                everday(resp.everdayGroupList);
                //在线人数统计
                userOnLine(resp.onlineGroup);
                //分布统计
                distribute(resp.distributeGroupList);
               // if(resp.success){
                    //执行成功
                    /*$('#useredit-form').form("load",{
                        id: resp.data.id,
                        name: resp.data.name,
                        loginName: resp.data.loginName,
                        merchant: resp.data.merchant,
                        secretKey: resp.data.secretKey
                    });*/

               // }else{
                  //  $.messager.alert('警告','根据id查询人员失败');
                //}
            }
        });



        //查询全局统计数据

        //查询每日统计图表

        //查询在线统计数据

        //查询分布统计数据

        $('#everyday').tabs({
            onSelect:function(title,index){
                //alert(title + "==========" + index);
                if(index != 0){


                }
            }
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



    });





</script>
</body>
</html>








