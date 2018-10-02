package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.manager.Res;

public interface ResDao extends BaseDao<Res>{

	boolean checkServletPathExists(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(Integer maxPos);

	List<Res> getAllResList();

	void batchDelete(List<Integer> resIdList);

	void updateResName(String resName, Integer resId);

	void updateResStatus(Integer resId);

	Res getResByServletPath(String servletPath);

}
