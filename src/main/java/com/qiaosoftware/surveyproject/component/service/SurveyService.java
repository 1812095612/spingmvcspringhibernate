package com.qiaosoftware.surveyproject.component.service;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.model.Page;

public interface SurveyService extends BaseService<Survey>{

	Page<Survey> getMyUncompletedSurvey(String pageNoStr, int pageSizeSmall,
			Integer userId);

	void updateSurvey(Survey survey);

	void updateSurveyStatus(Integer surveyId);

}
