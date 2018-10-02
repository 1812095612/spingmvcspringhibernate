package com.qiaosoftware.surveyproject.component.service;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.manager.Admin;

public interface AdminService extends BaseService<Admin>{

	List<Admin> getAllAdminList();

	void saveAdmin(Admin admin);

	List<Integer> getCurrentIdList(Integer adminId);

	void updateRelationship(Integer adminId, List<Integer> roleIdList);

	Admin login(Admin admin);

}
