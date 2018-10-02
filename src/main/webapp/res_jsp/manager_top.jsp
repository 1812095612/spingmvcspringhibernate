<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qiao.com/survey" prefix="qiao" %>
<div id="logoDiv" class="borderDiv">尚硅谷在线调查系统</div>

<div id="topDiv" class="borderDiv">
	
	<%-- 判断管理员是否已经登录 --%>
	<c:if test="${sessionScope.loginAdmin == null }">
		
		[<a href="manager/admin/toLoginUI">登录</a>]
		
	</c:if>
	<c:if test="${sessionScope.loginAdmin != null }">
		
		[欢迎您：${sessionScope.loginAdmin.adminName }]
		[<a href="manager/admin/logout">退出登录</a>]
		
		<qiao:authCheck servletPath="/manager/statistics/showAllSurvey">
			[<a href="manager/statistics/showAllSurvey">统计调查</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/res/showList">
			[<a href="manager/res/showList">资源列表</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/auth/toAddUI">
			[<a href="manager/auth/toAddUI">创建权限</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/auth/showList">
			[<a href="manager/auth/showList">权限列表</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/role/toAddUI">
			[<a href="manager/role/toAddUI">创建角色</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/role/showList">
			[<a href="manager/role/showList">角色列表</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/admin/toAddUI">
			[<a href="manager/admin/toAddUI">创建账号</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/admin/showList">
			[<a href="manager/admin/showList">账号列表</a>]
		</qiao:authCheck>
		
		<qiao:authCheck servletPath="/manager/log/showLogPage">
			[<a href="manager/log/showLogPage">查看日志</a>]
		</qiao:authCheck>
		
	</c:if>
	
	<%-- 不论当前是否有用户登录，都显示“首页”超链接 --%>
	[<a href="manager/admin/toMainUI">后台首页</a>]
	
</div>