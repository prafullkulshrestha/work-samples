package com.sg.employer.dto;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sg.employer.entity.Employee;
import com.sg.employer.utils.CustomJsonDateSerializerWithoutTimeZone;
import com.sg.employer.utils.EmployerApiApplicationConstants;

/**
 * @author prafullkulshrestha
 *
 */
public class EmployeeDto {

	private Employee employee;

	public EmployeeDto() {
		this.employee = new Employee();
	}

	public EmployeeDto(Employee emp) {
		this.employee = emp;
	}

	public long getEmployeeId() {
		return this.employee.getEmployeeId();
	}

	public void setEmployeeId(long employeeId) {
		this.employee.setEmployeeId(employeeId);
	}

	public String getFirstName() {
		return this.employee.getFirstName();
	}

	public void setFirstName(String firstName) {
		this.employee.setFirstName(firstName);
	}

	public String getLastName() {
		return this.employee.getLastName();
	}

	public void setLastName(String lastName) {
		this.employee.setLastName(lastName);
	}

	public char getGender() {
		return this.employee.getGender();
	}

	public void setGender(char gender) {
		this.employee.setGender(gender);
	}

	@JsonSerialize(using = CustomJsonDateSerializerWithoutTimeZone.class)
	public Date getDateOfBirth() {
		return this.employee.getDateOfBirth();
	}

	public void setDateOfBirth(String dateOfBirth) throws ParseException {
		this.employee.setDateOfBirth(DateUtils.parseDate(dateOfBirth, EmployerApiApplicationConstants.DATE_FORMAT));
		;
	}

	public DepartmentDto getDepartment() {
		return new DepartmentDto(this.employee.getDepartment());
	}

	public void setDepartment(DepartmentDto department) {
		this.employee.setDepartment(department.getDepartment());
	}

	@JsonIgnore()
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
