package juint.test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qiaosoftware.surveyproject.component.repository.EmployeeDao;
import com.qiaosoftware.surveyproject.component.service.AuthService;
import com.qiaosoftware.surveyproject.component.service.EmployeeService;
import com.qiaosoftware.surveyproject.component.service.ResService;
import com.qiaosoftware.surveyproject.component.service.SurveyService;
import com.qiaosoftware.surveyproject.entity.guest.Employee;
import com.qiaosoftware.surveyproject.entity.guest.Survey;
import com.qiaosoftware.surveyproject.entity.guest.User;
import com.qiaosoftware.surveyproject.model.Page;

public class CommonIOCTest {
	
	private ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-context.xml");
	private EmployeeDao employeeDao = ioc.getBean(EmployeeDao.class);
	private EmployeeService employeeService = ioc.getBean(EmployeeService.class);
	private SurveyService surveyService = ioc.getBean(SurveyService.class);
	private ResService resService = ioc.getBean(ResService.class);
	private AuthService authService = ioc.getBean(AuthService.class);
	
	//@Test
	public void testGetCurrentRes() {
		List<Integer> currentRes = authService.getCurrentRes(3);
		System.out.println(currentRes);
	}
	
	//@Test
	public void testToggleStatus() {
		resService.updateResStatus(5);
	}
	
	//@Test
	public void testBatchSaveSurvey() {
		
		User user = new User();
		user.setUserId(1);
		
		for(int i=0; i < 100; i++) {
			Survey survey = new Survey(null, "surveyName"+i, "res_static/logo.gif", false, null, user);
			
			surveyService.saveEntity(survey);
		}
		
	}
	
	//@Test
	public void testGetUncompletedPage() {
		Page<Survey> page = surveyService.getMyUncompletedSurvey("5555", 2, 1);
		
		int totalRecordNo = page.getTotalRecordNo();
		System.out.println("总记录数："+totalRecordNo);
		
		int totalPageNo = page.getTotalPageNo();
		System.out.println("总页数："+totalPageNo);
		
		int pageNo = page.getPageNo();
		System.out.println("当前页码："+pageNo);
		
		List<Survey> list = page.getList();
		for (Survey survey : list) {
			System.out.println(survey.getSurveyId()+" "+survey.getSurveyName());
		}
	}
	
	//@Test
	public void testEmpUpdate() {
		Employee employee = new Employee(13, "AAAKKKQQQ", 999);
		employeeService.updateEntity(employee);
	}

	//@Test
	public void testLimitedListSql() {
		List list = employeeDao.getLimitedListBySQL("select * from emps", 1, 5);
		for (Object object : list) {
			Object[] objArr = (Object[]) object;
			for(int i = 0; i < objArr.length; i++) {
				System.out.println(objArr[i]);
			}
			System.out.println();
		}
	}
	
	//@Test
	public void testRecordNoSql() {
		Integer count = employeeDao.getTotalRecordNoBySQL("select count(*) from emps");
		System.out.println(count);
	}
	
	//@Test
	public void testLimitedByHql() {
		List<Employee> list = employeeDao.getLimitedListByHql("From Employee e where e.age>?", 2, 5, 500);
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
	
	//@Test
	public void testTestRecordNoHql() {
		Integer count = employeeDao.getTotalRecordNoByHql("select count(*) from Employee e where e.age>?", 300);
		System.out.println(count);
	}
	
	//@Test
	public void testGetListByHql() {
		
		String hql = "From Employee e where e.age>?";
		
		List<Employee> empList = employeeDao.getListByHql(hql, 80);
		
		for (Employee employee : empList) {
			System.out.println(employee);
		}
	}
	
	//@Test
	public void testGetEntityByHql() {
		String hql = "From Employee e where e.empId=?";
		Employee employee = employeeDao.getEntityByHql(hql, 12);
		
		System.out.println(employee);
	}
	
	//@Test
	public void testUpdateByHQL() {
		String hql = "update Employee e set e.age=? where e.empId=?";
		employeeDao.updateByHql(hql, 456, 3);
	}
	
	//@Test
	public void updateEntity() {
		
		Employee t = new Employee(2, "empNameNew111vvv", 111);
		
		employeeDao.updateEntity(t);
	}
	
	//@Test
	public void testSaveEmp() {
		Employee employee = new Employee(null, "empNameNew111", 111);
		Integer id = employeeDao.saveEntity(employee);
		System.out.println(id);
	}
	
	//@Test
	public void testGetEntityById() {
		Employee employee = employeeDao.getEntity(6);
		System.out.println(employee);
	}
	
	//@Test
	public void testRemoveById() {
		employeeDao.removeEntityById(5);
	}
	
	@Test
	public void testBatchSave() {
		
		String sql = "insert into emps(emp_name,age) values(?,?)";
		
		Object[][] params = new Object[100][2];
		
		for(int i = 0; i < 100; i++) {
			
			Object[] param = new Object[2];
			param[0] = "emp"+i;
			param[1] = i*10;
			
			params[i] = param;
			
		}
		
		employeeDao.batchUpdate(sql, params);
		
	}
	
	//@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ioc.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}
	
	//@Test
	public void createTable() {}

}
