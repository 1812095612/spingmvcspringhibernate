package com.qiaosoftware.surveyproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.qiaosoftware.surveyproject.component.service.ResService;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.entity.manager.Res;
import com.qiaosoftware.surveyproject.exception.AdminNeededLoginException;
import com.qiaosoftware.surveyproject.exception.NoRightException;
import com.qiaosoftware.surveyproject.exception.UserNeededLoginException;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private ResService resService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//1.将静态资源放过
		if(handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		
		//2.获取Session对象
		HttpSession session = request.getSession();
		
		//3.获取ServletPath
		String servletPath = request.getServletPath();
		
		//4.将ServletPath中的多余部分剪掉
		servletPath = DataProcessUtils.cutServletPath(servletPath);
		
		//5.根据ServletPath获取对应的Res对象
		Res res = resService.getResByServletPath(servletPath);
		
		//6.检查是否是公共资源
		if(res.isPublicRes()) {
			//7.如果是公共资源则放行
			return true;
		}
		
		//8.如果当前请求的目标地址是guest部分的
		if(servletPath.startsWith("/guest")) {
			//9.检查User是否登录
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
			if(user == null) {
				//10.如果没有登录则抛出异常
				throw new UserNeededLoginException();
				
			}else{
				//11.已登录则检查权限
				String resCodeArr = user.getResCodeArr();
				boolean hasRight = DataProcessUtils.checkAuthority(res, resCodeArr);
				
				if(hasRight) {
					//12.有权限则放行
					return true;
					
				}else{
					//13.没有权限则抛出异常
					throw new NoRightException();
					
				}
				
			}
			
		}
		
		//14.如果当前请求的目标地址是manager部分的
		if(servletPath.startsWith("/manager")) {
			//15.检查Admin是否登录
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			if(admin == null) {
				//16.如果没有登录则抛出异常
				throw new AdminNeededLoginException();
				
			}else{
				//17.如果已登录则检查是否是超级管理员
				String adminName = admin.getAdminName();
				if("SuperAdmin".equals(adminName)) {
					return true;
				}else{
					//18.如果不是超级管理员，则检查是否具备访问目标资源的权限
					String resCodeArr = admin.getResCodeArr();
					
					boolean hasRight = DataProcessUtils.checkAuthority(res, resCodeArr);
					
					if(hasRight) {
						//19.有权限则放行
						return true;
						
					}else{
						//20.没有权限则抛出异常
						throw new NoRightException();
						
					}
					
				}
				
			}
			
		}
		return true;
	}	
	
}
