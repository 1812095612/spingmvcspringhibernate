package com.qiaosoftware.surveyproject.entity.guest;

import java.io.Serializable;

public class Question implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer questionId;
	private String questionName;
	
	//0：单选题
	//1：多选题
	//2：简答题
	private int questionType;
	
	private String options;
	
	private Bag bag;
	
	public Question() {
		
	}

	public Question(Integer questionId, String questionName, int questionType,
			String options, Bag bag) {
		super();
		this.questionId = questionId;
		this.questionName = questionName;
		this.questionType = questionType;
		this.options = options;
		this.bag = bag;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionName="
				+ questionName + ", questionType=" + questionType
				+ ", options=" + options + "]";
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getOptions() {
		return options;
	}

	//特殊设置1：将换行替换为逗号
	public void setOptions(String options) {
		this.options = options.replaceAll("\r\n", ",");
	}
	
	//特殊设置2：将字符串根据逗号拆分成数组
	public String[] getOptionArr() {
		return options.split(",");
	}
	
	//特殊设置3：将逗号替换为换行用户编辑问题时回显选项
	public String getOptionForEdit() {
		return options.replaceAll(",", "\r\n");
	}

	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}

}
