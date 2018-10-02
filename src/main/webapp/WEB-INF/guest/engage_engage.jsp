<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qiao.com/survey" prefix="qiao" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[参与调查]
		<table class="dataTable">
			
			<tr>
				<td colspan="2">调查的基本信息</td>
			</tr>
			<tr>
				<td><img src="${sessionScope.currentSurvey.logoPath }"/></td>
				<td>${sessionScope.currentSurvey.surveyName }</td>
			</tr>
			
		</table>
		
		<br/><br/>

		<form action="guest/engage/engage" method="post">
		
			<input type="hidden" name="currentIndex" value="${requestScope.currentIndex }"/>
			<input type="hidden" name="bagId" value="${currentBag.bagId }"/>
		
			<table class="dataTable">
				<tr>
					<td colspan="2">当前包裹</td>
				</tr>
				<tr>
					<td>${requestScope.currentBag.bagName }</td>
					<td>问题详情</td>
				</tr>
				
				<tr>
					<td></td>
					<td>
						<table class="dataTable">
							
							<c:forEach items="${requestScope.currentBag.questionSet }" var="question">
								
								<tr>
									<td>${question.questionName }</td>
									<td>
										<!-- 单选题 -->
										<c:if test="${question.questionType==0 }">
											<c:forEach items="${question.optionArr }" var="option" varStatus="optionStatus">
												<input id="labelId${question.questionId }${optionStatus.index }" type="radio" name="q${question.questionId }" value="${optionStatus.index }"
													<%-- 通过自定义标签实现回显 --%>
													<qiao:determineRedisplay 
														bagId="${currentBag.bagId }" 
														redisplayName="q${question.questionId }" 
														questionType="${question.questionType }" 
														optionIndex="${optionStatus.index }"/>
												/>
												<label for="labelId${question.questionId }${optionStatus.index }">${option }</label>
											</c:forEach>
										</c:if>
										
										<!-- 多选题 -->
										<c:if test="${question.questionType==1 }">
											<c:forEach items="${question.optionArr }" var="option" varStatus="optionStatus">
												<input id="labelId${question.questionId }${optionStatus.index }" type="checkbox" name="q${question.questionId }" value="${optionStatus.index }"
													<qiao:determineRedisplay bagId="${currentBag.bagId }" redisplayName="q${question.questionId }" questionType="${question.questionType }" optionIndex="${optionStatus.index }"/>
												/>
												<label for="labelId${question.questionId }${optionStatus.index }">${option }</label>
											</c:forEach>
										</c:if>
										
										<!-- 简答题 -->
										<c:if test="${question.questionType==2 }">
											<input type="text" name="q${question.questionId }" class="longInput" value='<qiao:determineRedisplay questionType="${question.questionType }" bagId="${currentBag.bagId }" redisplayName="q${question.questionId }"/>'/>
										</c:if>
									</td>
								</tr>
								
							</c:forEach>
							
						</table>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<c:if test="${requestScope.currentIndex>0 }">
							<input type="submit" name="submit_prev" value="返回上一个包裹"/>
						</c:if>
						
						<c:if test="${requestScope.currentIndex<sessionScope.bagListSize-1 }">
							<input type="submit" name="submit_next" value="进入下一个包裹"/>
						</c:if>
						
						<c:if test="${requestScope.currentIndex==sessionScope.bagListSize-1 }">
							<input type="submit" name="submit_done" value="完成"/>
						</c:if>
						
						<input type="submit" name="submit_quit" value="放弃">
						
					</td>
				</tr>
				
			</table>
		</form>		
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>