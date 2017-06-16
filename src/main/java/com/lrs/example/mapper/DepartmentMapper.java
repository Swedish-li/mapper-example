package com.lrs.example.mapper;

import com.lrs.example.model.Department;

import tk.mybatis.mapper.common.Mapper;

public interface DepartmentMapper extends Mapper<Department> {
	/**
	 * 使用存储过程保存部门信息
	 * 
	 * @param dept
	 * @return
	 */
	int insertDept(Department dept);
}
