package com.qiaosoftware.surveyproject.component.service;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.guest.Question;

public interface QuestionService extends BaseService<Question>{

	void updateQuestion(Question question);
	
}
