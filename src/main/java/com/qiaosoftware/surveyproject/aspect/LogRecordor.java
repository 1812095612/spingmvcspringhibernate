package com.qiaosoftware.surveyproject.aspect;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;

import com.qiaosoftware.surveyproject.component.service.LogService;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.entity.manager.Admin;
import com.qiaosoftware.surveyproject.entity.manager.Log;
import com.qiaosoftware.surveyproject.router.RoutingToken;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

public class LogRecordor {
	
	@Autowired
	private LogService logService;
	
	/**
	 * 1.调用目标方法
	 * 2.收集日志信息
	 * 3.保存日志对象
	 * @param joinPoint
	 * @return
	 * @throws Throwable 
	 */
	public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object[] args = joinPoint.getArgs();//toString [Ljava.lang.Object@23423
		
		Object result = null;
		String methodName = null;
		String typeName = null;
		String returnValue = null;
		String exceptionType = null, exceptionMessage = null;
		
		try {
			
			//获取目标方法的签名
			Signature signature = joinPoint.getSignature();
			
			//从签名中读取方法名
			methodName = signature.getName();
			
			//从签名中读取方法所在类型的信息
			typeName = signature.getDeclaringTypeName();
			
			//调用目标方法
			result = joinPoint.proceed(args);
			
			//将result的字符串形式赋值给returnValue
			if(result != null) {
				returnValue = result.toString();
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			
			//先获取异常本身的信息
			exceptionType = e.getClass().getName();
			exceptionMessage = e.getMessage();
			
			//再尝试获取异常的原因
			Throwable cause = e.getCause();
			
			//判断进一步的原因是否存在
			while(cause != null) {
				
				//如果存在，则保存当前原因的异常信息
				exceptionType = cause.getClass().getName();
				exceptionMessage = cause.getMessage();
				
				//再进一步获取“原因的原因”
				cause = cause.getCause();
				
				//会导致无限死循环的代码
				//cause = e.getCause();
			}
			
			throw e;
			
		} finally {
			
			//获取当前操作的登录用户
			HttpSession session = RequestBinder.getRequest().getSession();
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			
			String operatorUser = (user == null) ? "User未登录" : user.getUserName();
			String operatorAdmin = (admin == null) ? "Admin未登录" : admin.getAdminName();
			
			String operator = operatorUser + "/" + operatorAdmin;
			
			//捕获当前系统时间
			Date date = new Date();
			String operateTime = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(date);
			
			//将args转换为params
			List<Object> argList = Arrays.asList(args);
			String params = argList.toString();//toString [a,b,c]
			
			//判断返回值情况
			returnValue = (returnValue == null) ? "未获取到返回值" : returnValue;
			
			Log log = new Log(null, operator, operateTime, methodName, typeName, params, returnValue, exceptionType, exceptionMessage);
			
			//指定token值，让下面保存日志的操作访问日志数据库
			RoutingToken.bindToken(RoutingToken.LOG_KEY);
			
			logService.saveLog(log);
			
		}
		
		return result;
	}

}
