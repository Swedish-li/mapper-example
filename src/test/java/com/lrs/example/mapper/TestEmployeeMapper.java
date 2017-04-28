package com.lrs.example.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lrs.example.model.Employee;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestEmployeeMapper {
	@Resource
	private EmployeeMapper mapper;

	@Test
	public void testQuery() {
		List<Employee> list = mapper.selectAll();
		assertNotNull(list);
		assertEquals(list.size(), 16);
	}
}
