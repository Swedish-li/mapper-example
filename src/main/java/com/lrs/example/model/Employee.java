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
@Table(name = "emp")
public class Employee {
	@Id
	@Column(name = "empno")
	private int id;

	@Column(name = "ename")
	private String name;

	private String job;

	private int mgr;

	private Date hiredate;

	private BigDecimal sal;

	private BigDecimal comm;
	
	@Column(name = "deptno")
	private Integer deptNo;
}
