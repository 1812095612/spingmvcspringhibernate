package com.qiaosoftware.surveyproject.component.service;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.manager.Res;

public interface ResService extends BaseService<Res>{

	boolean checkServletPathExists(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(Integer maxPos);

	List<Res> getAllResList();

	void batchDelete(List<Integer> resIdList);

	void updateResName(String resName, Integer resId);

	boolean updateResStatus(Integer resId);

	Res getResByServletPath(String servletPath);

}
