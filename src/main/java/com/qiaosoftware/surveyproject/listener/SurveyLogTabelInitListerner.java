package com.qiaosoftware.surveyproject.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.qiaosoftware.surveyproject.component.service.LogService;
import com.qiaosoftware.surveyproject.router.RoutingToken;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

public class SurveyLogTabelInitListerner implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private LogService logService;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		//1.创建当月的数据库表
		RoutingToken.bindToken(RoutingToken.LOG_KEY);
		
		String tableName01 = DataProcessUtils.generateTableName(0);
		logService.createTable(tableName01);
		
		//2.创建下一个月的数据库表
		RoutingToken.bindToken(RoutingToken.LOG_KEY);
		
		String tableName02 = DataProcessUtils.generateTableName(1);
		logService.createTable(tableName02);
	}

	/*public void onApplicationEvent(ContextRefreshedEvent event) {
		
		ApplicationContext applicationContext = event.getApplicationContext();
		System.out.println(applicationContext);
		
		ApplicationContext parent = applicationContext.getParent();
		
		if(parent == null) {
			System.out.println("☆☆☆☆☆特定操作☆☆☆☆☆");
		}
		System.out.println("★★★★★★★项目启动了★★★★★★★");
		
	}*/

	/*public void onApplicationEvent(ContextStartedEvent event) {
		System.out.println("★★★★★★★项目启动了★★★★★★★");
	}*/

}
