package com.lrs.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(name = "dept")
@Entity
public class Department {
	@Id
	@Column(name = "deptno")
	private Integer id;

	@Column(name = "dname")
	private String name;

	@Column(name = "loc")
	private String location;

	@Transient
	private Integer status;
}
