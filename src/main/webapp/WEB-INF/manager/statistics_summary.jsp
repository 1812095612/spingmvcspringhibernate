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
		
		<h2>[调查大纲]</h2>
		
		<p>[调查名称：${survey.surveyName }]</p>
		
		<table class="dataTable">
			
			<tr>
				<td colspan="2">包裹列表</td>
			</tr>
			
			<c:forEach items="${survey.bagSet }" var="bag">
				
				<tr>
					<td>${bag.bagName }</td>
					<td>统计结果</td>
				</tr>
				
				<tr>
					<td></td>
					<td>
						<table class="dataTable">
							
							<c:forEach items="${bag.questionSet }" var="question">
								
								<tr>
									<td>${question.questionName }</td>
									<td>
										<c:if test="${question.questionType == 0 || question.questionType == 1 }">
											<img src="manager/statistics/generateChart/${question.questionId }"/>
										</c:if>
										<c:if test="${question.questionType == 2 }">
											<a href="manager/statistics/showTextList/${question.questionId }">以列表形式查看结果</a>
										</c:if>
									</td>
								</tr>
								
							</c:forEach>
							
							
						</table>
					</td>
				</tr>
				
			</c:forEach>
			
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>