package com.qiaosoftware.surveyproject.router;

public class RoutingToken {
	
	private static ThreadLocal<String> local = new ThreadLocal<String>();
	public static final String LOG_KEY = "DATASOURCE_LOG";
	
	public static void bindToken(String token) {
		local.set(token);
	}
	
	public static String getToken() {
		return local.get();
	}
	
	public static void removeToken() {
		local.remove();
	}

}
