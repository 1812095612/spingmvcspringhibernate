package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.SurveyDao;
import com.qiaosoftware.surveyproject.entity.guest.Survey;

@Repository
public class SurveyDaoImpl extends BaseDaoImpl<Survey> implements SurveyDao{

	public int getMyUncompletedCount(Integer userId) {
		
		String hql = "select count(*) from Survey s where s.completed=false and s.user.userId=?";
		
		return getTotalRecordNoByHql(hql, userId);
	}

	public List<Survey> getLimitedMyUncompletedList(int pageNo,
			int pageSizeSmall, Integer userId) {
		
		String hql = "From Survey s where s.completed=false and s.user.userId=? order by s.surveyId desc";
		
		return getLimitedListByHql(hql, pageNo, pageSizeSmall, userId);
	}

	public void updateSurvey(Survey survey) {
		
		String hql = null;
		
		//1.获取当前的logoPath值
		String logoPath = survey.getLogoPath();
		
		//2.检查logoPath是否是默认值
		if("res_static/logo.gif".equals(logoPath)) {
			
			hql = "update Survey s set s.surveyName=? where s.surveyId=?";
			
			//根据不同的HQL语句进行指定字段的更新
			updateByHql(hql, survey.getSurveyName(), survey.getSurveyId());
		}else{
			
			hql = "update Survey s set s.surveyName=?,s.logoPath=? where s.surveyId=?";
			
			updateByHql(hql, survey.getSurveyName(), survey.getLogoPath(), survey.getSurveyId());
			
		}
		
		
	}

	public void updateSurveyStatus(Integer surveyId) {
		String hql = "update Survey s set s.completed=true,s.completedTime=? where s.surveyId=?";
		updateByHql(hql, new Date(), surveyId);
	}

	public int getAllAvailableCount() {
		
		String sql = "select count(*) from survey_survey where COMPLETED=true";
		
		return getTotalRecordNoBySQL(sql);
	}

	public List<Survey> getAllAvailableList(int pageNo, int pageSizeSmall) {
		
		String hql = "From Survey s where s.completed=true";
		
		return getLimitedListByHql(hql, pageNo, pageSizeSmall);
	}

}
