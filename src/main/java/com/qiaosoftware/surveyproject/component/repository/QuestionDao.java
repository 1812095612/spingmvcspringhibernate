package com.qiaosoftware.surveyproject.component.repository;

import java.util.Set;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.guest.Question;

public interface QuestionDao extends BaseDao<Question>{

	void updateQuestion(Question question);

	void batchSave(Set<Question> questionSet);

}
