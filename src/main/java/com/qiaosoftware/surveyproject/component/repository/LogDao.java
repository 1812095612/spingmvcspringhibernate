package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.manager.Log;

public interface LogDao extends BaseDao<Log>{

	void createTable(String tableName);

	void saveLog(Log log);
	
	List<String> getTableNames();
	
	int getTotalRecordNo();
	
	List<Log> getLimitedLogList(int pageNo, int pageSize);

}
