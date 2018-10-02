package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.manager.Auth;

public interface AuthDao extends BaseDao<Auth>{

	List<Auth> getAllAuthList();

	void updateAuthName(String authName, Integer authId);

	void batchDelete(List<Integer> authIdList);

	List<Integer> getCurrentRes(Integer authId);

	void removeOldRelationship(Integer authId);

	void saveNewRelationship(Integer authId, List<Integer> resIdList);

}
