package com.qiaosoftware.surveyproject.tag;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.qiaosoftware.surveyproject.utils.GlobalNames;

public class DetermineRedisplayTag extends SimpleTagSupport{
	
	private Integer bagId;
	
	private String redisplayName;
	
	private int questionType;
	
	private String optionIndex;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//1.准备工作
		PageContext pageContext = (PageContext) getJspContext();
		
		//①out的作用：是在页面上显示数据
		JspWriter out = pageContext.getOut();
		
		//在页面上显示数据
		//out.print("对你产生想法！");

		//②Session的作用是读取之前保存到Session域中的数据
		HttpSession session = pageContext.getSession();
		
		//③从Session域中将allBagMap取出来
		Map<Integer,Map<String,String[]>> allBagMap = 
				(Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//④根据bagId从allBagMap获取param
		Map<String, String[]> param = allBagMap.get(bagId);
		
		//2.判断param是否为null
		if(param == null) {
			return ;
		}
		
		//3.根据请求参数name属性值从param中获取对应的value值
		String[] values = param.get(redisplayName);
		
		//4.判断values是否是一个有效的数组
		if(values == null || values.length == 0) {
			return ;
		}
		
		//5.根据题型进行不同方式的回显
		//①单选题和多选题
		if(questionType == 0 || questionType == 1) {
			
			//将字符串数组转换为List集合，原因是调用集合对象的contains()方法更容易检查是否存在
			List<String> valuesList = Arrays.asList(values);
			
			if(valuesList.contains(optionIndex)) {
				out.print("checked='checked'");
			}
			
		}
		
		//②简答题
		if(questionType == 2) {
			
			//文本框提交的数据，数组中肯定只有一个值，所以直接使用下标0访问
			String value = values[0];
			out.print(value);
			
		}
		
		
	}
	
	public void setOptionIndex(String optionIndex) {
		this.optionIndex = optionIndex;
	}
	
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	
	public void setBagId(Integer bagId) {
		this.bagId = bagId;
	}
	
	public void setRedisplayName(String redisplayName) {
		this.redisplayName = redisplayName;
	}

}
