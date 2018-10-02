package com.qiaosoftware.surveyproject.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.qiaosoftware.surveyproject.component.service.LogService;
import com.qiaosoftware.surveyproject.router.RoutingToken;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

public class LogScheduler extends QuartzJobBean{

	private LogService logService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		RoutingToken.bindToken(RoutingToken.LOG_KEY);
		String tableName = DataProcessUtils.generateTableName(1);
		logService.createTable(tableName);
		
		RoutingToken.bindToken(RoutingToken.LOG_KEY);
		tableName = DataProcessUtils.generateTableName(2);
		logService.createTable(tableName);
		
		RoutingToken.bindToken(RoutingToken.LOG_KEY);
		tableName = DataProcessUtils.generateTableName(3);
		logService.createTable(tableName);
		
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
