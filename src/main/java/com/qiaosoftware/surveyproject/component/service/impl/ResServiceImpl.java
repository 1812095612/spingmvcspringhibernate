package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.ResDao;
import com.qiaosoftware.surveyproject.component.service.ResService;
import com.qiaosoftware.surveyproject.entity.manager.Res;

@Service
public class ResServiceImpl extends BaseServiceImpl<Res> implements ResService{
	
	@Autowired
	private ResDao resDao;

	public boolean checkServletPathExists(String servletPath) {
		return resDao.checkServletPathExists(servletPath);
	}

	public Integer getMaxPos() {
		return resDao.getMaxPos();
	}

	public Integer getMaxCode(Integer maxPos) {
		return resDao.getMaxCode(maxPos);
	}

	public List<Res> getAllResList() {
		return resDao.getAllResList();
	}

	public void batchDelete(List<Integer> resIdList) {
		resDao.batchDelete(resIdList);
	}

	public void updateResName(String resName, Integer resId) {
		resDao.updateResName(resName, resId);
	}

	public boolean updateResStatus(Integer resId) {
		resDao.updateResStatus(resId);
		
		Res res = resDao.getEntity(resId);
		
		return res.isPublicRes();
	}

	public Res getResByServletPath(String servletPath) {
		return resDao.getResByServletPath(servletPath);
	}

}
