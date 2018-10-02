package com.qiaosoftware.surveyproject.component.service.impl;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.AnswerDao;
import com.qiaosoftware.surveyproject.component.repository.QuestionDao;
import com.qiaosoftware.surveyproject.component.repository.SurveyDao;
import com.qiaosoftware.surveyproject.component.service.StatisticsService;
import com.qiaosoftware.surveyproject.entity.guest.Answer;
import com.qiaosoftware.surveyproject.entity.guest.Bag;
import com.qiaosoftware.surveyproject.entity.guest.Question;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.model.Page;

@Service
public class StatisticsServiceImpl extends BaseServiceImpl<Survey> implements StatisticsService{
	
	@Autowired
	private SurveyDao surveyDao;
	
	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	public Page<Survey> getAllAvailableSurvey(String pageNoStr,
			int pageSizeSmall) {
		
		int totalRecordNo = surveyDao.getAllAvailableCount();
		
		Page<Survey> page = new Page<Survey>(totalRecordNo, pageNoStr, pageSizeSmall);
		
		int pageNo = page.getPageNo();
		
		List<Survey> list = surveyDao.getAllAvailableList(pageNo, pageSizeSmall);
		
		page.setList(list);
		
		return page;
	}

	public List<String> getTextResultList(Integer questionId) {
		return answerDao.getTextResultList(questionId);
	}

	public JFreeChart getChart(Integer questionId) {
		
		//1.根据questionId查询Question对象
		Question question = questionDao.getEntity(questionId);
		String questionName = question.getQuestionName();
		String[] optionArr = question.getOptionArr();
		
		//2.遍历option数组，生成Dataset对象
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for (int i = 0; i < optionArr.length; i++) {
			
			//①选项的显示名称
			String optionLabel = optionArr[i];
			
			//②选项的索引
			int optionIndex = i;
			
			//③当前选项被选中的次数
			int optionCount = answerDao.getOptionEngagedCount(questionId, optionIndex);
			
			dataset.setValue(optionLabel, optionCount);
			
		}
		
		//3.查询当前问题被参与的次数
		int questionCount = answerDao.getQuestionEngagedCount(questionId);
		
		//4.创建JFreeChart对象，并进行必要的设置
		//①组装title值
		String title = questionName+" "+questionCount+"次参与";
		
		//②创建JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset);
		
		//③修饰
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 15));
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setForegroundAlpha(0.6f);
		plot.setLabelFont(new Font("宋体", Font.PLAIN, 10));
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0},{1}/{3},{2}"));//{0}标签名称　{1}实际的数量　{2}占的百分比  {3}总的数量     
		
		return chart;
	}

	public HSSFWorkbook getWorkBook(Integer surveyId) {
		
		//1.准备数据
		//[1]根据surveyId查询Survey对象
		Survey survey = surveyDao.getEntity(surveyId);
		
		//[2]将Survey对象中级联的所有Question对象封装到List集合中
		List<Question> questionList = new ArrayList<Question>();
		Iterator<Bag> iterator = survey.getBagSet().iterator();
		while (iterator.hasNext()) {
			Bag bag = (Bag) iterator.next();
			Set<Question> questionSet = bag.getQuestionSet();
			
			//将questionSet整体一次性存入questionList
			questionList.addAll(questionSet);
		}
		
		//[3]根据surveyId查询List<Answer>
		List<Answer> answerList = answerDao.getAnswersBySurveyId(surveyId);
		
		//[4]根据surveyId查询当前调查被参与的次数
		int engagedCount = answerDao.getSurveyEngagedCount(surveyId);
		
		//2.转换数据
		Map<String, Map<Integer, String>> bigMap = convertMap(questionList, answerList);
		
		//3.创建HSSFWorkbook对象，并封装数据
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		//①创建Sheet
		String sheetName = survey.getSurveyName()+" "+engagedCount+"次参与";
		HSSFSheet sheet = workbook.createSheet(sheetName);
		
		//②创建第一行：问题标题
		HSSFRow row = sheet.createRow(0);
		
		//③遍历List<Question>
		for(int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(question.getQuestionName());
		}
		
		//④遍历bigMap
		if(engagedCount != 0) {
			
			Set<Entry<String, Map<Integer, String>>> entrySet = bigMap.entrySet();
			List<Entry<String, Map<Integer, String>>> entryList = new ArrayList<Entry<String, Map<Integer, String>>>(entrySet);
			
			for(int i = 0; i < entryList.size(); i++) {
				
				Entry<String, Map<Integer, String>> entry = entryList.get(i);
				
				//UUID
				String key = entry.getKey();
				
				//小Map
				Map<Integer, String> smallMap = entry.getValue();
				
				//索引0已经被第一行占用
				HSSFRow dataRow = sheet.createRow(i+1);
				
				//遍历List<Question>生成单元格
				for(int j = 0; j < questionList.size(); j++) {
					Question question = questionList.get(j);
					
					HSSFCell cell = dataRow.createCell(j);
					
					String content = smallMap.get(question.getQuestionId());
					
					cell.setCellValue(content);
				}
				
			}
			
		}
		
		for(int j = 0; j < questionList.size(); j++) {
			sheet.autoSizeColumn(j);
		}
		
		return workbook;
	}

	/*
	{
		7dc761cd-d7ed-4d26-adb5-267562d6062e={35=2, 38=1,2, 39=PPP, 36=2, 37=1,2, 40=WWW}, 
		e3a294e3-fabc-4c71-93b1-7b31989a8192={35=1, 38=1,2, 39=ww, 36=1, 37=1,2, 40=BBB222}
	}
	 */
	public Map<String, Map<Integer, String>> convertMap(
			List<Question> questionList, List<Answer> answerList) {
		
		Map<String, Map<Integer, String>> bigMap = new HashMap<String, Map<Integer,String>>();
		
		for (Answer answer : answerList) {
			
			String uuid = answer.getUuid();
			
			//一个Answer对象只能够生成Excel表中的一个单元格，并不能生成完整的小Map
			//所以要尝试从大Map中获取小Map
			Map<Integer, String> smallMap = bigMap.get(uuid);
			
			//判断小Map是否为空
			if(smallMap == null) {
				
				//说明是第一次添加：new
				smallMap = new HashMap<Integer, String>();
				
				//保存到大Map中
				bigMap.put(uuid, smallMap);
				
			}
			
			smallMap.put(answer.getQuestionId(), answer.getContent());
			
		}
		
		return bigMap;
	}
	
}




















