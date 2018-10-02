package com.qiaosoftware.surveyproject.entity.guest;

import java.util.Set;

import com.qiaosoftware.surveyproject.entity.manager.Role;

public class User {
	
	private Integer userId;
	private String userName;
	private String userPwd;
	private boolean company;
	private Set<Role> roleSet;
	private String resCodeArr;
	
	public User() {
		
	}

	public User(Integer userId, String userName, String userPwd, boolean company) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.company = company;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userPwd=" + userPwd + ", company=" + company + "]";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public boolean isCompany() {
		return company;
	}

	public void setCompany(boolean company) {
		this.company = company;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}
	
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	
	public String getResCodeArr() {
		return resCodeArr;
	}
	
	public void setResCodeArr(String resCodeArr) {
		this.resCodeArr = resCodeArr;
	}
}
