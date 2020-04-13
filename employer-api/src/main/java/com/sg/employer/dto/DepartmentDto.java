package com.sg.employer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sg.employer.entity.Department;

/**
 * @author prafullkulshrestha
 *
 */
public class DepartmentDto {
	
	private Department department;
	
	public DepartmentDto() {
		this.department = new Department();
	}

	public DepartmentDto(Department department) {
		this.department = department;
	}
	
	public long getDepartmentId() {
		return this.department.getDepartmentId();
	}

	public void setDepartmentId(long departmentId) {
		this.department.setDepartmentId(departmentId);
	}

	public String getDepartmentName() {
		return this.department.getDepartmentName();
	}

	public void setDepartmentName(String departmentName) {
		this.department.setDepartmentName(departmentName);
	}

	
	@JsonIgnore
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
