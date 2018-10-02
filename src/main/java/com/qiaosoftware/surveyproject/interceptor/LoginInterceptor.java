package com.qiaosoftware.surveyproject.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.exception.AdminNeededLoginException;
import com.qiaosoftware.surveyproject.exception.UserNeededLoginException;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//0.静态资源直接放行
		if(handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		
		//1.公共资源：项目中不需要登录就可以访问的资源
		Set<String> publicResSet = new HashSet<String>();
		publicResSet.add("/guest/user/toLoginUI");
		publicResSet.add("/guest/user/toRegistUI");
		publicResSet.add("/guest/user/login");
		publicResSet.add("/guest/user/regiest");
		publicResSet.add("/guest/user/logout");
		
		//添加后台相关的公共资源
		publicResSet.add("/manager/admin/toMainUI");
		publicResSet.add("/manager/admin/toLoginUI");
		publicResSet.add("/manager/admin/login");
		publicResSet.add("/manager/admin/logout");
		
		//2.获取当前请求的URL地址
		String servletPath = request.getServletPath();
		
		//3.检查当前请求是否是访问公共资源
		if(publicResSet.contains(servletPath)) {
			
			//4.如果是，则直接放行
			return true;
			
		}
		
		//5.如果不是公共资源，则检查登录状态：区分名称空间
		HttpSession session = request.getSession();
		
		//前台
		if(servletPath.startsWith("/guest")) {
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
			
			if(user == null) {
				
				//6.如果没有登录，则不能执行后续操作
				throw new UserNeededLoginException();
				
			}else{
				
				//7.如果已经登录，则放行
				return true;
				
			}
		}

		//后台
		if(servletPath.startsWith("/manager")) {
			
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			if(admin == null) {
				
				throw new AdminNeededLoginException();
				
			}else{
				
				return true;
				
			}
			
		}
		
		return false;
		
	}

}
