package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.QuestionDao;
import com.qiaosoftware.surveyproject.entity.guest.Question;

@Repository
public class QuestionDaoImpl extends BaseDaoImpl<Question> implements QuestionDao{

	public void updateQuestion(Question question) {
		String hql = "update Question q set q.questionName=?,q.questionType=?,q.options=? where q.questionId=?";
		updateByHql(hql, question.getQuestionName(), question.getQuestionType(), question.getOptions(), question.getQuestionId());
	}

	public void batchSave(Set<Question> questionSet) {
		
		String sql = "INSERT INTO survey_question(`QUESTION_NAME`,`QUESTION_TYPE`,`OPTIONS`,`BAG_ID`) VALUES(?,?,?,?)";
	
		Object[][] params = new Object[questionSet.size()][4];
		
		List<Question> questionList = new ArrayList<Question>(questionSet);
		
		for(int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			Object[] param = new Object[4];
			param[0] = question.getQuestionName();
			param[1] = question.getQuestionType();
			param[2] = question.getOptions();
			param[3] = question.getBag().getBagId();
			
			params[i] = param;
		}
		
		batchUpdate(sql, params);
		
	}

}
