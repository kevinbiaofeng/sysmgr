$.ajaxSetup({
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		if (XMLHttpRequest.status == 403) {
			$.messager.alert('提示', '限制访问此资源或进行此操作', 'info');
			return false;
		}
	},
	complete : function(XMLHttpRequest, textStatus) {
		var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头,sessionstatus，
		if (sessionstatus == 'timeout') {
			// 如果超时就处理 ，指定要跳转的页面
			parent.window.location.href = location.href;
		}
	}
});
