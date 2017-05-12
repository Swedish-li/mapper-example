package com.lrs.example.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
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
	@Resource
	private EmployeeMapper mapper;

	@Test
	public void testQueryAll() {
		List<Employee> list = mapper.selectAll();
		assertNotNull(list);
		assertEquals(list.size(), 16);
	}

	@Test
	public void testCount() {
		int count = mapper.count();
		assertEquals(16, count);
	}

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
		assertEquals(16, ((Page<Employee>) list).getTotal());
	}

	/**
	 * 和Spring整合的MyBatis不允许使用SqlSession管理事物
	 */
	@Rollback
	@Transactional
	@Test
	public void testInsert() {

		Employee employee = new Employee();
		employee.setId(123);
		employee.setDeptNo(50);
		employee.setJob("new");
		employee.setComm(new BigDecimal("26"));
		employee.setMgr(15);
		employee.setName("test");
		employee.setHiredate(new Date(System.currentTimeMillis()));
		employee.setSal(new BigDecimal("1235.26"));
		int count = mapper.insert(employee);
		assertEquals(1, count);

	}
}
