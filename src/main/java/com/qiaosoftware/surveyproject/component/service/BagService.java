package com.qiaosoftware.surveyproject.component.service;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.guest.Bag;

public interface BagService extends BaseService<Bag>{

	void saveBag(Bag bag);

	void updateBag(Bag bag);

	List<Bag> getBagListBySurveyId(Integer surveyId);

	void batchUpdateBagOrder(List<Integer> bagIdList, List<Integer> bagOrderList);

	void updateRelationshipBymove(Integer surveyId, Integer bagId);

	void updateRelationshipBycopy(Integer surveyId, Integer bagId);

}
