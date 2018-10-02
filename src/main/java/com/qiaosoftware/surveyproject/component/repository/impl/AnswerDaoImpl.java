package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.AnswerDao;
import com.qiaosoftware.surveyproject.entity.guest.Answer;

@Repository
public class AnswerDaoImpl extends BaseDaoImpl<Answer> implements AnswerDao{

	public void batchSaveAnswerList(List<Answer> answerList) {
		
		String sql = "INSERT INTO `survey_answer`(`CONTENT`,`QUESTION_ID`,`SURVEY_ID`,`UUID`) VALUES(?,?,?,?)";
		
		String uuid = UUID.randomUUID().toString();
		
		Object[][] params = new Object[answerList.size()][4];
		
		for (int i = 0; i < answerList.size(); i++) {
			
			Answer answer = answerList.get(i);
			
			Object[] param = new Object[4];
			
			param[0] = answer.getContent();
			param[1] = answer.getQuestionId();
			param[2] = answer.getSurveyId();
			param[3] = uuid;
			
			params[i] = param;
			
		}
		
		batchUpdate(sql, params);
		
	}

	public List<String> getTextResultList(Integer questionId) {
		String sql = "SELECT content FROM survey_answer WHERE question_id=?";
		return getListBySQL(sql, questionId);
	}

	public int getQuestionEngagedCount(Integer questionId) {
		
		String sql = "SELECT COUNT(question_id) FROM survey_answer WHERE question_id=?";
		
		return getTotalRecordNoBySQL(sql, questionId);
	}

	public int getOptionEngagedCount(Integer questionId, int optionIndex) {
		
		String optionParam = "%,"+optionIndex+",%";
		
		String sql = "SELECT COUNT(*) FROM survey_answer WHERE question_id=? AND CONCAT(',',content,',') LIKE ?";
		
		return getTotalRecordNoBySQL(sql, questionId, optionParam);
	}

	public List<Answer> getAnswersBySurveyId(Integer surveyId) {
		
		String hql = "From Answer a where a.surveyId=?";
		
		return getListByHql(hql, surveyId);
	}

	public int getSurveyEngagedCount(Integer surveyId) {
		
		String sql = "SELECT COUNT(DISTINCT `uuid`) FROM survey_answer WHERE survey_id=?";
		
		return getTotalRecordNoBySQL(sql, surveyId);
	}

}















