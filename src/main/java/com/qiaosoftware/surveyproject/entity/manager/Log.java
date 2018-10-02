package com.qiaosoftware.surveyproject.entity.manager;

public class Log {
	
	private Integer logId;
	
	//操作人
	private String operator;
	
	//操作时间
	private String operateTime;
	
	//方法名
	private String methodName;
	
	//方法所在的类型
	private String typeName;
	
	//方法执行时所传入的参数
	private String params;
	
	//方法执行后的返回值
	private String returnValue;
	
	//抛出的异常
	private String exceptionType;
	
	//异常信息
	private String exceptionMessage;
	
	public Log() {
		
	}

	public Log(Integer logId, String operator, String operateTime,
			String methodName, String typeName, String params,
			String returnValue, String exceptionType, String exceptionMessage) {
		super();
		this.logId = logId;
		this.operator = operator;
		this.operateTime = operateTime;
		this.methodName = methodName;
		this.typeName = typeName;
		this.params = params;
		this.returnValue = returnValue;
		this.exceptionType = exceptionType;
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "Log [logId=" + logId + ", operator=" + operator
				+ ", operateTime=" + operateTime + ", methodName=" + methodName
				+ ", typeName=" + typeName + ", params=" + params
				+ ", returnValue=" + returnValue + ", exceptionType="
				+ exceptionType + ", exceptionMessage=" + exceptionMessage
				+ "]";
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
