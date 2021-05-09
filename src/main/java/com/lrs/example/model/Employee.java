package com.lrs.example.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * EMP 实体
 * 
 * @author Swedish-li
 *
 */

@Data
@Table(name = "employees")
public class Employee {
	@Id
	@Column(name = "employee_id")
	private int id;

	@Column(name = "last_name")
	private String name;

	private String firstName;

	private String email;

	private String phoneNumber;

	private String jobId;

	private int managerId;

	private BigDecimal salary;

	private BigDecimal commissionPct;

	private int departmentId;
	
	@Column(name = "hire_date")
	private Date hireDate;
}
