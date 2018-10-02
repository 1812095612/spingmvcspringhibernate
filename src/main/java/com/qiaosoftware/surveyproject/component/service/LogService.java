package com.qiaosoftware.surveyproject.component.service;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.manager.Log;
import com.qiaosoftware.surveyproject.model.Page;

public interface LogService extends BaseService<Log>{

	void createTable(String tableName);

	void saveLog(Log log);
	
	Page<Log> getPage(String pageNoStr, int pageSize);

}
