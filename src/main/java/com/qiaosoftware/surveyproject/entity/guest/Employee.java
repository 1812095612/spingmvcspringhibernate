package com.qiaosoftware.surveyproject.entity.guest;

import java.io.Serializable;

public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer empId;
	private String empName;
	private int age;
	
	public Employee() {
		
	}

	public Employee(Integer empId, String empName, int age) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", age="
				+ age + "]";
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
