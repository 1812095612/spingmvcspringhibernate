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

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		<h2>[创建账号]</h2>
		
		<form action="manager/auth/saveAdmin" method="post">
			<table class="formTable">
			
				<c:if test="${exception != null }">
					<tr>
						<td colspan="2">
							<spring:message code="${exception.class.name }"/>
						</td>
					</tr>
				</c:if>
				
				<tr>
					<td>账号</td>
					<td>
						<input type="text" name="adminName" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>密码</td>
					<td>
						<input type="password" name="adminPwd" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="保存"/>
					</td>
				</tr>
				
			</table>
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>