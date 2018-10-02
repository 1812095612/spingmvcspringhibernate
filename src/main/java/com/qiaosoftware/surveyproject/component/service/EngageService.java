package com.qiaosoftware.surveyproject.component.service;

import java.util.Map;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.model.Page;

public interface EngageService extends BaseService<Survey>{

	Page<Survey> getAllAvailableSurvey(String pageNoStr, int pageSizeSmall);

	void parseAndSaveParam(Map<Integer, Map<String, String[]>> allBagMap,
			Integer surveyId);

	Survey getSurveyById(Integer surveyId);

}
