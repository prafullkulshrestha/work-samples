package com.sg.employer.service;

import com.sg.employer.dto.EmployeeDto;

/**
 * The service class has organizations' related business logic
 * 
 * @author prafullkulshrestha
 */
public interface EmployerService {

	/**
	 * The service method to save the employee in the database
	 * @param employeeDto
	 * @return
	 */
	EmployeeDto createEmployee(EmployeeDto employeeDto);
}
