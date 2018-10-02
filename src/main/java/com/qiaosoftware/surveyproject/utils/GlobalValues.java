package com.qiaosoftware.surveyproject.utils;

import java.util.HashSet;
import java.util.Set;

public class GlobalValues {
	
	public static final Set<String> ALLOWD_TYPES = new HashSet<String>();
	public static final Set<String> OTHER_TYPES = new HashSet<String>();
	
	static {
		ALLOWD_TYPES.add("image/jpg");
		ALLOWD_TYPES.add("image/jpeg");
		ALLOWD_TYPES.add("image/gif");
		ALLOWD_TYPES.add("image/png");
	}
	
	static {
	    OTHER_TYPES.add("application/octet-stream");
	}

}
