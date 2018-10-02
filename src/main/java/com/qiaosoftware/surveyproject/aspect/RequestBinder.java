package com.qiaosoftware.surveyproject.aspect;

import javax.servlet.http.HttpServletRequest;

public class RequestBinder {
	
	private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<HttpServletRequest>();
	
	public static void bindingRequest(HttpServletRequest request) {
		local.set(request);
	}
	
	public static HttpServletRequest getRequest() {
		return local.get();
	}
	
	public static void removeRequest() {
		local.remove();
	}

}
