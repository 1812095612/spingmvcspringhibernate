<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		<h2>[显示简答题统计结果]</h2>
		
		<table class="dataTable">
			
			<c:if test="${empty textResultList }">
				<tr>
					<td>暂时没有答案数据</td>
				</tr>
			
			</c:if>
			<c:if test="${!empty textResultList }">
				
				<c:forEach items="${textResultList }" var="text">
					
					<tr>
						<td>${text }</td>
					</tr>
				
				</c:forEach>
				
			</c:if>
			
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>