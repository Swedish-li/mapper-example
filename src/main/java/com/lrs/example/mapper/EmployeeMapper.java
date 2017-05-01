package com.lrs.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lrs.example.model.Employee;

import tk.mybatis.mapper.common.Mapper;

public interface EmployeeMapper extends Mapper<Employee> {
	/**
	 * 分页查询
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	List<Employee> query(@Param("start")int start,@Param("end") int end);
	/**
	 * 统计总数
	 * 
	 * @return
	 */
	int count();

}
