<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	$(function(){
		$("#goTo").change(function(){
			//1.获取当前文本框中输入的页码值
			var pageNo = $.trim(this.value);
			//2.校验页码
			if(pageNo == "" || isNaN(pageNo)) {
				this.value = this.defaultValue;
				return ;
			}
			//3.页面跳转
			window.location.href = 
				"${pageContext.request.contextPath}/${pageScope.navigatorUrl }?pageNoStr="+pageNo;
		});
	});
</script>
<c:if test="${page.hasPrev }">
	<a href="${pageScope.navigatorUrl }?pageNoStr=1">首页</a>
	<a href="${pageScope.navigatorUrl }?pageNoStr=${page.prev }">上一页</a>
</c:if>

<input class="shortInput" type="text" id="goTo" value="${page.pageNo }"/>
/${page.totalPageNo }
共${page.totalRecordNo }条记录
<%-- [${pageScope.navigatorUrl }] --%>

<c:if test="${page.hasNext }">
	<a href="${pageScope.navigatorUrl }?pageNoStr=${page.next }">下一页</a>
	<a href="${pageScope.navigatorUrl }?pageNoStr=${page.totalPageNo }">末页</a>
</c:if>