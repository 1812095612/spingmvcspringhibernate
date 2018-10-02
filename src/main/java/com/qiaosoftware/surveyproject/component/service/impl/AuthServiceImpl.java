package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.AdminDao;
import com.qiaosoftware.surveyproject.component.repository.AuthDao;
import com.qiaosoftware.surveyproject.component.repository.ResDao;
import com.qiaosoftware.surveyproject.component.repository.UserDao;
import com.qiaosoftware.surveyproject.component.service.AuthService;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.entity.manager.Auth;
import com.qiaosoftware.surveyproject.entity.manager.Role;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

@Service
public class AuthServiceImpl extends BaseServiceImpl<Auth> implements AuthService{

	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResDao resDao;
	
	@Autowired
	private UserDao userDao;
	
	public List<Auth> getAllAuthList() {
		
		return authDao.getAllAuthList();
	}

	public void updateAuthName(String authName, Integer authId) {
		authDao.updateAuthName(authName, authId);
	}

	public void batchDelete(List<Integer> authIdList) {
		authDao.batchDelete(authIdList);
	}

	public List<Integer> getCurrentRes(Integer authId) {
		return authDao.getCurrentRes(authId);
	}

	public void updateRelationship(Integer authId, List<Integer> resIdList) {
		
		//1.将旧的关联关系删除
		authDao.removeOldRelationship(authId);
		
		//2.将新的关联关系保存
		if(resIdList != null && resIdList.size() != 0) {
			authDao.saveNewRelationship(authId, resIdList);
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
