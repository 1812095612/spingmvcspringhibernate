package com.qiaosoftware.surveyproject.component.repository;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseDao;
import com.qiaosoftware.surveyproject.entity.guest.Bag;

public interface BagDao extends BaseDao<Bag>{

	void updateBag(Bag bag);

	List<Bag> getBagListBySurveyId(Integer surveyId);

	void batchUpdateBagOrder(List<Integer> bagIdList, List<Integer> bagOrderList);

	void updateRelationshipBymove(Integer surveyId, Integer bagId);

}
