package com.sg.employer.dto;

import java.util.List;

public class EmployeeListDto {

	private List<EmployeeDto> employeeList;
	private long totalCount;

	public EmployeeListDto(List<EmployeeDto> employeeDtos, long totalCount) {
		this.employeeList = employeeDtos;
		this.totalCount = totalCount;
	}

	public List<EmployeeDto> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeDto> employeeList) {
		this.employeeList = employeeList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
