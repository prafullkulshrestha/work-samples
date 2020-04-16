package com.sg.employer.service;

import java.text.ParseException;
import java.util.List;

import com.sg.employer.dto.DepartmentDto;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.dto.EmployeeListDto;
import com.sg.employer.dto.SearchAndSortCriteriaReqDto;

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
	
	/**
	 * The method returns a paginated and sorted list of all the available employees records in the
	 * database this can be extended to include the search criteria logic also later
	 * 
	 * @return employeeListDto
	 * @throws ParseException 
	 */
	EmployeeListDto getEmployeesByCriteria(SearchAndSortCriteriaReqDto searchAndSortCriteriaReqDto) throws ParseException;

	/**
	 * The method returns a list of all the departments which are available in the database
	 * @return
	 */
	List<DepartmentDto> getAllDepartments();
}
