package com.qiaosoftware.surveyproject.entity.guest;

import java.util.Date;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class Survey {
	
//	[1]基本属性
//	OID
	private Integer surveyId;
	
//	调查名称
	private String surveyName;
	
//	Logo图片路径
	//需要设置默认值：指向系统中的默认Logo图片
	private String logoPath = "res_static/logo.gif";
	
//	完成状态
	//true：完成
	//false：未完成
	private boolean completed = false;
	
//	完成时间
	private Date completedTime;
	
//	[2]关联关系：和User之间的单向多对一
	private User user;
	
	//和Bag之间的双向一对多关联关系
	private Set<Bag> bagSet;

	public Survey() {
		
	}

	public Survey(Integer surveyId, String surveyName, String logoPath,
			boolean completed, Date completedTime, User user) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.logoPath = logoPath;
		this.completed = completed;
		this.completedTime = completedTime;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", surveyName=" + surveyName
				+ ", logoPath=" + logoPath + ", completed=" + completed
				+ ", completedTime=" + completedTime + "]";
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(Date completedTime) {
		this.completedTime = completedTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Bag> getBagSet() {
		return bagSet;
	}

	public void setBagSet(Set<Bag> bagSet) {
		this.bagSet = bagSet;
	}

}
