package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.guest.Answer;

public interface AnswerDao extends BaseDao<Answer>{

	void batchSaveAnswerList(List<Answer> answerList);

	List<String> getTextResultList(Integer questionId);
	
	int getQuestionEngagedCount(Integer questionId);
	
	int getOptionEngagedCount(Integer questionId, int optionIndex);

	List<Answer> getAnswersBySurveyId(Integer surveyId);

	int getSurveyEngagedCount(Integer surveyId);

}
