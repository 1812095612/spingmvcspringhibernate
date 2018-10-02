package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.AnswerDao;
import com.qiaosoftware.surveyproject.component.repository.SurveyDao;
import com.qiaosoftware.surveyproject.component.service.EngageService;
import com.qiaosoftware.surveyproject.entity.guest.Answer;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.model.Page;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

@Service
public class EngageServiceImpl extends BaseServiceImpl<Survey> implements EngageService{

	@Autowired
	private SurveyDao surveyDao;
	
	@Autowired
	private AnswerDao answerDao;
	
	public Page<Survey> getAllAvailableSurvey(String pageNoStr,
			int pageSizeSmall) {
		
		int totalRecordNo = surveyDao.getAllAvailableCount();
		
		Page<Survey> page = new Page<Survey>(totalRecordNo, pageNoStr, pageSizeSmall);
		
		int pageNo = page.getPageNo();
		
		List<Survey> list = surveyDao.getAllAvailableList(pageNo, pageSizeSmall);
		
		page.setList(list);
		
		return page;
	}

	public void parseAndSaveParam(
			Map<Integer, Map<String, String[]>> allBagMap, Integer surveyId) {
		//创建一个保存答案数据的集合
		List<Answer> answerList = new ArrayList<Answer>();
		
		//从allBagMap中取所有的值
		Collection<Map<String, String[]>> values = allBagMap.values();
		
		//遍历vlaues集合
		for (Map<String, String[]> param : values) {
			
			//遍历param集合
			Set<Entry<String, String[]>> entrySet = param.entrySet();
			
			for (Entry<String, String[]> entry : entrySet) {
				
				//请求参数名
				String paramName = entry.getKey();
				
				//请求参数值
				String[] paramValues = entry.getValue();
				
				//检查paramName是否是以q开头
				if(!paramName.startsWith("q")){
					continue ;
				}
				
				//从paramName中解析出questionId
				String qIdStr = paramName.substring(1);
				
				Integer questionId = Integer.parseInt(qIdStr);
				
				//将paramValues转换为字符串
				String content = DataProcessUtils.convertStringArr(paramValues);
				
				Answer answer = new Answer(null, content, questionId, surveyId, null);
				
				answerList.add(answer);
				
			}
			
		}
		
		for (Answer answer : answerList) {
			System.out.println(answer);
		}
		
		answerDao.batchSaveAnswerList(answerList);
	}

	public Survey getSurveyById(Integer surveyId) {
		return surveyDao.getEntity(surveyId);
	}
	
	

}
