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
		
		[显示我的未完成调查]
		<table class="dataTable">
			<c:if test="${empty page.list }">
				<tr>
					<td>暂时没有调查</td>
				</tr>
			</c:if>
			<c:if test="${!empty page.list }">
				
				<tr>
					<td>ID</td>
					<td>名称</td>
					<td>Logo</td>
					<td colspan="3">操作</td>
				</tr>
				
				<c:forEach items="${page.list }" var="survey">
					
					<tr>
						<td>${survey.surveyId }</td>
						<td>${survey.surveyName }</td>
						<td>
							<img src="${survey.logoPath }" />
						</td>
						<td>
							<a href="guest/survey/toDesignUI/${survey.surveyId }">设计</a>
						</td>
						<td>
							<a href="guest/survey/removeSurvey/${survey.surveyId }">删除</a>
						</td>
						<td>
							<a href="guest/survey/editSurvey/${survey.surveyId }">更新</a>
						</td>
					</tr>
					
				</c:forEach>
				
				<tr>
					<td colspan="6">
						<c:set value="guest/survey/showMyUncompletedSurvey" var="navigatorUrl" scope="page"/>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
				
			</c:if>
		</table>
		
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>