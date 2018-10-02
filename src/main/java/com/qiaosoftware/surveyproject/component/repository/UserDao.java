package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.guest.User;

public interface UserDao extends BaseDao<User>{

	boolean checkUserNameAvailable(String userName);

	User getUserByUserNamePwd(User user);

	List<User> getAllUserList();

}
