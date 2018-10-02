package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.ResDao;
import com.qiaosoftware.surveyproject.component.repository.RoleDao;
import com.qiaosoftware.surveyproject.component.repository.UserDao;
import com.qiaosoftware.surveyproject.component.service.UserService;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Role;
import com.qiaosoftware.surveyproject.exception.UserNameAlreadyExistsException;
import com.qiaosoftware.surveyproject.exception.UserNameAndPwdNotMatchException;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ResDao resDao;

	public void regist(User user) {
		
		//1.检查用户名是否存在
		String userName = user.getUserName();
		boolean available = userDao.checkUserNameAvailable(userName);
		
		//2.如果用户名不可用，则抛出异常：UserNameAlreadyExistsException
		if(!available) {
			throw new UserNameAlreadyExistsException();
		}
		
		//※补充：对密码进行加密
		String userPwd = user.getUserPwd();
		userPwd = DataProcessUtils.md5(userPwd);
		user.setUserPwd(userPwd);
		
		//※补充：建立User到Role的关联关系
		//1.创建一个集合用来保存角色对象
		Set<Role> roleSet = new HashSet<Role>();
		
		//2.检查用户类型
		boolean company = user.isCompany();
		
		Role role = null;
		
		//2.根据用户类型查询对应的角色
		if(company) {
			
			role = roleDao.getRoleByName("企业用户");
			
		}else{
			
			role = roleDao.getRoleByName("个人用户");
			
		}
		
		roleSet.add(role);
		
		//注意：要设置到user对象中才能够保存关联关系
		user.setRoleSet(roleSet);
		
		//3.计算用户的权限码
		Integer maxPos = resDao.getMaxPos();
		
		int[] codeArr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
		
		String resCodeArr = DataProcessUtils.converIntArr(codeArr);
		
		user.setResCodeArr(resCodeArr);
		
		//将user对象保存到数据库中
		userDao.saveEntity(user);
		
	}

	public User login(User user) {
		
		//对密码进行加密，比较加密之后的字符串
		String userPwd = user.getUserPwd();
		userPwd = DataProcessUtils.md5(userPwd);
		user.setUserPwd(userPwd);
		
		User loginUser = userDao.getUserByUserNamePwd(user);
		
		if(loginUser == null) {
			throw new UserNameAndPwdNotMatchException();
		}
		
		return loginUser;
	}

}
