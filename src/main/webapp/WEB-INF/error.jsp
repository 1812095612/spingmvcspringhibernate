<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		$("button").click(function(){
			//后退
			window.history.back();
			
			//前进
			//window.history.forward();
			
		});
	});
	
</script>
</head>
<body>

	<div id="mainDiv" class="borderDiv">
		
		[错误消息]
		<br/>
		<spring:message code="${exception.class.name }"/>
		<br/>
		<button>返回继续操作</button>
		
	</div>
	
</body>
</html>