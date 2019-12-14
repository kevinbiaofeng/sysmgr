

//  easyui dialog 插件

$.extend({
	
	//获取打开 dialog 参数
	// 第一个参数 ： dialogId 
	// 第二个参数 ： 传递参数 paramkey 默认是 'dialogParams'
	getOpenDialogParam: function(dialogId,paramkey){
		if(dialogId == '' || dialogId == null || dialogId == 'undefined'){
			throw new Error("transmit param dialogId is null");
		}
		if(paramkey == '' || paramkey == null || paramkey == 'undefined'){
			paramkey = 'dialogParams';
		}
		var paramObj = $('#' + dialogId).dialog('options');
		var dialogParams = paramObj[paramkey];
		return dialogParams;
	}
	
	
	
	
});

