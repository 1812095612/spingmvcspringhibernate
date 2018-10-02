package com.qiaosoftware.surveyproject.component.controller.guest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.qiaosoftware.surveyproject.component.service.BagService;
import com.qiaosoftware.surveyproject.component.service.SurveyService;
import com.qiaosoftware.surveyproject.entity.guest.Bag;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.exception.BagOrderDuplicateException;
import com.qiaosoftware.surveyproject.exception.RemoveBagFailedException;
import com.qiaosoftware.surveyproject.model.Page;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

@Controller
public class BagHandler {
	
	@Autowired
	private BagService bagService;
	
	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping("/guest/bag/copyToThisSurvey/{bagId}/{surveyId}")
	public String copyToThisSurvey(@PathVariable("surveyId") Integer surveyId, 
			@PathVariable("bagId") Integer bagId) {
		
		bagService.updateRelationshipBycopy(surveyId, bagId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/moveToThisSurvey/{bagId}/{surveyId}")
	public String moveToThisSurvey(@PathVariable("surveyId") Integer surveyId, 
			@PathVariable("bagId") Integer bagId) {
		
		bagService.updateRelationshipBymove(surveyId, bagId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/toTargetSurveyListUI/{bagId}/{surveyId}")
	public String toTargetSurveyListUI(@PathVariable("surveyId") Integer surveyId, 
			@PathVariable("bagId") Integer bagId,
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			HttpSession session,
			Map<String, Object> map) {
		
		//1.获取当前用户范围内所有未完成调查
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		Integer userId = user.getUserId();
		
		Page<Survey> page = surveyService.getMyUncompletedSurvey(pageNoStr, Page.PAGE_SIZE_SMALL, userId);
		map.put(GlobalNames.PAGE, page);
		
		//2.将surveyId和bagId保存到请求域中，目的是携带到目标页面
		map.put("surveyId", surveyId);
		map.put("bagId", bagId);
		
		return "guest/bag_copyOrMoveTarget";
	}
	
	@RequestMapping("/guest/bag/adjustOrder")
	public String adjustOrder(@RequestParam("bagIdList") List<Integer> bagIdList, 
			@RequestParam("bagOrderList") List<Integer> bagOrderList, 
			@RequestParam("surveyId") Integer surveyId,
			HttpServletRequest request) {
		
		boolean right = DataProcessUtils.checkBagOrder(bagOrderList);
		
		if(!right) {
			List<Bag> bagList = bagService.getBagListBySurveyId(surveyId);
			request.setAttribute("bagList", bagList);
			request.setAttribute("surveyId", surveyId);
			throw new BagOrderDuplicateException();
		}
		
		bagService.batchUpdateBagOrder(bagIdList, bagOrderList);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/toOrderAdjustUI/{surveyId}")
	public String toAdjustUI(@PathVariable("surveyId") Integer surveyId, Map<String, Object> map) {
		
		List<Bag> bagList = bagService.getBagListBySurveyId(surveyId);
		map.put("bagList", bagList);
		map.put("surveyId", surveyId);
		
		return "guest/bag_orderAdjustUI";
	}
	
	@RequestMapping("/guest/bag/updateBag")
	public String updateBag(Bag bag) {
		
		bagService.updateBag(bag);
		
		return "redirect:/guest/survey/toDesignUI/"+bag.getSurvey().getSurveyId();
	}
	
	@RequestMapping("/guest/bag/toEditUI/{bagId}/{surveyId}")
	public String toEditUI(@PathVariable("bagId") Integer bagId, @PathVariable("surveyId") Integer surveyId, Map<String, Object> map) {
		
		map.put("surveyId", surveyId);
		
		Bag bag = bagService.getEntity(bagId);
		map.put("bag", bag);
		
		return "guest/bag_editUI";
	}
	
	@RequestMapping("/guest/bag/removeBag/{bagId}/{surveyId}")
	public String removeBag(@PathVariable("bagId") Integer bagId, @PathVariable("surveyId") Integer surveyId) {
		
		try {
			bagService.removeEntityById(bagId);
		} catch (Exception e) {
			e.printStackTrace();
			
			Throwable cause = e.getCause();
			
			if(cause != null) {
				
				if(cause instanceof MySQLIntegrityConstraintViolationException) {
					
					throw new RemoveBagFailedException();
					
				}
				
			}
			
		}
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/bag/saveBag")
	public String saveBag(Bag bag) {
		
		//事务1
		//Integer bagId = bagService.saveEntity(bag);
		
		//bag.setBagOrder(bagId);
		
		//事务2
		//bagService.updateEntity(bag);
		
		bagService.saveBag(bag);
		
		return "redirect:/guest/survey/toDesignUI/"+bag.getSurvey().getSurveyId();
	}
	
	@RequestMapping("/guest/bag/toAddUI/{surveyId}")
	public String toAddUI(@PathVariable("surveyId") Integer surveyId, Map<String, Object> map) {
		
		map.put("surveyId", surveyId);
		
		return "guest/bag_addUI";
	}

}
