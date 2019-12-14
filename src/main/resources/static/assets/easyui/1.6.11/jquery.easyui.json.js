
// hengoking
// payplatform.com

/*
 * 
//序列化问json对象
function getFormJson(form) {
	var o = {};
	var a = $(form).serializeArray();
	$.each(a, function () {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}
 */
(function($){
	
	// form 序列化成 json 对象
	$.fn.serializeObject = function(){
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	
	
	// json对象 转成 json字符串
	$.toString = function(jsonObject){
		//把对象转换成json字符串
		let jsonStr = JSON.stringify(obj);
		return jsonStr;
	} ;
	
	// json字符串  转成  json对象
	$.toObject = function(jsonStr){
		// 把字符串转换成json对象
		let jsonObj = JSON.parse(string);
		return jsonObj;
	} ;
	
	
	
})($);


