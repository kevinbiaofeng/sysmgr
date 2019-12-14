/***
*** 日期时间类工具类
*** author:xieguojun
**/

/**
***  获取
***/
function getSystemDate(now){

	return parseDate(now);			
}

/**
** 获取业务月度
**/
function getYYYYMM(now){
	var date =getSystemDate(now);
	
	var result ='';
	var month =date.getMonth();
	 
	result +=date.getYear();
	
	if(month.length==2)
		result +=month;
	else
		result +="0"+month;
	
	return result;	
}


/** 
**  将dateStr日期字符串转换为javascript日期
**   dateStr:日期字符串
**	 format:日期格式，默认值为Y-m-d H:i:s
**/
function parseDate(dateStr,format){
	return new Date(dateStr);
	/*if(typeof(dateStr) !='undefined'){
		if(typeof(format) !='undefined')
			//return Date.parseDate(dateStr,format);
			return new Date(dateStr);
		else
			//return Date.parseDate(dateStr,'Y-m-d H:i:s');	
			return new Date(dateStr);
	}*/
}

//取得日期字符串,返回YYYY-MM-DD   
function getDate(date)   
{   
    var thisYear = date.getFullYear();   
    var thisMonth = date.getMonth() + 1;   
    //如果月份长度是一位则前面补0   
    if(thisMonth<10) thisMonth = "0" + thisMonth;   
       
    var thisDay = date.getDate();   
    //如果天的长度是一位则前面补0   
    if(thisDay<10) thisDay = "0" + thisDay;   
       
    return thisYear + "-" + thisMonth + "-" + thisDay;   
} 
//取得日期字符串,返回YYYYMMDDHHMMSS   
function getDateYmdhms(date)   
{   
    var thisYear = date.getFullYear();   
    var thisMonth = date.getMonth() + 1;   
    //如果月份长度是一位则前面补0   
    if(thisMonth<10) thisMonth = "0" + thisMonth;   
       
    var thisDay = date.getDate();   
    //如果天的长度是一位则前面补0   
    if(thisDay<10) thisDay = "0" + thisDay;  
    
    //获取小时
    var thisHours = date.getHours(); //获取当前小时数(0-23)
    //获取分钟
    var thisMinutes = date.getMinutes(); //获取当前分钟数(0-59)
    //获取秒数
    var thisSeconds = date.getSeconds(); //获取当前秒数(0-59)
       
    return thisYear + "" + thisMonth + "" + thisDay + "" + thisHours + "" + thisMinutes + "" + thisSeconds;   
}
//取得日期字符串,返回YYYYMMDD   
function getDateYmd(date)   
{   
    var thisYear = date.getFullYear();   
    var thisMonth = date.getMonth() + 1;   
    //如果月份长度是一位则前面补0   
    if(thisMonth<10) thisMonth = "0" + thisMonth;   
       
    var thisDay = date.getDate();   
    //如果天的长度是一位则前面补0   
    if(thisDay<10) thisDay = "0" + thisDay;   
       
    return thisYear + "" + thisMonth + "" + thisDay;   
}

//取得日期字符串,返回YYYYMM  
function getDateYm(date)   
{   
    var thisYear = date.getFullYear();   
    var thisMonth = date.getMonth() + 1;   
    //如果月份长度是一位则前面补0   
    if(thisMonth<10) thisMonth = "0" + thisMonth;
    return thisYear + "" + thisMonth;
}

//取得日期字符串,返回YYYY 
function getDateY(date)   
{   
    var thisYear = date.getFullYear();   
    var thisMonth = date.getMonth() + 1;   
    //如果月份长度是一位则前面补0   
    if(thisMonth<10) thisMonth = "0" + thisMonth;
    return thisYear;
}

//取得上几年度日期字符串,返回YYYYMM
/**
 * date 传入时间
 * pYear 上几年度
 */
function getPreviousDateYm(date,pYear,pMonth)   
{   
    var thisYear = date.getFullYear(); 
    thisYear = thisYear - pYear;
    var thisMonth = date.getMonth() + 1;  
    //如果月份长度是一位则前面补0   
    if(thisMonth<10){
    	thisMonth = "0" + thisMonth;
    } 
    return thisYear + "-" + thisMonth;
}

//获取当前时间前几个月
function getPreviousDateYmd(date,pMonth)   
{
	date.setMonth(date.getMonth()-pMonth + 1);
    var thisYear = date.getFullYear(); 
    var thisMonth = date.getMonth();  
    //如果月份长度是一位则前面补0   
    if(thisMonth<10){
    	thisMonth = "0" + thisMonth;
    } 
    var thisDay = date.getDate();   
    //如果天的长度是一位则前面补0   
    if(thisDay<10){
    	thisDay = "0" + thisDay;
    }   
    return thisYear + "-" + thisMonth + "-" + thisDay;
}

//获取指定日期的上一年度
function getPreviousDate(date,pMonth)
{
	var previousYearStr = getPreviousDateYmd(date,pMonth);
    return new Date(previousYearStr);
}
   
