package com.qiaosoftware.surveyproject.component.controller.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.AdminService;
import com.qiaosoftware.surveyproject.component.service.RoleService;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.entity.manager.Role;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

@Controller
public class AdminHandler {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/manager/admin/dispatcher")
	public String dispatcher(@RequestParam("adminId") Integer adminId, @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList) {
		
		adminService.updateRelationship(adminId, roleIdList);
		
		return "redirect:/manager/admin/showList";
	}
	
	@RequestMapping("/manager/admin/toDispatcherUI/{adminId}")
	public String toDispatcherUI(
			@PathVariable("adminId") Integer adminId, 
			Map<String, Object> map) {
		
		List<Role> allRoleList = roleService.getAllRoleList();
		List<Integer> currentIdList = adminService.getCurrentIdList(adminId);
		
		map.put("allRoleList", allRoleList);
		map.put("currentIdList", currentIdList);
		map.put("adminId", adminId);
		
		return "manager/dispatcher_admin_role";
	}
	
	@RequestMapping("/manager/auth/saveAdmin")
	public String saveAdmin(Admin admin) {
		
		adminService.saveAdmin(admin);
		
		return "redirect:/manager/admin/showList";
	}
	
	@RequestMapping("/manager/admin/showList")
	public String showList(Map<String, Object> map) {
		
		List<Admin> adminList = adminService.getAllAdminList();
		map.put("adminList", adminList);
		
		return "manager/admin_list";
	}
	
	@RequestMapping("/manager/admin/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "manager/manager_main";
	}
	
	@RequestMapping("/manager/admin/login")
	public String login(Admin admin, HttpSession session) {
		
		//1.检查当前admin是否是超级管理员
		String adminName = admin.getAdminName();
		String adminPwd = admin.getAdminPwd();
		
		if("SuperAdmin".equals(adminName) && "qiao".equals(adminPwd)) {
			
			session.setAttribute(GlobalNames.LOGIN_ADMIN, admin);
			
			return "manager/manager_main";
		}
		
		//2.查询数据库检查是否是普通管理员
		Admin loginAdmin = adminService.login(admin);
		
		//3.如果登录成功，则保存到Session域中
		session.setAttribute(GlobalNames.LOGIN_ADMIN, loginAdmin);
		
		return "manager/manager_main";
		
	}
	
	/*@RequestMapping("/manager/admin/login")
	public String login(Admin admin, HttpSession session) {
		
		//1.验证是否可以登录
		String adminName = admin.getAdminName();
		String adminPwd = admin.getAdminPwd();
		
		if(!"SuperAdmin".equals(adminName) || !"qiao".equals(adminPwd)) {
			//2.如果不能登录，则抛出异常
			throw new AdminLoginErrorExceptioin();
			
		}
		
		//3.将admin保存到Session域中
		session.setAttribute(GlobalNames.LOGIN_ADMIN, admin);
		
		return "manager/manager_main";
	}*/

}
