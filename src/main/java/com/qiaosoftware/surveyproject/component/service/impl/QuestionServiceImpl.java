package com.qiaosoftware.surveyproject.component.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.QuestionDao;
import com.qiaosoftware.surveyproject.component.service.QuestionService;
import com.qiaosoftware.surveyproject.entity.guest.Question;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService{
	
	@Autowired
	private QuestionDao questionDao;

	public void updateQuestion(Question question) {
		questionDao.updateQuestion(question);
	}

}
