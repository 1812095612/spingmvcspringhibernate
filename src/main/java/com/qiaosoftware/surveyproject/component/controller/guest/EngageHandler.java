package com.qiaosoftware.surveyproject.component.controller.guest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.EngageService;
import com.qiaosoftware.surveyproject.entity.guest.Bag;
import com.qiaosoftware.surveyproject.entity.guest.Question;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.model.Page;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

@Controller
public class EngageHandler {
	
	@Autowired
	private EngageService engageService;
	
	@RequestMapping("/guest/engage/engage")
	public String engage(HttpServletRequest request, @RequestParam("currentIndex") Integer currentIndex, @RequestParam("bagId") Integer bagId) {
		
		HttpSession session = request.getSession();
		
		//检查当前请求是点击哪个提交按钮之后发送的
		Map<String,String[]> param = request.getParameterMap();
		
		String submitName = DataProcessUtils.getSubmitName(param);
		
		System.out.println("submitName="+submitName);
		
		List<Bag> bagList = (List<Bag>) session.getAttribute(GlobalNames.BAG_LIST);
		
		//合并答案：将param保存到allBagMap中
		Map<Integer,Map<String,String[]>> allBagMap = 
				(Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//后面提交的请求参数对以前保存的请求参数有影响，不能使用param本身
		//allBagMap.put(bagId, param);
		DataProcessUtils.mergeParam(allBagMap, bagId, param);
		
		if("submit_prev".equals(submitName)) {
			//计算下一个包裹的索引值
			int prevIndex = currentIndex - 1;
			
			//将下一个包裹保存到请求域中作为“当前包裹”
			Bag bag = bagList.get(prevIndex);
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			//将下一个包裹的索引保存到请求域中
			request.setAttribute(GlobalNames.CURRENT_INDEX, prevIndex);
		}
		
		if("submit_next".equals(submitName)) {
			//计算下一个包裹的索引值
			int nextIndex = currentIndex + 1;
			
			//将下一个包裹保存到请求域中作为“当前包裹”
			Bag bag = bagList.get(nextIndex);
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			//将下一个包裹的索引保存到请求域中
			request.setAttribute(GlobalNames.CURRENT_INDEX, nextIndex);
			
		}
		
		if("submit_done".equals(submitName)) {
			
			//解析并保存答案数据
			Survey survey = (Survey) session.getAttribute(GlobalNames.CURRENT_SURVEY);
			
			engageService.parseAndSaveParam(allBagMap, survey.getSurveyId());
			
			//清理Session
			session.removeAttribute(GlobalNames.ALL_BAG_MAP);
			session.removeAttribute(GlobalNames.BAG_LIST);
			session.removeAttribute(GlobalNames.BAG_LIST_SIZE);
			session.removeAttribute(GlobalNames.CURRENT_SURVEY);
			
			//返回首页
			return "redirect:/index.jsp";
			
		}
		
		if("submit_quit".equals(submitName)) {
			
			//将参与调查过程中Session域中保存的数据移除
			session.removeAttribute(GlobalNames.ALL_BAG_MAP);
			session.removeAttribute(GlobalNames.BAG_LIST);
			session.removeAttribute(GlobalNames.BAG_LIST_SIZE);
			session.removeAttribute(GlobalNames.CURRENT_SURVEY);
			
			//返回首页
			return "redirect:/index.jsp";
			
		}
		
		return "guest/engage_engage";
	}
	
	@RequestMapping("/guest/engage/entry/{surveyId}")
	public String entry(@PathVariable("surveyId") Integer surveyId, 
			HttpSession session,
			Map<String, Object> map) {
		
		//1.根据surveyId查询Survey对象
		Survey survey = engageService.getSurveyById(surveyId);
		
		//2.将Survey对象保存到Session域中
		session.setAttribute(GlobalNames.CURRENT_SURVEY, survey);
		
		//3.将Set<Bag>转换为List<Bag>
		Set<Bag> bagSet = survey.getBagSet();
		List<Bag> bagList = new ArrayList<Bag>(bagSet);
		//关注：bagList是否能够保持包裹本身根据bagOrder进行排序后的顺序
		for (int i = 0; i < bagList.size(); i++) {
			Bag bag = bagList.get(i);
			System.out.println(bag);
			
			Set<Question> questionSet = bag.getQuestionSet();
			System.out.println(questionSet);
		}
		
		//4.将List<Bag>保存到Session域中
		session.setAttribute(GlobalNames.BAG_LIST, bagList);
		
		//5.将List<Bag>集合的长度保存到Session域中
		int size = bagList.size();
		session.setAttribute(GlobalNames.BAG_LIST_SIZE, size);
		
		//6.将第一个包裹保存到请求域中
		Bag bag = bagList.get(0);
		map.put(GlobalNames.CURRENT_BAG, bag);
		
		//7.将当前要显示的包裹的索引index保存到请求域
		map.put(GlobalNames.CURRENT_INDEX, 0);
		
		//8.创建Map<bagId,param> allBagMap
		Map<Integer,Map<String,String[]>> allBagMap = new HashMap<Integer, Map<String,String[]>>();
		
		//9.将allBagMap保存到Session域中
		session.setAttribute(GlobalNames.ALL_BAG_MAP, allBagMap);
		
		return "guest/engage_engage";
	}
	
	@RequestMapping("/guest/engage/showAllAvailableSurvey")
	public String showAllAvailableSurvey(@RequestParam(value="pageNoStr", required=false) String pageNoStr, Map<String, Object> map) {
		
		Page<Survey> page = 
				engageService.getAllAvailableSurvey(pageNoStr, Page.PAGE_SIZE_SMALL);
		map.put(GlobalNames.PAGE, page);
		
		return "guest/engage_list";
	}

}
