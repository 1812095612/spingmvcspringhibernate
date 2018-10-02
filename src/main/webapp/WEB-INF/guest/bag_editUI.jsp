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
		
		[更新包裹]
		<form action="guest/bag/updateBag" method="post">
			<!-- hidden标签说明 -->
			<!-- name属性值：控制将数据注入到实体类对象的哪个属性中 -->
			<!-- value属性值：实际提交给服务器的数据 -->
			<!-- survey.surveyId=23 -->
			<input type="hidden" name="survey.surveyId" value="${surveyId }"/>
			<input type="hidden" name="bagId" value="${bag.bagId }"/>
			<%-- <input type="hidden" name="bagOrder" value="${bag.bagOrder }"/> --%>
			<table class="formTable">
				
				<tr>
					<td>包裹名称</td>
					<td>
						<input type="text" name="bagName" class="longInput" value="${bag.bagName}"/>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="submit" value="更新"/>
					</td>
				</tr>
				
			</table>
			
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/guest_bottom.jsp" %>

</body>
</html>