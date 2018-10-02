package com.qiaosoftware.surveyproject.component.service;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.manager.Role;

public interface RoleService extends BaseService<Role>{

	List<Role> getAllRoleList();

	void updateRoleName(String roleName, Integer roleId);

	void batchDelete(List<Integer> roleIdList);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void updateRelationship(Integer roleId, List<Integer> authIdList);

}
