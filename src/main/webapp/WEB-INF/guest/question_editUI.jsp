<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		
		//在页面刚加载时，检测被选中的是哪一个题型
		var type = $(":radio:checked").val();
		console.log("type="+type);
		
		//判断type的值
		if(type == 2) {
			$("#optionTr").hide();
		}
		
		//根据选择情况进行切换
		$(":radio").click(function(){
			
			//检查当前点击的是“单/多选”还是“简答”
			var type = this.value;
			
			if(type == 2) {
				
				//简答题
				$("#optionTr").hide();
				
			}else{
				
				//单/多选题
				$("#optionTr").show();
				
			}
			
		});
		
	});
	
</script>
</head>
<body>

	<%@ include file="/res_jsp/guest_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		[编辑问题]
		<form:form action="guest/question/updateQuestion" method="post" modelAttribute="question">
			
			<!-- 在实体类的各个属性以外还想再通过表单元素来保持数据，那么就只能使用HTML标签 -->
			<%-- <form:hidden path="surveyId"/> --%>
			<input type="hidden" name="surveyId" value="${surveyId }"/>
			<form:hidden path="questionId"/>
		
			<table class="formTable">
				
				<tr>
					<td>题干</td>
					<td>
						<form:input path="questionName" cssClass="longInput"/>
					</td>
				</tr>
				
				<tr>
					<td>题型</td>
					<td>
						<form:radiobuttons path="questionType" items="${radioMap }"/>
					</td>
				</tr>
				
				<tr id="optionTr">
					<td>选项</td>
					<td>
						<%-- form:textarea must be empty, but is not --%>
						<%-- <form:textarea path="options" cssClass="multiInput">${question.optionForEdit }</form:textarea> --%>
						<textarea name="options" class="multiInput">${question.optionForEdit }</textarea>
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