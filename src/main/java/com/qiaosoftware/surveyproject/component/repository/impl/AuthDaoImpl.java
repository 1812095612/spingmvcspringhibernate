package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.AuthDao;
import com.qiaosoftware.surveyproject.entity.manager.Auth;

@Repository
public class AuthDaoImpl extends BaseDaoImpl<Auth> implements AuthDao{

	public List<Auth> getAllAuthList() {
		return getListByHql("From Auth");
	}

	public void updateAuthName(String authName, Integer authId) {
		String hql = "update Auth a set a.authName=? where a.authId=?";
		updateByHql(hql, authName, authId);
	}

	public void batchDelete(List<Integer> authIdList) {
		String sql = "delete from survey_auth where auth_id=?";
		Object[][] params = new Object[authIdList.size()][1];
		for (int i = 0; i < authIdList.size(); i++) {
			params[i] = new Object[]{authIdList.get(i)};
		}
		batchUpdate(sql, params);
	}

	public List<Integer> getCurrentRes(Integer authId) {
		
		String sql = "select res_id from inner_auth_res where auth_id=?";
		
		return getListBySQL(sql, authId);
	}

	public void removeOldRelationship(Integer authId) {
		String sql = "DELETE FROM `inner_auth_res` WHERE auth_id=?";
		updateBySQL(sql, authId);
	}

	public void saveNewRelationship(Integer authId, List<Integer> resIdList) {
		String sql = "INSERT INTO `inner_auth_res`(`auth_id`,`RES_ID`) VALUES(?,?)";
		Object[][] params = new Object[resIdList.size()][2];
		for (int i = 0; i < resIdList.size(); i++) {
			
			params[i] = new Object[]{authId,resIdList.get(i)};
			
		}
		batchUpdate(sql, params);
	}

}
