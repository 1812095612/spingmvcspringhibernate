package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.ResDao;
import com.qiaosoftware.surveyproject.entity.manager.Res;

@Repository
public class ResDaoImpl extends BaseDaoImpl<Res> implements ResDao{

	public boolean checkServletPathExists(String servletPath) {

		String sql = "select count(*) from survey_res where res_name=?";
		
		return getTotalRecordNoBySQL(sql, servletPath) > 0;
	}

	public Integer getMaxPos() {
		
		String hql = "select max(r.resPos) from Res r";
		
		Object result = getQuery(hql).uniqueResult();
		
		if(result == null) {
			return null;
		}else{
			return (Integer) result;
		}
		
	}

	public Integer getMaxCode(Integer maxPos) {
		
		String hql = "select max(r.resCode) from Res r where r.resPos=?";
		
		Object result = getQuery(hql, maxPos).uniqueResult();
		
		if(result == null) {
			return null;
		}else{
			return (Integer) result;
		}
		
	}

	public List<Res> getAllResList() {
		
		String hql = "From Res r order by r.resName";
		
		return getListByHql(hql);
	}

	public void batchDelete(List<Integer> resIdList) {
		
		String sql = "delete from survey_res where res_id=?";
		
		Object[][] params = new Object[resIdList.size()][1];
		
		for(int i = 0; i < resIdList.size(); i++) {
			Integer resId = resIdList.get(i);
			params[i] = new Object[]{resId};
		}
		
		batchUpdate(sql, params);
	}

	public void updateResName(String resName, Integer resId) {
		String sql = "update survey_res set res_name=? where res_id=?";
		updateBySQL(sql, resName, resId);
	}

	public void updateResStatus(Integer resId) {
		String sql = "UPDATE survey_res r SET r.PUBLIC_RES = !r.PUBLIC_RES WHERE r.res_id=?";
		updateBySQL(sql, resId);
	}

	public Res getResByServletPath(String servletPath) {
		
		String hql = "From Res r where r.resName=?";
		
		return getEntityByHql(hql, servletPath);
	}

}
