package com.qiaosoftware.surveyproject.router;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class SurveyRouterDataSource extends AbstractRoutingDataSource{
	
	@Override
	protected Object determineCurrentLookupKey() {
		
		//从当前线程上获取token值
		String token = RoutingToken.getToken();
		
		//检查token值是否是LOG_KEY
		if(RoutingToken.LOG_KEY.equals(token)) {
			
			//token值使用之后要立即移除
			//原因1：避免影响下一个线程，
			//原因2：避免影响同一个线程的其他数据库操作
			RoutingToken.removeToken();
			
			//如果是，则返回LOG_KEY，表示访问日志数据库
			return RoutingToken.LOG_KEY;
		}
		
		//其他情况返回null，表示使用默认的主数据库
		return null;
	}

}
