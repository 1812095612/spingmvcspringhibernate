package com.qiaosoftware.surveyproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.qiaosoftware.surveyproject.component.service.ResService;
import com.qiaosoftware.surveyproject.entity.manager.Res;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

public class GenerateResInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private ResService resService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//1.检查是否是静态资源，如果是则直接放行
		if(handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		
		//2.获取当前请求的servletPath
		String servletPath = request.getServletPath();
		
		//3.切割
		servletPath = DataProcessUtils.cutServletPath(servletPath);
		
		//4.检查servletPath是否已经保存到数据库中了，如果保存过则放行
		boolean resExists = resService.checkServletPathExists(servletPath);
		
		if(resExists) {
			return true;
		}
		
		//5.将servletPath封装到Res对象中保存到数据库
		//关键：生成权限码和权限位
		//①声明两个变量，用于保存最终确定的权限码和权限位
		Integer resCodeFinal = null;
		Integer resPosFinal = null;
		
		//②声明一个变量用于保存当前系统允许的最大的权限码的值，便于后面比较
		int systemMaxCode = 1 << 30;
		
		//③查询当前系统中实际的最大权限位
		Integer maxPos = resService.getMaxPos();
		
		//④查询当前最大权限位范围内的最大权限码
		Integer maxCode = (maxPos == null)? null : resService.getMaxCode(maxPos);
		
		//⑤分具体情况给最终变量赋值
		if(maxPos == null && maxCode == null) {
			//[1]系统中尚未保存过任何资源
			resCodeFinal = 1;
			resPosFinal = 0;
			
		}else if(maxCode < systemMaxCode) {
			
			//[2]系统有保存过的资源，且权限码没有达到极限
			resPosFinal = maxPos;
			resCodeFinal = maxCode << 1;
			
		}else{
			
			//[3]系统有保存过的资源，且权限码已经达到极限
			resCodeFinal = 1;
			resPosFinal = maxPos + 1;
			
		}
		
		//⑥封装Res对象
		Res res = new Res(null, servletPath, resCodeFinal, resPosFinal, false);
		resService.saveEntity(res);
		
		return true;
	}

}

























































