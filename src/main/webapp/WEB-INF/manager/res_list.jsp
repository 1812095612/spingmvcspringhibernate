<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/res_jsp/base.jsp" %>
<script type="text/javascript">
	
	$(function(){
		
		$(":text").change(function(){
			
			//1.获取当前资源名称
			var resName = $.trim(this.value);
			var id = this.id;
			
			if(resName == "") {
				this.value = this.defaultValue;
				return ;
			}
			
			//2.准备Ajax操作需要的参数
			var url = "${pageContext.request.contextPath}/manager/res/updateResName";
			var paramData = {"resName":resName,"resId":id,"time":new Date()};
			var callBack = function(respData){
				
			};
			var type = "text";
			
			//3.发送Ajax请求
			$.post(url, paramData, callBack, type);
			
		});
		
		$(".toggleStatus").click(function(){
			
			//this关键字在这里才能够代表我们点击的按钮
			var btnThis = this;
			
			//1.准备数据
			var resId = this.id;
			
			//2.准备Ajax参数
			var url = "${pageContext.request.contextPath}/manager/res/updateResStatus";
			var paramData = {"resId":resId,"time":new Date()};
			var callBack = function(respData){
				//JavaScript中this关键字代表的对象是：调用当前函数的那个对象
				//this.value = respData;
				btnThis.value = respData;
			};
			var type = "text";
			
			//3.发送Ajax请求
			$.post(url, paramData, callBack, type);
			
		});
		
	});
	
</script>
</head>
<body>

	<%@ include file="/res_jsp/manager_top.jsp" %>
	
	<div id="mainDiv" class="borderDiv">
		
		<h2>[显示资源列表]</h2>
		<form action="manager/res/batchDelete" method="post">
			<table class="dataTable">
				
				<c:if test="${empty resList }">
					
					<tr>
						<td>当前尚未捕获到任何资源</td>
					</tr>
					
				</c:if>
				
				<c:if test="${!empty resList }">
					<tr>
						<td>ID</td>
						<td>Res Name</td>
						<td>资源状态</td>
						<td>删除</td>
					</tr>
					
					<c:forEach items="${resList }" var="res">
						
						<tr>
							<td>${res.resId }</td>
							<td>
								${res.resName }
								<%-- <input id="${res.resId }" type="text" class="longInput" value="${res.resName }"/> --%>
							</td>
							<td>
								<c:if test="${res.publicRes }">
									<input class="toggleStatus" id="${res.resId }" type="button" value="公共资源"/>
								</c:if>
								<c:if test="${!res.publicRes }">
									<input class="toggleStatus" id="${res.resId }" type="button" value="受保护资源"/>
								</c:if>
							</td>
							<td>
								<input type="checkbox" name="resIdList" value="${res.resId }"/>
							</td>
						</tr>
						
					</c:forEach>
					
					<tr>
						<td colspan="4">
							<input type="submit" value="批量删除"/>
						</td>
					</tr>
					
				</c:if>
				
			</table>
		</form>
		
	</div>
	
	<%@ include file="/res_jsp/manager_bottom.jsp" %>

</body>
</html>