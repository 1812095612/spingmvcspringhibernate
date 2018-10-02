package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.AdminDao;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Admin;

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao{

	public List<Admin> getAllAdminList() {
		return getListByHql("From Admin");
	}

	public boolean checkAdminName(String adminName) {
		
		String sql = "select count(*) from survey_admin where admin_name=?";
		
		return getTotalRecordNoBySQL(sql, adminName) > 0;
	}

	public List<Integer> getCurrentIdList(Integer adminId) {
		
		String sql = "SELECT `ROLE_ID` FROM `inner_admin_role` WHERE `admin_id`=?";
		
		return getListBySQL(sql, adminId);
	}

	public void removeOldRelationship(Integer adminId) {
		String sql = "DELETE FROM `inner_admin_role` WHERE `admin_id`=?";
		updateBySQL(sql, adminId);
	}

	public void saveNewRelationship(Integer adminId, List<Integer> roleIdList) {
		String sql = "INSERT INTO `inner_admin_role`(`admin_id`,`ROLE_ID`) VALUES(?,?)";
		
		Object[][] params = new Object[roleIdList.size()][2];
		
		for (int i = 0; i < roleIdList.size(); i++) {
			
			params[i] = new Object[]{adminId, roleIdList.get(i)};
			
		}
		batchUpdate(sql, params);
	}

	public Admin checkAccountPwd(Admin admin) {
		
		String hql = "From Admin a where a.adminName=? and a.adminPwd=?";
		
		return getEntityByHql(hql, admin.getAdminName(), admin.getAdminPwd());
	}

}
