package com.qiaosoftware.surveyproject.component.controller.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qiaosoftware.surveyproject.component.service.StatisticsService;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.model.Page;
import com.qiaosoftware.surveyproject.utils.GlobalNames;

@Controller
public class StatisticsHandler {
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("/manager/statistics/export/{surveyId}")
	public void exportExcel(@PathVariable("surveyId") Integer surveyId, HttpServletResponse response) throws IOException {
		
		HSSFWorkbook workbook = statisticsService.getWorkBook(surveyId);
		
		response.setContentType("application/vnd.ms-excel");
		
		response.setHeader("Content-Disposition", "attachment;filename="+System.nanoTime()+".xls");
		
		ServletOutputStream outputStream = response.getOutputStream();
		
		workbook.write(outputStream);
		
	}
	
	@RequestMapping("/manager/statistics/testExport")
	public void testExport(HttpServletResponse response) throws IOException {
		
		//1.创建HSSFWorkbook对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		workbook.createSheet("UUUUUUUKKKKKKKK");
		
		//2.进行文件下载的必要设置
		//①响应数据的内容类型
		response.setContentType("application/vnd.ms-excel");
		
		//②通过设置Content-dispostion响应消息头，指定文件名
		response.setHeader("Content-Disposition", "attachment;filename="+System.nanoTime()+".xls");
		
		//3.将HSSFWorkbook对象中封装的数据写入到response提供的字节输出流中
		//①从response对象中获取字节输出流
		ServletOutputStream outputStream = response.getOutputStream();
		
		//②写入
		workbook.write(outputStream);
	}
	
	@RequestMapping("/manager/statistics/generateChart/{questionId}")
	public void generateChart(@PathVariable("questionId") Integer questionId, HttpServletResponse response) throws IOException {
		
		//1.根据questionId生成对应的图表对象
		JFreeChart chart = statisticsService.getChart(questionId);
		
		//2.将图表对象生成的图片数据作为当前请求的响应返回给浏览器
		//①从response对象中获取一个能够返回二进制数据的字节输出流
		ServletOutputStream out = response.getOutputStream();
		//PrintWriter writer = response.getWriter();
		//out是一个字节输出流，writer是一个字符输出流，他们都可以返回响应数据，就看响应数据是什么格式
		
		//②将chart对象中的数据写入到上述输出流中
		ChartUtilities.writeChartAsJPEG(out, chart, 400, 300);
		
	}
	
	@RequestMapping("manager/statistics/showTextList/{questionId}")
	public String showTextResult(@PathVariable("questionId") Integer questionId,Map<String, Object> map) {
		
		List<String> textResultList = statisticsService.getTextResultList(questionId);
		map.put("textResultList", textResultList);
		
		return "manager/statistics_textList";
	}
	
	@RequestMapping("/manager/statistics/showSummary/{surveyId}")
	public String toSummary(@PathVariable("surveyId") Integer surveyId, Map<String, Object> map) {
		
		Survey survey = statisticsService.getEntity(surveyId);
		map.put("survey", survey);
		
		return "manager/statistics_summary";
	}
	
	@RequestMapping("/manager/statistics/showAllSurvey")
	public String showAllSurvey(
			@RequestParam(value="pageNoStr", required=false) String pageNoStr,
			Map<String, Object> map) {
		
		Page<Survey> page = statisticsService.getAllAvailableSurvey(pageNoStr, Page.PAGE_SIZE_MIDDLE);
		map.put(GlobalNames.PAGE, page);
		
		return "manager/statistics_list";
	}

}
