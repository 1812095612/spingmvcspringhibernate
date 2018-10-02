package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.manager.Admin;

public interface AdminDao extends BaseDao<Admin>{

	List<Admin> getAllAdminList();

	boolean checkAdminName(String adminName);

	List<Integer> getCurrentIdList(Integer adminId);

	void removeOldRelationship(Integer adminId);

	void saveNewRelationship(Integer adminId, List<Integer> roleIdList);

	Admin checkAccountPwd(Admin admin);

}
