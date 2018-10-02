<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[用户登录]
		
		<form action="guest/user/login" method="post">
			
			<table class="formTable">
			
				<!-- 在exception对象为null的情况下，EL表达式会解析为空字符串，导致spring:message标签抛异常 -->
				<c:if test="${exception != null }">
					<tr>
						<td colspan="2">
							<spring:message code="${exception.class.name }"/>
						</td>
					</tr>
				</c:if>
				
				<%-- <c:if test="${message != null }">
					<tr>
						<td colspan="2">${message }</td>
					</tr>
				</c:if> --%>
				
				<tr>
					<td>用户名</td>
					<td>
						<input type="text" name="userName" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>密码</td>
					<td>
						<input type="password" name="userPwd" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="登录"/>
					</td>
				</tr>
				
			</table>
			
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>