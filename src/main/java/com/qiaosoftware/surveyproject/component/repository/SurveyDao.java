package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.guest.Survey;

public interface SurveyDao extends BaseDao<Survey>{

	int getMyUncompletedCount(Integer userId);

	List<Survey> getLimitedMyUncompletedList(int pageNo, int pageSizeSmall,
			Integer userId);

	void updateSurvey(Survey survey);

	void updateSurveyStatus(Integer surveyId);

	int getAllAvailableCount();

	List<Survey> getAllAvailableList(int pageNo, int pageSizeSmall);

}
