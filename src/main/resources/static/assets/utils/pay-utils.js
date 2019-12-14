mini.namespace("wssip.utils");
wssip.utils.request = function (params) {
    $.ajax({
        url: params.url,
        type: "post",
        data: params.data,
        //enctype: 'multipart/form-data',
        success: function (text) {
            try {
                var obj = mini.decode(text, true);
                if (obj.success) {
                    if (typeof(params.success) == "function") {
                        params.success(obj.result);
                    }
                } else {
                    var errMsg = "服务端返回格式有误";
                    if (typeof(obj.error) != "undefined") {
                        errMsg = obj.error;
                    }
                    if (typeof(params.error) == "function") {
                        params.error(errMsg);
                    }
                }

            } catch (e) {
                if (e.message) {
                    errMsg = e.message;
                } else {
                    errMsg = text;
                }
                if (typeof(params.error) == "function") {
                    params.error(errMsg);
                }
            }

        },
        error: function (e, f, g) {
            var errMsg = e.responseText;
            if (typeof(params.error) == "function") {
                params.error(errMsg);
            }
        }
    });
}

wssip.utils.formLoad = function (url, formId, params, successFunc, failFunc) {
    if(!url){
        mini.alert("未传入url参数");
        return;
    }
    if(!formId){
        mini.alert("未传入formId参数");
        return;
    }
    if (!params) {
        params = {};
    }
    wssip.utils.request({
        url: url,
        data: params,
        method: "POST",
        success: function (json) {
            var form = new mini.Form("#"+formId);
            form.setData(json);
            if (typeof(successFunc) == 'function') {
                successFunc(json);
            }
        },
        error: function (json) {
            if (typeof(failFunc) == 'function') {
                failFunc(json);
            } else {
                mini.alert(json.error);
            }
        }
    });
}

wssip.utils.formSubmit = function (url, formId, successFunc, failFunc) {
    if(!url){
        mini.alert("未传入url参数");
        return;
    }
    if(!formId){
        mini.alert("未传入formId参数");
        return;
    }
    var form = new mini.Form("#"+formId);
    var json = form.getData();
    wssip.utils.request({
        url: url,
        data: json,
        method: "POST",
        success: function (json) {
            if (typeof(successFunc) == 'function') {
                successFunc(json);
            } else {
                mini.alert("操作成功！");
            }
        },
        error: function (json) {
            if (typeof(failFunc) == 'function') {
                failFunc(json);
            } else {
                mini.alert(json.error);
            }
        }
    });
}

wssip.utils.asyncRequest = function (params) {
    $.ajax({
        url: params.url,
        type: "post",
        async: params.async,
        data: params.data,
        success: function (text) {
            try {
                var obj = mini.decode(text, true);
                if (obj.success) {
                    if (typeof(params.success) == "function") {
                        params.success(obj.result);
                    }
                } else {
                    var errMsg = "服务端返回格式有误";
                    if (typeof(obj.error) != "undefined") {
                        errMsg = "服务端发生错误：" + obj.error;
                    }
                    //alert(errMsg);
                    if (typeof(params.error) == "function") {
                        params.error(errMsg);
                    }
                }

            } catch (e) {
                errMsg = "服务端发生错误：" + text;
                if (typeof(params.error) == "function") {
                    params.error(errMsg);
                }
            }

        },
        error: function (e, f, g) {
            var errMsg = "服务端发生异常：" + e.responseText;
            //alert(errMsg);
            if (typeof(params.error) == "function") {
                params.error(errMsg);
            }
        }
    });
}

wssip.utils.getTopWin = function () {
    var topwin = top["mainFrame"];
    if (!topwin)topwin = top;
    return topwin;
}
wssip.utils.error = function (msg, callback) {
    mini.showMessageBox({
        title: "提示信息",
        iconCls: "mini-messagebox-error",
        buttons: ["ok"],
        message: msg,
        callback: function (action) {
            if (typeof(callback) == "function") {
                try {
                    callback(action);
                } catch (e) {
                    alert("回调函数执行错误");
                }
            }
        }
    });
}

