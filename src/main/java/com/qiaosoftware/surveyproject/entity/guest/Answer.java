package com.qiaosoftware.surveyproject.entity.guest;


public class Answer {
	
	private Integer answerId;
	
	//保存答案的内容
	private String content;
	
	private Integer questionId;
	
	private Integer surveyId;
	
	//代表当前答案在参与过程中的批次
	//批次：同一个调查可以被参与很多次，不同参与之间问题相同，答案不同，所以使用批次来区分
	private String uuid;
	
	public Answer() {
		
	}

	public Answer(Integer answerId, String content, Integer questionId,
			Integer surveyId, String uuid) {
		super();
		this.answerId = answerId;
		this.content = content;
		this.questionId = questionId;
		this.surveyId = surveyId;
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + "@ content=" + content
				+ "@ questionId=" + questionId + "@ surveyId=" + surveyId
				+ "@ uuid=" + uuid + "]";
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
