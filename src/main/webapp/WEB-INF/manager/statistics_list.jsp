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
		
		<h2>[显示所有已完成调查]</h2>
		
		<table class="dataTable">
			<c:if test="${empty page.list }">
				<tr>
					<td>暂时没有已完成调查</td>
				</tr>
			</c:if>
			<c:if test="${!empty page.list }">
				<tr>
					<td>调查名称</td>
					<td>查看大纲</td>
					<td colspan="2">导出数据</td>
				</tr>
				
				<c:forEach items="${page.list }" var="survey">
					
					<tr>
						<td>${survey.surveyName }</td>
						<td>
							<a href="manager/statistics/showSummary/${survey.surveyId }">显示调查问题大纲</a>
						</td>
						<td>
							<a href="manager/statistics/export/${survey.surveyId }">导出Excel文件</a>
						</td>
						<td>
							<a href="manager/statistics/testExport">使用假数据测试Excel下载</a>
						</td>
					</tr>
					
				</c:forEach>
				
				<tr>
					<td colspan="4">
						<c:set value="manager/statistics/showAllSurvey" var="navigatorUrl" scope="page"></c:set>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
				
			</c:if>
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>