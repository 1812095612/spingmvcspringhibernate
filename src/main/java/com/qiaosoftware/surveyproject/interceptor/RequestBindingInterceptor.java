package com.qiaosoftware.surveyproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qiaosoftware.surveyproject.aspect.RequestBinder;

public class RequestBindingInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//将Request对象绑定到当前线程上
		RequestBinder.bindingRequest(request);
		System.out.println("将Request对象绑定到当前线程上");
		return true;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//将Request对象从当前线程上移除
		RequestBinder.removeRequest();
		System.out.println("将Request对象从当前线程上移除");
	}

}
