<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[选择一个目标调查]
		<table class="dataTable">
			
			<tr>
				<td>调查名称</td>
				<td>操作</td>
			</tr>
			
			<c:forEach items="${page.list }" var="survey">
				
				<tr>
					<td>${survey.surveyName }</td>
					<td>
						<%-- 当前包裹所在的调查：requestScope.surveyId --%>
						<%-- 遍历过程中的当前调查：survey.surveyId --%>
						<c:if test="${requestScope.surveyId == survey.surveyId }">
							当前调查
						</c:if>
						<c:if test="${requestScope.surveyId != survey.surveyId }">
							<a href="guest/bag/moveToThisSurvey/${bagId }/${survey.surveyId}">移动到当前调查</a>
							<a href="guest/bag/copyToThisSurvey/${bagId }/${survey.surveyId}">复制到当前调查</a>
						</c:if>
					</td>
				</tr>
				
			</c:forEach>
			
			<tr>
				<td colspan="2">
					<c:set value="guest/bag/toTargetSurveyListUI/${bagId }/${surveyId}" var="navigatorUrl" scope="page"/>
					<%@include file="/res_jsp/navigator.jsp" %>
				</td>
			</tr>
			
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>