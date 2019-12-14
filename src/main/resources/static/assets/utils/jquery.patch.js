/**
 *
 * 返回JSON结果统一校验，格式要求：
 * 1、表单类
 * 2、表格类
 * 3、树形类
 *
 * 其中只在result=false时存在，并且必须存在
 *
 **/
function $logmsg(msg) {
    if (typeof(console) === 'object' && typeof(console.log) === 'function') {
        console.log(msg);
    }
}
$(document).ajaxSuccess(function (evt, request, settings) {
    var text = request.responseText;
    var url = settings.url;
    var json;
    if (text.charAt(0) == '[' && text.charAt(text.length - 1) == "]") {
        return;
    }
    try {
        json = eval('(' + text + ')');//解析JSON
    } catch (e) {
        alert("解析远程返回数据失败,错误信息:[" + e.message + "]");
        return;
    }
    if (json.success === false) {
        //判断是否登陆失效
        if (json.status == 600) {
            if(json.refresh){
                if(window.parent){
                    window.parent.location = json.loginurl;
                }
            }
        }
        //alert(json.error);
        $logmsg("url:" + url + ",text:" + text);
        return;
    } else if (typeof(json.success) === 'undefined') {
        alert("远程返回数据格式不正确.");
        $logmsg("url:" + url + ",text:" + text);
        return;
    }
});

