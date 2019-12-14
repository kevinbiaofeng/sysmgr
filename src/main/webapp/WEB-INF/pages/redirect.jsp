<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>redirect</title>
    <%@ include file="/static/pub/include.jsp" %>
</head>
<body>

	<script type="text/javascript">
		var storage = window.sessionStorage;
		storage["payplatform_sysmgr_menu"] = ""; 
	
		$(function($){
			window.location.href= "${pageContext.request.contextPath}/index";
		});
		
		
	</script>
</body>
</html>