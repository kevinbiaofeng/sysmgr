
// hengoking
// payplatform.com

$.extend({
    log : function(message) {
        var now = new Date(),
            y = now.getFullYear(),
            m = now.getMonth() + 1, //！JavaScript中月分是从0开始的
            d = now.getDate(),
            h = now.getHours(),
            min = now.getMinutes(),
            s = now.getSeconds(),
            time = y + '/' + m + '/' + d + ' ' + h + ':' + min + ':' + s;
        //console.log(time + ' My App: ' + message);
    },
    // json对象 转成 json字符串
	toString : function(jsonObject){
		//把对象转换成json字符串
		let jsonStr = JSON.stringify(jsonObject);
		return jsonStr;
	},
	// json字符串  转成  json对象
	toObject : function(jsonStr){
		// 把字符串转换成json对象
		let jsonObj = JSON.parse(jsonStr);
		return jsonObj;
	},
	//获取当前日期
	getNow : function(){
		var curr_time = new Date();
		var str = curr_time.getFullYear()+"-";
		str += curr_time.getMonth()+1+"-";
		str += curr_time.getDate()+"-";
		str += curr_time.getHours()+":";
		str += curr_time.getMinutes()+":";
		str += curr_time.getSeconds();
		return str;  	
	},
	//获取当前日期
	_getNow : function(){
		var curr_time = new Date();
		var str = curr_time.getFullYear()+"-";
		str += curr_time.getMonth()+1+"-";
		str += curr_time.getDate();
		/*str += curr_time.getHours()+":";
		str += curr_time.getMinutes()+":";
		str += curr_time.getSeconds();*/
		return str;  	
	},
	//格式化日期
	dateFormatter : function dateFormatter (value) {
    	//console.log(value);
		if(value == null || value == 'undefined'){
			return "";
		}
	    var date = new Date(value);
	    var year = date.getFullYear().toString();
	    var month = (date.getMonth() + 1);
	    var day = date.getDate().toString();
	    var hour = date.getHours().toString();
	    var minutes = date.getMinutes().toString();
	    var seconds = date.getSeconds().toString();
	    if (month < 10) {
	        month = "0" + month;
	    }
	    if (day < 10) {
	        day = "0" + day;
	    }
	    if (hour < 10) {
	        hour = "0" + hour;
	    }
	    if (minutes < 10) {
	        minutes = "0" + minutes;
	    }
	    if (seconds < 10) {
	        seconds = "0" + seconds;
	    }
	    return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
	},
	_dateFormatter : function dateFormatter (value) {
		if(value == null || value == 'undefined'){
			return "";
		}
	    var date = new Date(value);
	    var year = date.getFullYear().toString();
	    var month = (date.getMonth() + 1);
	    var day = date.getDate().toString();
	    var hour = date.getHours().toString();
	    var minutes = date.getMinutes().toString();
	    var seconds = date.getSeconds().toString();
	    if (month < 10) {
	        month = "0" + month;
	    }
	    if (day < 10) {
	        day = "0" + day;
	    }
	    return year + "-" + month + "-" + day;
	},
	//格式化日期 转成14位数字
	dateFormatterYmd : function dateFormatter (value) {
	    var date = new Date(value);
	    var year = date.getFullYear().toString();
	    var month = (date.getMonth() + 1);
	    var day = date.getDate().toString();
	    var hour = date.getHours().toString();
	    var minutes = date.getMinutes().toString();
	    var seconds = date.getSeconds().toString();
	    if (month < 10) {
	        month = "0" + month;
	    }
	    if (day < 10) {
	        day = "0" + day;
	    }
	    if (hour < 10) {
	        hour = "0" + hour;
	    }
	    if (minutes < 10) {
	        minutes = "0" + minutes;
	    }
	    if (seconds < 10) {
	        seconds = "0" + seconds;
	    }
	    return year + "" + month + "" + day + "" + hour + "" + minutes + "" + seconds;
	},
	//转成8位数字
	_dateFormatterYmd : function dateFormatter (value) {
	    var date = new Date(value);
	    var year = date.getFullYear().toString();
	    var month = (date.getMonth() + 1);
	    var day = date.getDate().toString();
	    var hour = date.getHours().toString();
	    var minutes = date.getMinutes().toString();
	    var seconds = date.getSeconds().toString();
	    if (month < 10) {
	        month = "0" + month;
	    }
	    if (day < 10) {
	        day = "0" + day;
	    }
	    return year + "" + month + "" + day;
	},

	//格式化数字
	numberFormatter : function(value){
		if(value == null || value == 'undefined'){
			return 0.00;
		}
		return value.toFixed(2);
	}
    
});


