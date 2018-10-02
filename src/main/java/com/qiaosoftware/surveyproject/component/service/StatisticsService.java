package com.qiaosoftware.surveyproject.component.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.model.Page;

public interface StatisticsService extends BaseService<Survey>{
	
	Page<Survey> getAllAvailableSurvey(String pageNoStr, int pageSizeSmall);

	List<String> getTextResultList(Integer questionId);

	JFreeChart getChart(Integer questionId);

	HSSFWorkbook getWorkBook(Integer surveyId);

}
