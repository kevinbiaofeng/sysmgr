
// hengoking
// payplatform.com

// form 序列化成 json 对象
$.fn.serializeObject = function(){
	var o = {};
	var a = this.serializeArray();
	//console.log( a );
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
}





