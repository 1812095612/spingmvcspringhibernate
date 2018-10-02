<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
		
		[编辑调查]
		<%-- 我们希望在图片验证失败后返回当前表单页面时，能够回显调查名称，所以使用Spring的表单标签 --%>
		<%-- Spring的表单标签中的form标签要求必须提供modelAttribute属性的值 --%>
		<%-- 提供的方式是将模型对象以survey作为属性名保存到请求域中 --%>
		<form:form action="guest/survey/updateSurvey" 
				   method="post"
				   enctype="multipart/form-data"
				   modelAttribute="survey">
				   
			<form:hidden path="surveyId"/>
			
			<table class="formTable">
			
				<c:if test="${exception != null }">
					<tr>
						<td colspan="2">
							<spring:message code="${exception.class.name }"/>
						</td>
					</tr>
				</c:if>
				
				<tr>
					<td>调查名称</td>
					<td>
						<form:input path="surveyName" cssClass="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>当前Logo图片</td>
					<td>
						<input type="hidden" name="logoPath" value="${survey.logoPath }"/>
						<img src="${survey.logoPath }"/>
					</td>
				</tr>
				
				<tr>
					<td>Logo图片</td>
					<td>
						<%-- 文件上传框的name属性值千万不要用logoPath --%>
						<%-- 如果设置为logoPath，那么SpringMVC就会将文件上传框架提交的文件数据注入到logoPath属性中 --%>
						<%-- 而文件数据又不能设置到一个String类型的属性中，所以会造成类型转换失败，页面上显示400 --%>
						<%-- 所以文件上传框提交的数据需要在handler方法中特殊处理 --%>
						<input type="file" name="logoFile" class="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="更新"/>
					</td>
				</tr>
				
			</table>
			
		</form:form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>