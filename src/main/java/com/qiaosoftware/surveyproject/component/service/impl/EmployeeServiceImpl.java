package com.qiaosoftware.surveyproject.component.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiaosoftware.surveyproject.base.impl.BaseServiceImpl;
import com.qiaosoftware.surveyproject.component.repository.EmployeeDao;
import com.qiaosoftware.surveyproject.component.service.EmployeeService;
import com.qiaosoftware.surveyproject.entity.guest.Employee;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService{

	@Autowired
	private EmployeeDao employeeDao;
	
	public EmployeeServiceImpl() {
		System.out.println("EmployeeServiceImpl对象创建了！");
	}

	public List<Employee> getEmpList() {
		return employeeDao.getListByHql("From Employee");
	}
	
}
