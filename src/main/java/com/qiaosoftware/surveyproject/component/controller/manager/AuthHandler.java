package com.qiaosoftware.surveyproject.component.controller.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.AuthService;
import com.qiaosoftware.surveyproject.component.service.ResService;
import com.qiaosoftware.surveyproject.entity.manager.Auth;
import com.qiaosoftware.surveyproject.entity.manager.Res;

@Controller
public class AuthHandler {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ResService resService;
	
	@RequestMapping("/manager/auth/dispatcher")
	public String dispatcher(@RequestParam("authId") Integer authId, @RequestParam(value="resIdList", required=false) List<Integer> resIdList) {
		
		authService.updateRelationship(authId, resIdList);
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/toDispatcherUI/{authId}")
	public String toDispatcherUI(@PathVariable("authId") Integer authId, Map<String, Object> map) {
		
		//1.准备数据
		List<Res> allResList = resService.getAllResList();
		
		List<Integer> currentResIdList = authService.getCurrentRes(authId);
		
		//2.将数据保存到请求域
		map.put("allResList", allResList);
		map.put("currentResIdList", currentResIdList);
		map.put("authId", authId);
		
		return "manager/dispatcher_auth_res";
	}
	
	@RequestMapping("/manager/auth/batchDelete")
	public String batchDelete(@RequestParam(value="authIdList",required=false) List<Integer> authIdList) {
		
		if(authIdList != null) {
			authService.batchDelete(authIdList);
		}
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/updateAuthName")
	public void updateAuthName(HttpServletResponse response, @RequestParam("authName") String authName, @RequestParam("authId") Integer authId) throws IOException {
		
		authService.updateAuthName(authName, authId);
		
		response.setContentType("text/html;charsert=UTF-8");
		response.getWriter().write("操作成功！");
		
	}
	
	@RequestMapping("/manager/auth/saveAuth")
	public String saveAuth(Auth auth) {
		
		authService.saveEntity(auth);
		
		return "redirect:/manager/auth/showList";
	}
	
	@RequestMapping("/manager/auth/showList")
	public String showList(Map<String, Object> map) {
		
		List<Auth> authList = authService.getAllAuthList();
		map.put("authList", authList);
		
		return "manager/auth_list";
	}
	
}
