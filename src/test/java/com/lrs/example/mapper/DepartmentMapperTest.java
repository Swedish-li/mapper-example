package com.lrs.example.mapper;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lrs.example.model.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DepartmentMapperTest {

	@Resource
	DepartmentMapper mapper;

	@Test
	@Commit
	public void testInsertDept() {
		Department dept = new Department()
				.setId(88)
				.setLocation("shanghai")
				.setName("tech");
		mapper.insertDept(dept);

		assertEquals(Integer.valueOf(1), dept.getStatus());

		// 数据删除
		int count = mapper.delete(dept);
		assertEquals(1, count);
	}

}
