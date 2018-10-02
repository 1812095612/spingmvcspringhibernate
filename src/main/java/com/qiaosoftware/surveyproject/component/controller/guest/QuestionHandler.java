package com.qiaosoftware.surveyproject.component.controller.guest;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.QuestionService;
import com.qiaosoftware.surveyproject.entity.guest.Question;

@Controller
public class QuestionHandler {
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping("/guest/question/updateQuestion")
	public String updateQuestion(Question question, @RequestParam("surveyId") Integer surveyId) {
		
		questionService.updateQuestion(question);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/question/toEditUI/{questionId}/{surveyId}")
	public String toEditUI(
			@PathVariable("questionId") Integer questionId, 
			@PathVariable("surveyId") Integer surveyId,
			Map<String, Object> map) {
		
		Question question = questionService.getEntity(questionId);
		map.put("question", question);
		map.put("surveyId", surveyId);
		
		//创建一个Map用户生成题型对应的单选按钮
		Map<String, String> radioMap = new LinkedHashMap<String, String>();
		radioMap.put("0", "单选题");
		radioMap.put("1", "多选题");
		radioMap.put("2", "简答题");
		//将生成单选按钮的Map保存到请求域中
		map.put("radioMap", radioMap);
		
		return "guest/question_editUI";
	}
	
	@RequestMapping("/guest/question/removeQuestion/{questionId}/{surveyId}")
	public String removeQuestion(@PathVariable("questionId") Integer questionId, @PathVariable("surveyId") Integer surveyId) {
		
		questionService.removeEntityById(questionId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/question/saveQuestion")
	public String saveQuestion(Question question, @RequestParam("surveyId") Integer surveyId) {
		
		questionService.saveEntity(question);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	@RequestMapping("/guest/question/toAddUI/{bagId}/{surveyId}")
	public String toAddUI(@PathVariable("bagId") Integer bagId, @PathVariable("surveyId") Integer surveyId, Map<String, Object> map) {
		
		map.put("bagId", bagId);
		map.put("surveyId", surveyId);
		
		return "guest/question_addUI";
	}

}
