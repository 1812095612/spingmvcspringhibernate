package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.RoleDao;
import com.qiaosoftware.surveyproject.entity.manager.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{

	public List<Role> getAllRoleList() {
		
		return getListByHql("From Role");
	}

	public void updateRoleName(String roleName, Integer roleId) {
		
		String hql = "update Role r set r.roleName=? where r.roleId=?";
		updateByHql(hql, roleName, roleId);
		
	}

	public void batchDelete(List<Integer> roleIdList) {
		String sql = "delete from survey_role where role_id=?";
		Object[][] params = new Object[roleIdList.size()][1];
		for(int i = 0; i < roleIdList.size(); i++) {
			params[i] = new Object[]{roleIdList.get(i)};
		}
		batchUpdate(sql, params);
	}

	public List<Integer> getCurrentAuthIdList(Integer roleId) {
		
		String sql = "SELECT auth_id FROM `inner_role_auth` WHERE role_id=?";
		
		return getListBySQL(sql, roleId);
	}

	public void removeOldRelationship(Integer roleId) {
		
		String sql = "DELETE FROM `inner_role_auth` WHERE role_id=?";
		
		updateBySQL(sql, roleId);
		
	}

	public void saveNewRelationship(Integer roleId, List<Integer> authIdList) {
		
		String sql = "INSERT INTO `inner_role_auth`(`ROLE_ID`,`AUTH_ID`) VALUES(?,?)";
		
		Object[][] params = new Object[authIdList.size()][2];
		
		for(int i = 0; i < authIdList.size(); i++) {
			
			params[i] = new Object[]{roleId, authIdList.get(i)};
			
		}
		
		batchUpdate(sql, params);
		
	}

	public Role getRoleByName(String roleName) {
		
		String hql = "From Role r where r.roleName=?";
		
		return getEntityByHql(hql, roleName);
	}

}
