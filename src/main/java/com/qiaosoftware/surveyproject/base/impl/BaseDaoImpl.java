package com.qiaosoftware.surveyproject.base.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;

import com.qiaosoftware.surveyproject.base.BaseDao;

public class BaseDaoImpl<T> implements BaseDao<T> {
	
	//一、注入SessionFactory，提供getSession()方法
	
	@Autowired
	private SessionFactory factory;
	
	public Session getSession() {
		//测试Service方法时使用getCurrentSession();
		return factory.getCurrentSession();
		
		//单独测试Dao方法时使用openSession();
		//return factory.openSession();
	}
	
	//二、获取子类Dao继承BaseDaoImpl时传入的泛型参数类型——也就是实体类的类型
	//例如：下面代码中的Employee
	//public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao
	private Class<T> entityType;
	
	public BaseDaoImpl() {
		
		//1.证实：父类中的this关键字，在子类对象创建时引用的是子类对象
		System.out.println(this);
		
		//2.通过子类的Class类对象获取父类的Class类对象，但是不带泛型参数
		Class<?> superclass = this.getClass().getSuperclass();
		System.out.println(superclass);
		
		//3.通过子类的Class类对象获取“带泛型参数”的Class类对象
		Type type = this.getClass().getGenericSuperclass();
		System.out.println(type);
		
		//4.关于Type
		//java.lang.reflect.Type接口代表的含义：类型
		//凡是可以修饰一个引用类型的变量都可以看成是类型。
		//String a;
		//List<Employee> list;
		//同样是类型，彼此之间还是存在区别。
		//String是一个不带参数的类型，List<Employee>是一个“带参数”的类型
		//java.lang.reflect.ParameterizedType接口代表“带参数”的类型
		//此时通过getGenericSuperclass()方法得到的type对象就是一个“带参数”的类型
		//所以，下面我们可以将type强转为ParameterizedType类型
		ParameterizedType pt = (ParameterizedType) type;
		
		//5.调用ParameterizedType接口的getActualTypeArguments()方法获取实际的参数类型
		Type[] types = pt.getActualTypeArguments();
		
		System.out.println(types[0]);
		
		//6.将types[0]赋值给entityType
		entityType = (Class<T>) types[0];
		
	}
	
	//三、为实现其他抽象方法做一些准备
	public Query getQuery(String hql, Object ...params) {
		
		Query query = getSession().createQuery(hql);
		
		if(params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		return query;
	}
	
	public SQLQuery getSQLQuery(String sql, Object ...params) {
		
		SQLQuery query = getSession().createSQLQuery(sql);
		
		if(params != null) {
			
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			
		}
		
		return query;
		
	}

	public void removeEntityById(Integer id) {
		
		//Object object = getSession().get(entityType, id);
		
		//getSession().delete(object);
		
		//delete from 实体类简单类名 别名 where 别名.id属性名=?
		
		//获取当前要删除的实体类的简单类名
		String simpleName = entityType.getSimpleName();
		
		//获取当前要删除的实体类的id属性名
		//empId,userId,bookId
		String idName = factory.getClassMetadata(entityType).getIdentifierPropertyName();
		
		String hql = "delete from "+simpleName+" alias where alias."+idName+"=?";
		updateByHql(hql, id);
		
	}

	public T getEntity(Integer id) {
		return (T) getSession().get(entityType, id);
	}

	public Integer saveEntity(T t) {
		return (Integer) getSession().save(t);
	}

	public void updateEntity(T t) {
		getSession().update(t);
	}

	public void updateByHql(String hql, Object... params) {
		getQuery(hql, params).executeUpdate();
	}

	public T getEntityByHql(String hql, Object... params) {
		return (T) getQuery(hql, params).uniqueResult();
	}

	public List<T> getListByHql(String hql, Object... params) {
		return getQuery(hql, params).list();
	}

	public Integer getTotalRecordNoByHql(String hql, Object... params) {
		
		//count(*)
		long count = (Long) getQuery(hql, params).uniqueResult();
		
		return (int) count;
	}

	public List<T> getLimitedListByHql(String hql, int pageNo, int pageSize,
			Object... params) {
		return getQuery(hql, params).setFirstResult((pageNo - 1)*pageSize).setMaxResults(pageSize).list();
	}

	public void updateBySQL(String sql, Object... params) {
		getSQLQuery(sql, params).executeUpdate();
	}

	public List getListBySQL(String sql, Object... params) {
		return getSQLQuery(sql, params).list();
	}

	public Integer getTotalRecordNoBySQL(String sql, Object... params) {
		
		BigInteger count = (BigInteger) getSQLQuery(sql, params).uniqueResult();
		
		return count.intValue();
	}

	public List getLimitedListBySQL(String sql, int pageNo, int pageSize,
			Object... params) {
		return getSQLQuery(sql, params).setFirstResult((pageNo - 1)*pageSize).setMaxResults(pageSize).list();
	}
	
	public void batchUpdate(final String sql, final Object[][] params) {

		getSession().doWork(new Work() {
			
			public void execute(Connection connection) throws SQLException {
				
				//1.通过connection获取PreparedStatement对象
				PreparedStatement ps = connection.prepareStatement(sql);
				
				//2.遍历二维数组
				//通过第一个维度积攒要批量执行的SQL语句
				if(params != null) {
					for(int i = 0; i < params.length; i++) {
						
						Object[] param = params[i];
						
						//遍历param，给PreparedStatement对象设置SQL语句的占位符参数
						if(param != null) {
							
							for(int j = 0; j < param.length; j++) {
								ps.setObject(j+1, param[j]);
							}
							
						}
						
						//做批量操作中的积攒操作
						ps.addBatch();
						
					}
					
				}
				
				//执行批量操作
				ps.executeBatch();
				
				//3.释放资源
				if(ps != null) {
					ps.close();
				}
				
				//※注意：connection对象还会被后续的操作用到，所以不能关闭。
				
			}
		});
		
	}

}
