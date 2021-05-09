package com.lrs.example.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.junit.Ignore;

// import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lrs.example.model.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestEmployeeMapper {
	@Autowired
	private EmployeeMapper mapper;

	@Test
	public void testQueryAll() {
		List<Employee> list = mapper.selectAll();
		assertNotNull(list);
		assertEquals(list.size(), 107);
	}

	@Test
	public void testCount() {
		int count = mapper.count();
		assertEquals(107, count);
	}

	/**
	 * https://github.com/mybatis/mybatis-3/issues/206
	 */
	@Test
	public void testPageQuery() {
		List<Employee> list = mapper.query(5, 8);
		assertNotNull(list);
		assertEquals(4, list.size());
	}

	@Test
	public void testPageHelper() {
		PageHelper.startPage(5, 4);
		List<Employee> list = mapper.selectAll();
		assertNotNull(list);
		assertEquals(4, list.size());
		// 分页时，实际返回的结果list类型是Page<E>，如果想取出分页信息，需要强制转换为Page<E>
		assertEquals(107, ((Page<Employee>) list).getTotal());
	}

	/**
	 * 和Spring整合的MyBatis不允许使用SqlSession管理事物
	 */
	@Rollback
	@Transactional
	@Test
	@Ignore
	public void testInsert() {

		Employee employee = new Employee();
		employee.setId(800);
		employee.setDepartmentId(50);
		employee.setJobId("AD_VP");
		employee.setCommissionPct(new BigDecimal("0.31"));
		employee.setManagerId(100);
		employee.setName("test");
		employee.setFirstName("kaka");
		employee.setHireDate(new Date(System.currentTimeMillis()));
		employee.setSalary(new BigDecimal("1235.26"));
		employee.setEmail("test@123.coom");
		employee.setPhoneNumber("515.123.1234");
		int count = mapper.insert(employee);
		assertEquals(1, count);

	}
}
