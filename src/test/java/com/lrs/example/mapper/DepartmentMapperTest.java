package com.lrs.example.mapper;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lrs.example.model.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DepartmentMapperTest {

	@Autowired
	DepartmentMapper mapper;

	@Test
	@Commit
	@Ignore
	public void testInsertDept() {
		Department dept = new Department()
				.setManagerId(100)
				.setLocationId(1500)
				.setName("tech");
		mapper.insertDept(dept);

		assertTrue(dept.getId() > 270);

		// 数据删除
		int count = mapper.delete(dept);
		assertEquals(1, count);
	}

}
