package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.LogDao;
import com.qiaosoftware.surveyproject.component.service.LogService;
import com.qiaosoftware.surveyproject.entity.manager.Log;
import com.qiaosoftware.surveyproject.model.Page;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService{
	
	@Autowired
	private LogDao logDao;

	public void createTable(String tableName) {
		logDao.createTable(tableName);
	}

	public void saveLog(Log log) {
		logDao.saveLog(log);
	}

	public Page<Log> getPage(String pageNoStr, int pageSize) {
		
		int totalRecordNo = logDao.getTotalRecordNo();
		
		Page<Log> page = new Page<Log>(totalRecordNo, pageNoStr, pageSize);
		
		int pageNo = page.getPageNo();
		
		List<Log> list = logDao.getLimitedLogList(pageNo, pageSize);
		
		page.setList(list);
		
		return page;
	}

}
