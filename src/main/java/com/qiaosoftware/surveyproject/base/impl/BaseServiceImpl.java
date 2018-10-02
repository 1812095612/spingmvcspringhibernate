package com.qiaosoftware.surveyproject.base.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.base.BaseService;

public class BaseServiceImpl<T> implements BaseService<T>{
	
	@Autowired
	private BaseDao<T> baseDao;

	public void removeEntityById(Integer id) {
		baseDao.removeEntityById(id);
	}

	public T getEntity(Integer id) {
		return baseDao.getEntity(id);
	}

	public Integer saveEntity(T t) {
		return baseDao.saveEntity(t);
	}

	public void updateEntity(T t) {
		baseDao.updateEntity(t);
	}
	
	

}
