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
		
		[设计调查]
		<table class="dataTable">
			
			<tr>
				<td colspan="4">调查本身的基本信息</td>
			</tr>
			
			<tr>
				<td><img src="${requestScope.survey.logoPath }"/></td>
				<td>${requestScope.survey.surveyName }</td>
				<td>
					<a href="guest/bag/toAddUI/${survey.surveyId }">创建包裹</a>
				</td>
				<td>
					<a href="guest/bag/toOrderAdjustUI/${survey.surveyId }">调整包裹的顺序</a>
				</td>
			</tr>
			
		</table>
		
		<br/><br/>
		
		<table class="dataTable">
			
			<c:if test="${empty requestScope.survey.bagSet }">
				
				<tr>
					<td>暂时没有包裹</td>
				</tr>
				
			</c:if>
			<c:if test="${!empty requestScope.survey.bagSet }">
				
				<c:forEach items="${requestScope.survey.bagSet }" var="bag">
					
					<tr>
						<td>${bag.bagName }</td>
						<td>
							<a href="guest/bag/removeBag/${bag.bagId }/${requestScope.survey.surveyId}">删除包裹</a>
							<a href="guest/bag/toEditUI/${bag.bagId }/${requestScope.survey.surveyId}">更新包裹</a>
							<a href="guest/bag/toTargetSurveyListUI/${bag.bagId }/${requestScope.survey.surveyId}"><span style="color:red">移动/复制包裹</span></a>
							<a href="guest/question/toAddUI/${bag.bagId }/${requestScope.survey.surveyId}">创建问题</a>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td>
							<table class="dataTable">
								
								<c:if test="${empty bag.questionSet }">
									<tr>
										<td>暂时没有问题</td>
									</tr>
								</c:if>
								<c:if test="${!empty bag.questionSet }">
									
									<c:forEach items="${bag.questionSet }" var="question">
										
										<tr>
											<td>
												${question.questionName }
												
												<!-- 单选题 -->
												<c:if test="${question.questionType==0 }">
													
													<c:forEach items="${question.optionArr }" var="option">
														<input type="radio"/>
														${option }
													</c:forEach>
													
												</c:if>
												
												<!-- 多选题 -->
												<c:if test="${question.questionType==1 }">
													
													<c:forEach items="${question.optionArr }" var="option">
														<input type="checkbox"/>
														${option }
													</c:forEach>
													
												</c:if>
												
												<!-- 简答题 -->
												<c:if test="${question.questionType==2 }">
													
													<input type="text" class="longInput"/>
													
												</c:if>
												
											</td>
											<td>
												<a href="guest/question/removeQuestion/${question.questionId }/${requestScope.survey.surveyId}">删除问题</a>
												<a href="guest/question/toEditUI/${question.questionId }/${requestScope.survey.surveyId}">更新问题</a>
											</td>
										</tr>
										
									</c:forEach>
									
								</c:if>
								
							</table>
						</td>
					</tr>
					
				</c:forEach>
				
			</c:if>
			
			<tr>
				<td colspan="2">
					<a href="guest/survey/complete/${survey.surveyId }">完成调查</a>
				</td>
			</tr>
			
		</table>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>