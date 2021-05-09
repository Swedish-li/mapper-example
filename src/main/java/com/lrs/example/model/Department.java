package com.lrs.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(name = "departments")
@Entity
public class Department {
	@Id
	@Column(name = "department_id")
	private Integer id;

	@Column(name = "department_name")
	private String name;

	private int locationId;

	private int managerId;
}
