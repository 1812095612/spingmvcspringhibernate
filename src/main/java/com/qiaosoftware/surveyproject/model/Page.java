package com.qiaosoftware.surveyproject.model;

import java.util.List;

public class Page<T> {
	
	//一、封装的数据
	//1.List集合
	private List<T> list;
	
	//2.每页显示多少条数据
	public static final int PAGE_SIZE_SMALL = 5;
	public static final int PAGE_SIZE_MIDDLE = 10;
	public static final int PAGE_SIZE_BIG = 20;
	public static final int PAGE_SIZE_HUGE = 40;
	
	//3.当前页的页码
	private int pageNo;
	
	//4.总记录数
	private int totalRecordNo;
	
	//5.总页数
	private int totalPageNo;
	
	//二、构造器
	public Page(int totalRecordNo, String pageNoStr, int pageSize) {
		
		//1.计算总页数
		this.totalRecordNo = totalRecordNo;
		
		this.totalPageNo = (totalRecordNo / pageSize) + ((totalRecordNo % pageSize==0)?0:1);
		
		//2.修正pageNo
		this.pageNo = 1;
		
		try {
			this.pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {/*如果转换失败则什么都不做，保持默认值*/}
		
		if(this.pageNo > this.totalPageNo) {
			this.pageNo = this.totalPageNo;
		}
		
		if(this.pageNo < 1) {
			this.pageNo = 1;
		}
		
	}
	
	public boolean isHasPrev() {
		return this.pageNo > 1;
	}
	
	public boolean isHasNext() {
		return this.pageNo < this.totalPageNo;
	}
	
	public int getPrev() {
		return this.pageNo - 1;
	}
	
	public int getNext() {
		return this.pageNo + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalRecordNo() {
		return totalRecordNo;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}

}
