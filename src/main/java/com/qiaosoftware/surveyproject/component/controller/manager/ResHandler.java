package com.qiaosoftware.surveyproject.component.controller.manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.ResService;
import com.qiaosoftware.surveyproject.entity.manager.Res;

@Controller
public class ResHandler {
	
	@Autowired
	private ResService resService;
	
	@RequestMapping("/manager/res/updateResStatus")
	public void updateResStatus(HttpServletResponse response, @RequestParam("resId") Integer resId) throws IOException {
		
		boolean currentStatus = resService.updateResStatus(resId);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		if(currentStatus) {
			writer.write("公共资源");
		}else{
			writer.write("受保护资源");
		}
		
	}
	
	@RequestMapping("/manager/res/updateResName")
	public void updateResName(HttpServletResponse response, @RequestParam("resName") String resName, @RequestParam("resId") Integer resId) throws IOException {
		
		resService.updateResName(resName, resId);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("操作成功！");
		
	}
	
	@RequestMapping("/manager/res/batchDelete")
	public String batchDelete(@RequestParam("resIdList") List<Integer> resIdList) {
		
		for (Integer resId : resIdList) {
			System.out.println("resId="+resId);
		}
		
		resService.batchDelete(resIdList);
		
		return "redirect:/manager/res/showList";
	}
	
	@RequestMapping("/manager/res/showList")
	public String showList(Map<String, Object> map) {
		
		List<Res> resList = resService.getAllResList();
		map.put("resList", resList);
		
		return "manager/res_list";
	}

}
