function _info_(message) {
    if (typeof(console) != 'undefined' && typeof(console.info) == 'function') {
        console.info(message);
    }
}
function getBrowserName() {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.name = "IE" :
        (s = ua.match(/msie ([\d.]+)/)) ? Sys.name = "IE" :
            (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.name = "Firefox" :
                (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.name = "Chrome" :
                    (s = ua.match(/opera.([\d.]+)/)) ? Sys.name = "Opera" :
                        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.name = "Safari" : Sys.name = "";

    if (!Sys.name)
        alert("不支持浏览器");
    return Sys.name;
}

function getBrowserVersion() {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.version = s[1] :
        (s = ua.match(/msie ([\d.]+)/)) ? Sys.version = s[1] :
            (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.version = s[1] :
                (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.version = s[1] :
                    (s = ua.match(/opera.([\d.]+)/)) ? Sys.version = s[1] :
                        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.version = s[1] : 0;

    return Sys.version;
}

function downloadAdobeReader(message) {
    alert(message);
    //location = 'http://ardownload.adobe.com/pub/adobe/reader/win/9.x/9.3/chs/AdbeRdr930_zh_CN.exe';
    location = '../plugins/AdbeRdr1010_zh_CN.exe';
}
function checkPdfSupport() {
    if (!isPdfSupport()) {
        var browserName = getBrowserName();
        if (browserName == "Chrome") {
            downloadAdobeReader("请先安装Chrome PDF Viewer,或者安装Adobe Reader!");
        } else {
            downloadAdobeReader("您还没有安装PDF阅读器软件呢,为了方便预览PDF文档,请选择安装！");
        }
    }
}
function isPdfSupport() {
    var isInstalled = false;
    var browserName = getBrowserName();
    _info_(browserName);
    if (browserName == "Firefox") {
        // 如果是firefox浏览器
        var plugins = navigator.plugins;
        if (plugins && plugins.length) {
            for (var x = 0, len = plugins.length; x < len; x++) {
                var pluginName = plugins[x].name;
                _info_(pluginName);
                //Adobe Reader 或者是 pdf.js
                if (pluginName == 'Adobe Acrobat' || pluginName.startsWith("PDF")) {
                    isInstalled = true;
                    break;
                }
            }
            if (!isInstalled) {
                //downloadAdobeReader("您还没有安装PDF阅读器软件呢,为了方便预览PDF文档,请选择安装！");
                return false;
            }
        }
    } else if (browserName == "Chrome") {
        // 如果是Chrome浏览器
        var plugins = navigator.plugins;
        if (plugins && plugins.length) {
            for (var x = 0, len = plugins.length; x < len; x++) {
                var pluginName = plugins[x].name;
                _info_(pluginName);
                if (pluginName == 'Chrome PDF Viewer' || pluginName == 'Adobe Acrobat') {
                    isInstalled = true;
                    break;
                }
            }
            if (!isInstalled) {
                //downloadAdobeReader("请先安装Chrome PDF Viewer,或者安装Adobe Reader!");
                return false;
            }
        }
    } else if (browserName == "IE") {
        var oAcro;
        try {
            // AcroPDF.PDF is used by version 7 and later
            oAcro = new ActiveXObject('AcroPDF.PDF.1');
            if (oAcro) {
                isInstalled = true;
            }
        } catch (e) {
            isInstalled = false;
        }
        /*
         if (!isInstalled) {
         try {
         // PDF.PdfCtrl is used by version 6 and earlier
         oAcro = new ActiveXObject('PDF.PdfCtrl');
         if (oAcro)
         isInstalled = true;
         } catch (e) {
         isInstalled = false;
         }
         }
         */
        if (!isInstalled) {
            //downloadAdobeReader("您还没有安装PDF阅读器软件呢,为了方便预览PDF文档,请选择安装！");
            return false;
        }
    } else {
        alert("不支持的浏览器，请提交您的浏览器信息，以便兼容。");
    }
    return true;
}