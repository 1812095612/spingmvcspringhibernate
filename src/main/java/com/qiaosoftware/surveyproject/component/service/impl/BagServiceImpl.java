package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.BagDao;
import com.qiaosoftware.surveyproject.component.repository.QuestionDao;
import com.qiaosoftware.surveyproject.component.service.BagService;
import com.qiaosoftware.surveyproject.entity.guest.Bag;
import com.qiaosoftware.surveyproject.entity.guest.Question;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.utils.DataProcessUtils;

@Service
public class BagServiceImpl extends BaseServiceImpl<Bag> implements BagService{
	
	@Autowired
	private BagDao bagDao;
	
	@Autowired
	private QuestionDao questionDao;

	public void saveBag(Bag bag) {
		
		Integer bagId = bagDao.saveEntity(bag);
		
		bag.setBagOrder(bagId);
		
	}

	public void updateBag(Bag bag) {
		bagDao.updateBag(bag);
	}

	public List<Bag> getBagListBySurveyId(Integer surveyId) {
		return bagDao.getBagListBySurveyId(surveyId);
	}

	public void batchUpdateBagOrder(List<Integer> bagIdList,
			List<Integer> bagOrderList) {
		bagDao.batchUpdateBagOrder(bagIdList,bagOrderList);
	}

	public void updateRelationshipBymove(Integer surveyId, Integer bagId) {
		bagDao.updateRelationshipBymove(surveyId, bagId);
	}

	public void updateRelationshipBycopy(Integer surveyId, Integer bagId) {
		
		//1.加载一个Bag对象
		Bag bag = bagDao.getEntity(bagId);
		
		//2.复制这个Bag对象
		Bag targetBag = (Bag) DataProcessUtils.deeplyCopy(bag);
		
		//3.保存这个Bag对象
		//由于survey没有参与序列化，所以需要额外设置一下
		Survey survey = new Survey();
		survey.setSurveyId(surveyId);
		targetBag.setSurvey(survey);
		
		//空指针异常
		//targetBag.getSurvey().setSurveyId(surveyId);
		bagDao.saveEntity(targetBag);
		
		//4.保存这个Bag对象关联的Question集合对象
		Set<Question> questionSet = targetBag.getQuestionSet();
		
		if(questionSet != null && questionSet.size() > 0) {
			questionDao.batchSave(questionSet);
		}
		
		
	}

}
