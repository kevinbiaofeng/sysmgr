
//  easyui datagrid 插件

//const EDIT_BTN = "<button class='layui-btn layui-btn-xs layui-btn-warm'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i></button>";
//const DELETE_BTN = "<button class='layui-btn layui-btn-xs layui-btn-danger'><i class='iconfont' data-icon='&#xe626;'>&#xe626;</i></button>";

const EDIT_BTN = "<button class='layui-btn layui-btn-xs layui-btn-warm' onclick='optionEditEvent({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
const DELETE_BTN = "<button class='layui-btn layui-btn-xs layui-btn-danger' onclick='optionDeleteEvent({0})'><i class='iconfont' data-icon='&#xe626;'>&#xe626;</i><span>{1}</span></button>";

//const EDIT_BTN = "<button class='layui-btn layui-btn-sm layui-btn-warm' onclick='optionEditEvent({0})'><i class='iconfont' data-icon='&#xe653;'>&#xe653;</i><span>{1}</span></button>";
//const DELETE_BTN = "<button class='layui-btn layui-btn-sm layui-btn-danger' onclick='optionDeleteEvent({0})'><i class='iconfont' data-icon='&#xe626;'>&#xe626;</i><span>{1}</span></button>";

/**
 * 定义原生使用占位符的方法，格式化数据
 * 
 * @author sky
 * @date 2018-07-09
 * @returns {*}
 */
/*
 * String.prototype.format = function () { // 数据长度为空，则直接返回 if (arguments.length ==
 * 0){ return this; } // console.log(arguments[]); // 使用正则表达式，循环替换占位符数据 for (var
 * result = this, i = 0; i < arguments.length; i++){ result = result.replace(new
 * RegExp("\\{" + i + "\\}", "g"), arguments[i]); //return result; } };
 */
String.prototype.format = function() {
	if (arguments.length == 0)
		return this;
	for (var s = this, i = 0; i < arguments.length; i++)
		s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
	return s;
};

// 原生字符串替换
$.extend({

	/**
	 * value：字段值 row：行记录数据。 index: 行索引 从0开始
	 */
	// rowFormater: function rowFormater(value, row, index)
	rowFormater : function rowFormater(pk, row, type, object) {
		if (pk == '' || pk == null || pk == 'undefined') {
			throw new Error("transmit param pk is null");
		}
		if (object == null || object == '' || object == 'undefined') {
			object = {
				edit : '编辑',
				del : '删除',
			};
		}
		if (type == '' || type == null || type == 'undefined') {
			return EDIT_BTN.format(row[pk],object['edit']) + DELETE_BTN.format(row[pk],object['del']);
		} else if (type === 'edit') {
			//console.log(EDIT_BTN.format(row[pk],object['edit']));
			return EDIT_BTN.format(row[pk],object['edit']);
		} else if (type === 'del') {
			return DELETE_BTN.format(row[pk],object['del']);
		}
		return EDIT_BTN.format(row[pk]) + DELETE_BTN.format(row[pk]);
	}

});
