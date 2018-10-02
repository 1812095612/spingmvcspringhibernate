package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.manager.Role;

public interface RoleDao extends BaseDao<Role> {

	List<Role> getAllRoleList();

	void updateRoleName(String roleName, Integer roleId);

	void batchDelete(List<Integer> roleIdList);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void removeOldRelationship(Integer roleId);

	void saveNewRelationship(Integer roleId, List<Integer> authIdList);

	Role getRoleByName(String roleName);

}