//取得日期时间字符串,返回YYYY-MM-DD HH:mm:SS   
function getDateTime(date)   
{   
    var thisYear = date.getFullYear();   
    var thisMonth = date.getMonth() + 1;   
    //如果月份长度是一位则前面补0   
    if(thisMonth<10) thisMonth = "0" + thisMonth;   
       
    var thisDay = date.getDate();   
    //如果天的长度是一位则前面补0   
    if(thisDay<10) thisDay = "0" + thisDay;   
   
    var thisHour = date.getHours();   
    //如果小时长度是一位则前面补0   
    if(thisHour<10) thisHour = "0" + thisHour;   
       
    var thisMinute = date.getMinutes();   
    //如果分钟长度是一位则前面补0   
    if(thisMinute<10) thisMinute = "0" + thisMinute;   
       
    var thisSecond = date.getSeconds();   
    //如果分钟长度是一位则前面补0   
    if(thisSecond<10) thisSecond = "0" + thisSecond;   
       
    return thisYear + "-" + thisMonth + "-" + thisDay + " " + thisHour + ":" + thisMinute + ":" + thisSecond;   
}   
   
//根据日期字符串生成日期对象,日期字符串格式为YYYY-MM-DD   
function setDate(strDate)   
{   
    var aDate = strDate.split("-");   
    return new Date(aDate[0],aDate[1]-1,aDate[2]);   
}

//根据季度和年份设置统计开始时间和结束时间
/*
第一个参数  传入年份 
第二个参数传入 第几个季度(1,2,3,4)
返回值是一个数组  
	* 数组第一个是当前季度开始时间
	* 数组第一个是当前季度结束时间 
*/
function getQuarterSectionDate(year,quarter){
	var section = [];
	year = parseInt(year);
	quarter = parseInt(quarter);
	if(quarter > 4 || quarter < 1){
		return;
	}
	//计算月份 [((3*quarter) - 2) , 3*quarter]  
	var beginMonth = ((3*quarter) - 2);//开始月份
	if(beginMonth < 10){
		beginMonth = "0" + beginMonth;
	}
	var beginDateStr = year + "-" + beginMonth;
	var beginDate = new Date(beginDateStr);
	section.push(beginDate);
	var endMonth = (3*quarter);//结束月份
	if( endMonth < 10){
		endMonth = "0" + endMonth;
	}
	var endDateStr = year + "-" + endMonth;
	var endDate = new Date(endDateStr);
	section.push(endDate);
	return section;
}

//根据年份设置统计开始时间和结束时间
/*
第一个参数  传入年份 
返回值是一个数组  
	* 数组第一个是当前季度开始时间
	* 数组第一个是当前季度结束时间 
*/
function getYearSectionDate(year){
	var section = [];
	year = parseInt(year);
	var beginDateStr = year + "-" + "01";
	var beginDate = new Date(beginDateStr);
	section.push(beginDate);
	var endDateStr = year + "-" + "12";
	var endDate = new Date(endDateStr);
	section.push(endDate);
	return section;
}
   
//获得指定日期的临近日期   
//strDate:指定的日期,格式为yyyy-mm-dd  nDay:与指定日期相邻的天数 1为明天 -1为昨天   
function getNearDay(strDate,nDay)   
{   
    try  
    {   
        var oDate = setDate(strDate);   
        var newDate = new Date(oDate.valueOf() + nDay*24*60*60*1000);   
        return getDate(newDate);   
    }   
    catch(ex)   
    {   
        return "error";   
    }   
} 


/*
获取指定日期往前或者往后的日期
参数1：指定日期
参数2：日期偏移量 正数当前日期往后  负数当前日期往前
参数3：指定参数2的单位（只能是1，2,3）
	1 年
	2 月
	3 日
*/
function addDaysByUnit(date , offset , company){
	
		offset = parseInt(offset);
		//年
		var thisYear = date.getFullYear(); 
		//月 
	    var thisMonth = date.getMonth();   
	    //日
	    var thisDay = date.getDate();   
	    //单位
		if(company > 3 || company < 1){
			return;
		}
		if(company == 1){
			//年
			var offsetYear = parseInt(thisYear) + offset;
			date.setYear(offsetYear);
			return date;
		}else if(company == 2){
			//月
			var offsetMonth = parseInt(thisMonth) + offset;
			date.setMonth(offsetMonth);
			return date;
		}else if(company == 3){
			//日
			var offsetDay = parseInt(thisDay) + offset;
			date.setDate(offsetDay);
			return date;
		}
}

var myview = $.extend({},$.fn.datagrid.defaults.view,{
    onAfterRender:function(target){
        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
        var opts = $(target).datagrid('options');
        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
        vc.children('div.datagrid-empty').remove();
        if (!$(target).datagrid('getRows').length){
            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
            d.css({
                position:'absolute',
                left:0,
                top:50,
                width:'100%',
                textAlign:'center'
            });
        }
    }
});

//Ext.namespace('Yzsb.date');
//
//Yzsb.date.format =function(date,format){
//	
//	return date.format(format);	
//}
//
//Yzsb.date.parseMonth =function(months){
//	
//	var year =Math.floor(months / 12);
//	var month =months % 12;
//	
//	var result ='';
//	
//	if(year <10)
//		result +='0';
//	result +=year;
//	
//	if(month <10)
//		result +='0';
//	result +=month;
//	
//	return result;	
//}