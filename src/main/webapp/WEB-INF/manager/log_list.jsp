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
		
		显示日志
		
		<table class="dataTable">
			
			<c:if test="${empty page.list }">
				
				<tr>
					<td>暂时还没有日志数据</td>
				</tr>
				
			</c:if>
			
			<c:if test="${!empty page.list }">
				
				<tr>
					<td>ID</td>
					<td>详情</td>
				</tr>
				
				<c:forEach items="${page.list }" var="log">
					
					<tr>
						<td>${log.logId }</td>
						<td>
							<table class="dataTable">
								
								<tr>
									<td>操作时间</td>
									<td>${log.operateTime }</td>
								</tr>
								
								<tr>
									<td>操作人</td>
									<td>${log.operator }</td>
								</tr>
								
								<tr>
									<td>方法名</td>
									<td>${log.methodName }</td>
								</tr>
								
								<tr>
									<td>类名</td>
									<td>${log.typeName }</td>
								</tr>
								
								<tr>
									<td>参数列表</td>
									<td>${log.params }</td>
								</tr>
								
								<tr>
									<td>返回值</td>
									<td>${log.returnValue }</td>
								</tr>
								
								<tr>
									<td>异常类型</td>
									<td>${log.exceptionType }</td>
								</tr>
								
								<tr>
									<td>异常信息</td>
									<td>${log.exceptionMessage }</td>
								</tr>
								
							</table>
						</td>
					</tr>
					
				</c:forEach>
				
				<tr>
					<td colspan="2">
						<c:set var="navigatorUrl" value="manager/log/showLogPage" scope="page"/>
						<%@ include file="/res_jsp/navigator.jsp" %>
					</td>
				</tr>
				
			</c:if>
			
		</table>
		
	</div>
	
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>