package com.qiaosoftware.surveyproject.component.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.BagDao;
import com.qiaosoftware.surveyproject.entity.guest.Bag;

@Repository
public class BagDaoImpl extends BaseDaoImpl<Bag> implements BagDao{

	public void updateBag(Bag bag) {
		String hql = "update Bag b set b.bagName=? where b.bagId=?";
		updateByHql(hql, bag.getBagName(), bag.getBagId());
	}

	public List<Bag> getBagListBySurveyId(Integer surveyId) {
		String hql = "From Bag b where b.survey.surveyId=?";
		return getListByHql(hql, surveyId);
	}

	public void batchUpdateBagOrder(List<Integer> bagIdList,
			List<Integer> bagOrderList) {
		String sql = "UPDATE survey_bag SET bag_order=? WHERE bag_id=?";
		Object[][] params = new Object[bagIdList.size()][2];
		for(int i = 0; i < bagIdList.size(); i++) {
			Object[] param = new Object[2];
			param[0] = bagOrderList.get(i);
			param[1] = bagIdList.get(i);
			params[i] = param;
			
			//params[i] = new Object[]{bagOrderList.get(i), bagIdList.get(i)};
		}
		
		batchUpdate(sql, params);
	}

	public void updateRelationshipBymove(Integer surveyId, Integer bagId) {
		
		String sql = "UPDATE survey_bag SET SURVEY_ID=? WHERE BAG_ID=?";
		
		updateBySQL(sql, surveyId, bagId);
		
	}

}
