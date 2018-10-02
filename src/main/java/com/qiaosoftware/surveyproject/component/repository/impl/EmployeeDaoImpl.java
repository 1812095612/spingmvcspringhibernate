package com.qiaosoftware.surveyproject.component.repository.impl;

import org.springframework.stereotype.Repository;

import com.qiaosoftware.surveyproject.base.impl.BaseDaoImpl;
import com.qiaosoftware.surveyproject.component.repository.EmployeeDao;
import com.qiaosoftware.surveyproject.entity.guest.Employee;

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao{

}
