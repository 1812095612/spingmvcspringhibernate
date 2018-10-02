package com.qiaosoftware.surveyproject.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class SurveyKeyGenerator implements KeyGenerator{

	/**
	 * @param target：调用目标方法的对象
	 * @param method：目标方法
	 * @param params：调用目标方法时传入的参数
	 */
	public Object generate(Object target, Method method, Object... params) {
		
		StringBuilder builder = new StringBuilder();
		
		String className = target.getClass().getName();
		
		builder.append(className).append(".");
		
		String methodName = method.getName();
		
		builder.append(methodName).append(".");
		
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				
				Object param = params[i];
				
				builder.append(param).append(".");
				
			}
		}
		
		String key = builder.substring(0, builder.lastIndexOf("."));
		
		System.out.println(key);
		
		return key;
	}

}
