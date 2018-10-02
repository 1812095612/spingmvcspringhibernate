package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.UserDao;
import com.qiaosoftware.surveyproject.entity.guest.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	public boolean checkUserNameAvailable(String userName) {
		
		String hql = "select count(*) from User u where u.userName=?";
		//String sql = "select count(*) from survey_user where user_name=?";
		
		return getTotalRecordNoByHql(hql, userName) == 0;
	}

	public User getUserByUserNamePwd(User user) {
		
		String hql = "From User u where u.userName=? and u.userPwd=?";
		
		return getEntityByHql(hql, user.getUserName(), user.getUserPwd());
	}

	public List<User> getAllUserList() {
		
		String hql = "From User";
		
		return getListByHql(hql);
	}

}
