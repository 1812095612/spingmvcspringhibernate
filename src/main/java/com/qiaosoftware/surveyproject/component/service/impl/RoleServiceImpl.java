package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.AdminDao;
import com.qiaosoftware.surveyproject.component.repository.ResDao;
import com.qiaosoftware.surveyproject.component.repository.RoleDao;
import com.qiaosoftware.surveyproject.component.repository.UserDao;
import com.qiaosoftware.surveyproject.component.service.RoleService;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.entity.manager.Role;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResDao resDao;
	
	@Autowired
	private UserDao userDao;

	public List<Role> getAllRoleList() {
		return roleDao.getAllRoleList();
	}

	public void updateRoleName(String roleName, Integer roleId) {
		roleDao.updateRoleName(roleName, roleId);
	}

	public void batchDelete(List<Integer> roleIdList) {
		roleDao.batchDelete(roleIdList);
	}

	public List<Integer> getCurrentAuthIdList(Integer roleId) {
		return roleDao.getCurrentAuthIdList(roleId);
	}

	public void updateRelationship(Integer roleId, List<Integer> authIdList) {
		
		//1.删除旧的关联关系
		roleDao.removeOldRelationship(roleId);
		
		//2.保存新的关联关系
		if(authIdList != null && authIdList.size() != 0) {
			roleDao.saveNewRelationship(roleId, authIdList);
		}
		
		//※补充：重新计算所有用户的权限码
		Integer maxPos = resDao.getMaxPos();
		
		//一、admin重新计算
		//1.查询所有admin
		List<Admin> adminList = adminDao.getAllAdminList();
		
		//2.遍历集合
		for (Admin admin : adminList) {
			
			//3.重新计算
			Set<Role> roleSet = admin.getRoleSet();
			int[] codeArr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
			String resCodeArr = DataProcessUtils.converIntArr(codeArr);
			admin.setResCodeArr(resCodeArr);
			
		}
		
		//二、user重新计算
		//1.查询所有user
		List<User> userList = userDao.getAllUserList();
		
		//2.遍历集合
		for (User user : userList) {
			
			//3.重新计算
			Set<Role> roleSet = user.getRoleSet();
			int[] codeArr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
			String resCodeArr = DataProcessUtils.converIntArr(codeArr);
			user.setResCodeArr(resCodeArr);
			
		}
		
	}

}
