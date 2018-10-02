package com.qiaosoftware.surveyproject.component.controller.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.AuthService;
import com.qiaosoftware.surveyproject.component.service.RoleService;
import com.qiaosoftware.surveyproject.entity.manager.Auth;
import com.qiaosoftware.surveyproject.entity.manager.Role;

@Controller
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/manager/role/dispatcher")
	public String dispatcher(
			@RequestParam("roleId") Integer roleId, 
			@RequestParam(value="authIdList", required=false) List<Integer> authIdList) {
		
		roleService.updateRelationship(roleId, authIdList);
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/toDispatcherUI/{roleId}")
	public String toDispatcherUI(@PathVariable("roleId") Integer roleId, Map<String,Object> map) {
		
		List<Auth> allAuthList = authService.getAllAuthList();
		List<Integer> currentIdList = roleService.getCurrentAuthIdList(roleId);
		
		map.put("allAuthList", allAuthList);
		map.put("currentIdList", currentIdList);
		map.put("roleId", roleId);
		
		return "manager/dispatcher_role_auth";
	}
	
	@RequestMapping("/manager/role/batchDelete")
	public String batchDelete(@RequestParam("roleIdList") List<Integer> roleIdList) {
		
		roleService.batchDelete(roleIdList);
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/updateRoleName")
	public void updateRoleName(@RequestParam("roleName") String roleName, @RequestParam("roleId") Integer roleId) {
		
		roleService.updateRoleName(roleName, roleId);
		
	}
	
	@RequestMapping("/manager/role/saveRole")
	public String saveRole(Role role) {
		
		roleService.saveEntity(role);
		
		return "redirect:/manager/role/showList";
	}
	
	@RequestMapping("/manager/role/showList")
	public String showList(Map<String, Object> map) {
		
		List<Role> roleList = roleService.getAllRoleList();
		map.put("roleList", roleList);
		
		return "manager/role_list";
	}

}