wssip.utils.topError = function (msg, callback) {
    if (top.mini) {
        top.mini.showMessageBox({
            title: "提示信息",
            iconCls: "mini-messagebox-error",
            buttons: ["ok"],
            message: msg,
            callback: function (action) {
                if (typeof(callback) == "function") {
                    try {
                        callback(action);
                    } catch (e) {
                        alert("回调函数执行错误");
                    }
                }
            }
        });
    } else {
        mini.showMessageBox({
            title: "提示信息",
            iconCls: "mini-messagebox-error",
            buttons: ["ok"],
            message: msg,
            callback: function (action) {
                if (typeof(callback) == "function") {
                    try {
                        callback(action);
                    } catch (e) {
                        alert("回调函数执行错误");
                    }
                }
            }
        });
    }
}

wssip.utils.alert = function (msg, callback) {
    mini.showMessageBox({
        title: "提示信息",
        iconCls: "mini-messagebox-warning",
        buttons: ["ok"],
        message: msg,
        callback: function (action) {
            if (typeof(callback) == "function") {
                try {
                    callback(action);
                } catch (e) {
                    alert("回调函数执行错误");
                }
            }
        }
    });
}

wssip.utils.topAlert = function (msg, callback) {
    if (top.mini) {
        top.mini.showMessageBox({
            title: "提示信息",
            iconCls: "mini-messagebox-warning",
            buttons: ["ok"],
            message: msg,
            callback: function (action) {
                if (typeof(callback) == "function") {
                    try {
                        callback(action);
                    } catch (e) {
                        alert("回调函数执行错误");
                    }
                }
            }
        });
    } else {
        mini.showMessageBox({
            title: "提示信息",
            iconCls: "mini-messagebox-warning",
            buttons: ["ok"],
            message: msg,
            callback: function (action) {
                if (typeof(callback) == "function") {
                    try {
                        callback(action);
                    } catch (e) {
                        alert("回调函数执行错误");
                    }
                }
            }
        });
    }
}

wssip.utils.info = function (msg, callback) {
    mini.showMessageBox({
        title: "提示信息",
        iconCls: "mini-messagebox-info",
        buttons: ["ok"],
        message: msg,
        callback: function (action) {
            if (typeof(callback) == "function") {
                try {
                    callback(action);
                } catch (e) {
                    alert("回调函数执行错误");
                }
            }
        }
    });
}

wssip.utils.confirm = function (msg, callback) {
    mini.confirm(msg, "确认信息",
        function (action) {
            if (typeof(callback) == "function") {
                if (action == "ok") {
                    try {
                        callback(true);
                    } catch (e) {
                        alert("回调函数执行错误");
                    }
                } else {
                    try {
                        callback(false);
                    } catch (e) {
                        alert("回调函数执行错误");
                    }
                }
            }
        }
    );
}

wssip.utils.prompt = function (msg, callback, isMulti) {
    mini.prompt(msg, "请输入",
        function (action, value) {
            if (typeof(callback) == "function") {
                if (action == "ok") {
                    try {
                        callback(value);
                    } catch (e) {
                        alert("回调函数执行错误");
                    }
                }
            }
        }, isMulti
    );
}

wssip.utils.mask = function () {
    var topwin = wssip.utils.getTopWin();
    try {
        topwin.mask();
    } catch (e) {

    }
}

wssip.utils.unmask = function () {
    var topwin = wssip.utils.getTopWin();
    try {
        topwin.unmask();
    } catch (e) {

    }
}

wssip.utils.maskwin = function (msg) {
    mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: msg
    });
}

wssip.utils.unmaskwin = function () {
    mini.unmask(document.body);
}

wssip.utils.NVL = function () {
    for (i = 0; i < arguments.length; i++) {
        if (arguments[i])return arguments[i];
    }
    return "";
}
wssip.utils.openErrPage = function (htmlcontent) {
    var newWindow = window.open("", "", "status,height=600,width=800");
    newWindow.document.write(htmlcontent);
    newWindow.document.close();
}