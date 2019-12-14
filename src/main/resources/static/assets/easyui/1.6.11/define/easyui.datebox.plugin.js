

//  easyui datebox 插件

const DEF_BEGIN = " 00:00:00";//开始日期后面加后缀

const DEF_END = " 23:59:59";//结束日期后面加后缀

//比较字符串不区分大小写
String.prototype.compare = function(str){
	//不区分大小写
	if(this.toLowerCase() == str.toLowerCase()){
	   return true; // 正确
	}else{
	   return false; // 错误
	}
}

$.extend({
	
	/**
	 * 格式化日期
	 *  第一个参数 ： 日期类型 包含年月日
	 * 	第二个参数 ： type 取值 'begin'  或者 'end'
	 *  第三个参数 ： 目标赋值域 id
	 */
	easyuiDateBoxFormat : function(date,type,target){
		if(date == null || date == 'undefined'){
			throw new Error("transmit param date is null");
		}
		if(type == '' || type == null || type == 'undefined'){
			throw new Error("transmit param type is null");
		}
		if(target == '' || target == null || target == 'undefined'){
			throw new Error("transmit param target is null")
		}
		//都不为空的情况下进行赋值
		if( 'begin'.compare(type) || 'end'.compare(type) ) {
			//获取日期字符串
			let dateStr = $._dateFormatter(date);// yyyy-MM-dd
			if( 'begin'.compare(type) ){
				//开始时间
				dateStr += DEF_BEGIN;
			}else if( 'end'.compare(type) ){
				//结束时间
				dateStr += DEF_END;
			}
			//设置值
			$("#" + target).textbox('setValue',dateStr); 
		}else{
			throw new Error("transmit param type is not 'begin' or 'end'");
		}
	}
	
	
});
