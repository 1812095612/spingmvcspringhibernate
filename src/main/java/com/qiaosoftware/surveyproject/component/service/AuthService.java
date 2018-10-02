package com.qiaosoftware.surveyproject.component.service;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.manager.Auth;

public interface AuthService extends BaseService<Auth>{

	List<Auth> getAllAuthList();

	void updateAuthName(String authName, Integer authId);

	void batchDelete(List<Integer> authIdList);

	List<Integer> getCurrentRes(Integer authId);

	void updateRelationship(Integer authId, List<Integer> resIdList);

}
