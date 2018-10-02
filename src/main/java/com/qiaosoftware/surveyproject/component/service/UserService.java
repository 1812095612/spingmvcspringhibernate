package com.qiaosoftware.surveyproject.component.service;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.guest.User;

public interface UserService extends BaseService<User>{

	void regist(User user);

	User login(User user);

}
