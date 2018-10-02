package com.qiaosoftware.surveyproject.component.service;

import java.util.List;

import com.qiaosoftware.surveyproject.base.BaseService;
import com.qiaosoftware.surveyproject.entity.guest.Employee;

public interface EmployeeService extends BaseService<Employee>{

	List<Employee> getEmpList();

}
