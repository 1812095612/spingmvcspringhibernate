<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="logoDiv" class="borderDiv">尚硅谷在线调查系统</div>

<div id="topDiv" class="borderDiv">
	
	<%-- 1.检测当前是否有用户登录 --%>
	<c:if test="${sessionScope.loginUser == null }">
		<%-- 2.如果没有登录，则显示“登录”、“注册”超链接 --%>
		[<a href="guest/user/toLoginUI">登录</a>]
		[<a href="guest/user/toRegistUI">注册</a>]
	</c:if>
	<c:if test="${sessionScope.loginUser != null }">
		<%-- 3.如果有用户登录，则首先显示欢迎信息 --%>
		[欢迎您：${sessionScope.loginUser.userName }]
		
		<%-- 4.检测当前用户是企业用户还是个人用户 --%>
		<%-- user.isCompany() --%>
		<c:if test="${sessionScope.loginUser.company }">
			
			<%-- 5.如果是企业用户，则显示“创建调查”、“我未完成的调查”超链接 --%>
			[<a href="guest/survey/toAddUI">创建调查</a>]
			[<a href="guest/survey/showMyUncompletedSurvey">我未完成的调查</a>]
			
		</c:if>
		
		<%-- 6.显示不论企业用户还是个人用户都可以访问的超链接 --%>
		[<a href="guest/engage/showAllAvailableSurvey">参与调查</a>]
		[<a href="guest/user/logout">退出登录</a>]
		
	</c:if>
	
	<%-- 不论当前是否有用户登录，都显示“首页”超链接 --%>
	[<a href="index.jsp">首页</a>]
	
</div>