package com.qiaosoftware.surveyproject.component.controller.guest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qiaosoftware.surveyproject.component.service.UserService;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

@Controller
public class UserHandler {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/guest/user/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/user/login")
	public String login(User user, HttpSession session) {
		
		//提供信息，告诉determine方法此时需要连接主数据库
		//RoutingToken.bindToken("DATASOURCE_MAIN");
		
		User loginUser = userService.login(user);
		
		session.setAttribute(GlobalNames.LOGIN_USER, loginUser);
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/guest/user/regiest")
	public String regist(User user) {
		
		userService.regist(user);
		
		return "guest/user_loginUI";
	}

}
