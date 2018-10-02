package com.qiaosoftware.surveyproject.component.controller.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.LogService;
import com.qiaosoftware.surveyproject.entity.manager.Log;
import com.qiaosoftware.surveyproject.model.Page;
import com.qiaosoftware.surveyproject.router.RoutingToken;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

@Controller
public class LogHandler {
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/manager/log/showLogPage")
	public String showLogPage(@RequestParam(value="pageNoStr",required=false) String pageNoStr, Map<String, Object> map) {
		
		RoutingToken.bindToken(RoutingToken.LOG_KEY);
		
		Page<Log> page = logService.getPage(pageNoStr, Page.PAGE_SIZE_SMALL);
		map.put(GlobalNames.PAGE, page);
		
		return "manager/log_list";
	}

}
