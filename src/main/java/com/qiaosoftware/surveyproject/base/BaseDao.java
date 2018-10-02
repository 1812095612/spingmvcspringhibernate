package com.qiaosoftware.surveyproject.base;

import java.util.List;

public interface BaseDao<T> {
	
	/**
	 * 根据entityId删除实体类对象
	 * @param id
	 */
	void removeEntityById(Integer id);
	
	/**
	 * 根据entityId查询单个实体类对象
	 * @param id
	 * @return
	 */
	T getEntity(Integer id);
	
	/**
	 * 保存实体类对象
	 * @param t
	 * @return 保存后自增的主键值
	 */
	Integer saveEntity(T t);
	
	/**
	 * 根据实体类对象进行更新
	 * @param t
	 */
	void updateEntity(T t);
	
	//update User u set u.userName=? where u.userId=?
	/**
	 * 根据HQL语句更新实体类对象，也可以执行删除操作，但不能执行保存操作
	 * @param hql
	 * @param params
	 */
	void updateByHql(String hql, Object ... params);
	
	/**
	 * 根据HQL语句和相关的条件查询单个对象
	 * @param hql
	 * @param params
	 * @return
	 */
	T getEntityByHql(String hql, Object ... params);
	
	/**
	 * 根据HQL和相关的条件查询List集合，不分页
	 * @param hql
	 * @param params
	 * @return
	 */
	List<T> getListByHql(String hql, Object ... params);
	
	/**
	 * 根据HQL和相关的条件查询总记录数，可以用于分页
	 * @param hql
	 * @param params
	 * @return
	 */
	Integer getTotalRecordNoByHql(String hql, Object...params);
	
	/**
	 * 根据HQL和相关条件，查询List集合，可以用于分页
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	List<T> getLimitedListByHql(String hql, int pageNo, int pageSize, Object...params);
	
	/**
	 * 根据SQL语句执行增删改操作
	 * @param sql
	 * @param params
	 */
	void updateBySQL(String sql, Object ... params);
	
	/**
	 * 根据SQL语句查询List集合，不指定泛型是因为泛型类型需要根据SQL语句的实际情况确定
	 * @param sql
	 * @param params
	 * @return
	 */
	List getListBySQL(String sql, Object ... params);
	
	/**
	 * 根据SQL语句查询总记录数，可以用于分页
	 * @param sql
	 * @param params
	 * @return
	 */
	Integer getTotalRecordNoBySQL(String sql, Object...params);
	
	/**
	 * 根据SQL语句查询List集合，可以用于分页，不指定泛型是因为泛型类型需要根据SQL语句的实际情况确定
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	List getLimitedListBySQL(String sql, int pageNo, int pageSize, Object...params);

	/**
	 * 进行批量操作
	 * @param sql
	 * @param params
	 * insert into emps(emp_name,age) values(?,?) [emp01,10]
	 * insert into emps(emp_name,age) values(?,?) [emp02,20]
	 * insert into emps(emp_name,age) values(?,?) [emp03,30]
	 * insert into emps(emp_name,age) values(?,?) [emp04,40]
	 */
	void batchUpdate(String sql, Object[][] params);//List<Object[]>
	
}
