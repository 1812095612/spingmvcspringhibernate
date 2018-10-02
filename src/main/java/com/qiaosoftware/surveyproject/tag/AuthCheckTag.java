package com.qiaosoftware.surveyproject.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.qiaosoftware.surveyproject.component.service.ResService;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.entity.manager.Res;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

/**
<qiao:authCheck servletPath="manager/auth/showList">
	[<a href="manager/auth/showList">权限列表</a>]
</qiao:authCheck>
 */
public class AuthCheckTag extends SimpleTagSupport{
	
	private String servletPath;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//1.准备工作
		PageContext pageContext = (PageContext) getJspContext();
		HttpSession session = pageContext.getSession();
		ServletContext servletContext = pageContext.getServletContext();
		
		//手动获取当前环境下的IOC容器对象，因为当前自定义标签的类不是IOC容器创建的，需要的组件不能自动注入
		WebApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		ResService resService = ioc.getBean(ResService.class);
		
		//2.根据servletPath查询对应的Res对象
		Res res = resService.getResByServletPath(servletPath);
		
		//3.检查是否是公共资源
		if(res.isPublicRes()) {
			//4.执行标签体
			getJspBody().invoke(null);
			
			return ;
		}
		
		//5.检查登录情况
		if(servletPath.startsWith("/guest")) {
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
			if(user != null) {
				
				//6.已登录则检查权限
				String resCodeArr = user.getResCodeArr();
				boolean hasRight = DataProcessUtils.checkAuthority(res, resCodeArr);
				
				if(hasRight) {
					getJspBody().invoke(null);
					
					return ;
				}
				
			}
		}
		
		if(servletPath.startsWith("/manager")) {
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			if(admin != null) {
				
				String adminName = admin.getAdminName();
				
				if("SuperAdmin".equals(adminName)) {
					getJspBody().invoke(null);
					
					return ;
				}
				
				String resCodeArr = admin.getResCodeArr();
				boolean hasRight = DataProcessUtils.checkAuthority(res, resCodeArr);
				if(hasRight) {
					getJspBody().invoke(null);
					
					return ;
				}
				
			}
			
		}
		
	}
	
	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

}
