package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.LogDao;
import com.qiaosoftware.surveyproject.entity.manager.Log;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

@Repository
public class LogDaoImpl extends BaseDaoImpl<Log> implements LogDao{

	public void createTable(String tableName) {
	    String dbname = GlobalNames.DATABASE_NAME;
		String sql = "CREATE TABLE IF NOT EXISTS "+tableName+" LIKE "+dbname+".`survey_log`";
		updateBySQL(sql);
	}

	public List<String> getTableNames() {
	    String dbname = GlobalNames.DATABASE_LOG_NAME;
	    String logtablename = GlobalNames.LOG_TABLE_NAME;
		String sql = "SELECT table_name FROM `information_schema`.`TABLES` WHERE TABLE_SCHEMA="+dbname+" AND TABLE_NAME LIKE " + logtablename;
		
		return getListBySQL(sql);
	}

	public void saveLog(Log log) {
		String tableName = DataProcessUtils.generateTableName(0);
		
		String sql = "INSERT INTO "+tableName+"("
				+ "`OPERATOR`,"
				+ "`OPERATE_TIME`,"
				+ "`METHOD_NAME`,"
				+ "`TYPE_NAME`,"
				+ "`PARAMS`,"
				+ "`RETURN_VALUE`,"
				+ "`EXCEPTION_TYPE`,"
				+ "`EXCEPTION_MESSAGE`) VALUES(?,?,?,?,?,?,?,?)";
		
		updateBySQL(sql, log.getOperator(),
						 log.getOperateTime(),
						 log.getMethodName(),
						 log.getTypeName(),
						 log.getParams(),
						 log.getReturnValue(),
						 log.getExceptionType(),
						 log.getExceptionMessage());	
	}

	public int getTotalRecordNo() {
		
		List<String> tableNames = getTableNames();
		
		String subSelect = DataProcessUtils.generateSubSelect(tableNames);
		
		String sql = "SELECT COUNT(unoin_logs.log_id) FROM ("+subSelect+") unoin_logs";
		
		return getTotalRecordNoBySQL(sql);
	}

	public List<Log> getLimitedLogList(int pageNo, int pageSize) {
		
		List<String> tableNames = getTableNames();
		
		String subSelect = DataProcessUtils.generateSubSelect(tableNames);
		
		String sql = "SELECT unoin_logs.`LOG_ID`,unoin_logs.`OPERATE_TIME`,unoin_logs.`OPERATOR`,unoin_logs.`METHOD_NAME`,unoin_logs.`TYPE_NAME`,unoin_logs.`PARAMS`,unoin_logs.`RETURN_VALUE`,unoin_logs.`EXCEPTION_TYPE`,unoin_logs.`EXCEPTION_MESSAGE` FROM ("+subSelect+") unoin_logs";
		
		List<Object[]> list = getLimitedListBySQL(sql, pageNo, pageSize);
		
		List<Log> logList = new ArrayList<Log>();
		
		for (Object[] objects : list) {
			
			Integer logId = (Integer) objects[0];
			String operateTime = (String) objects[1];
			String operator = (String) objects[2];
			String methodName = (String) objects[3];
			String typeName = (String) objects[4];
			String params = (String) objects[5];
			String returnValue = (String) objects[6];
			String exceptionType = (String) objects[7];
			String exceptionMessage = (String) objects[8];
			
			Log log = new Log(logId, operator, operateTime, methodName, typeName, params, returnValue, exceptionType, exceptionMessage);
			
			logList.add(log);
			
		}
		
		return logList;
	}

}
