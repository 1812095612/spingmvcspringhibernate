package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.SurveyDao;
import com.qiaosoftware.surveyproject.component.service.SurveyService;
import com.qiaosoftware.surveyproject.entity.guest.Bag;
import com.qiaosoftware.surveyproject.entity.guest.Question;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.exception.BagEmptyException;
import com.qiaosoftware.surveyproject.exception.SurveyEmptyException;
import com.qiaosoftware.surveyproject.model.Page;

@Service
public class SurveyServiceImpl extends BaseServiceImpl<Survey> implements SurveyService{

	@Autowired
	private SurveyDao surveyDao;

	public Page<Survey> getMyUncompletedSurvey(String pageNoStr,
			int pageSizeSmall, Integer userId) {
		
		//1.查询总记录数
		int totalRecordNo = surveyDao.getMyUncompletedCount(userId);
		
		//2.创建Page对象
		Page<Survey> page = new Page<Survey>(totalRecordNo, pageNoStr, pageSizeSmall);
		
		//3.获取经过修正以后的pageNo
		int pageNo = page.getPageNo();
		
		//4.根据pageNo和pageSize查询List集合
		List<Survey> list = surveyDao.getLimitedMyUncompletedList(pageNo, pageSizeSmall, userId);
		
		//5.将list集合设置到Page对象中
		page.setList(list);
		
		//6.返回
		return page;
	}

	public void updateSurvey(Survey survey) {
		
		surveyDao.updateSurvey(survey);
		
	}

	public void updateSurveyStatus(Integer surveyId) {
		
		//1.根据surveyId查询调查对象
		Survey survey = surveyDao.getEntity(surveyId);
		
		//2.检查是否可以完成
		Set<Bag> bagSet = survey.getBagSet();
		if(bagSet == null || bagSet.size() == 0) {
			
			//说明调查中没有包裹
			throw new SurveyEmptyException();
			
		}
		
		Iterator<Bag> iterator = bagSet.iterator();
		while (iterator.hasNext()) {
			Bag bag = (Bag) iterator.next();
			Set<Question> questionSet = bag.getQuestionSet();
			if(questionSet == null || questionSet.size() == 0) {
				throw new BagEmptyException();
			}
		}
		
		//3.设置完成状态
		surveyDao.updateSurveyStatus(surveyId);
		
	}
	
}
