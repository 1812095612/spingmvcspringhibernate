package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.AdminDao;
import com.qiaosoftware.surveyproject.component.repository.ResDao;
import com.qiaosoftware.surveyproject.component.service.AdminService;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.entity.manager.Role;
import com.qiaosoftware.surveyproject.exception.AdminAlreadyExistsException;
import com.qiaosoftware.surveyproject.exception.AdminLoginErrorExceptioin;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService{
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ResDao resDao;

	public List<Admin> getAllAdminList() {
		return adminDao.getAllAdminList();
	}

	public void saveAdmin(Admin admin) {
		
		String adminName = admin.getAdminName();
		
		boolean exists = adminDao.checkAdminName(adminName);
		
		if(exists) {
			throw new AdminAlreadyExistsException();
		}
		
		String adminPwd = admin.getAdminPwd();
		adminPwd = DataProcessUtils.md5(adminPwd);
		admin.setAdminPwd(adminPwd);
		
		adminDao.saveEntity(admin);
		
	}

	public List<Integer> getCurrentIdList(Integer adminId) {
		return adminDao.getCurrentIdList(adminId);
	}

	public void updateRelationship(Integer adminId, List<Integer> roleIdList) {
		
		adminDao.removeOldRelationship(adminId);
		
		if(roleIdList != null && roleIdList.size() != 0) {
			adminDao.saveNewRelationship(adminId, roleIdList);
		}
		
		//特有的操作：计算权限码数组
		//1.查询Admin对象
		Admin admin = adminDao.getEntity(adminId);
		
		//2.计算权限码数组
		//查询当前系统中最大的权限位的值
		Integer maxPos = resDao.getMaxPos();
		
		Set<Role> roleSet = admin.getRoleSet();
		
		//int类型数组中所有元素的默认值都是0
		int [] codeArr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
		
		//3.转换
		String resCodeArrStr = DataProcessUtils.converIntArr(codeArr);
		
		//4.设置到Admin对象中
		admin.setResCodeArr(resCodeArrStr);
	}

	public Admin login(Admin admin) {
		
		String adminPwd = admin.getAdminPwd();
		adminPwd = DataProcessUtils.md5(adminPwd);
		admin.setAdminPwd(adminPwd);
		
		Admin loginAdmin = adminDao.checkAccountPwd(admin);
		
		if(loginAdmin == null) {
			throw new AdminLoginErrorExceptioin();
		}
		
		return loginAdmin;
	}

}

























