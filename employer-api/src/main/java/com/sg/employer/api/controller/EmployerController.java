package com.sg.employer.api.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.employer.dto.DepartmentDto;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.dto.EmployeeListDto;
import com.sg.employer.dto.SearchAndSortCriteriaReqDto;
import com.sg.employer.service.EmployerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The controller exposes APIs for the employer's related operations
 * @author prafullkulshrestha
 *
 */
@RestController
@Api(value = "/", tags = "Employer API")

@RequestMapping("/")
public class EmployerController {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private EmployerService employerService;

	@ApiOperation(httpMethod = "POST", value = "The API for saving the detail of an the employee")
	@PostMapping(value = "/v1/employees", produces = "application/json")
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) throws IOException, ParseException{
		log.debug("Request recieved for saving an employee ");
		EmployeeDto employeeDtoRes = employerService.createEmployee(employeeDto);
		log.debug("Request completed for saving an employee ");
		return new ResponseEntity<>(employeeDtoRes, HttpStatus.CREATED);
	}
	
	@ApiOperation(httpMethod = "POST", value = "The API for getting the paginated and sorted details of all the employees")
	@PostMapping(value = "/v1/employees/list", produces = "application/json")
	public ResponseEntity<EmployeeListDto> getEmployees(@RequestBody SearchAndSortCriteriaReqDto searchAndSortCriteriaReqDto) throws IOException, ParseException{
		log.debug("Request recieved for getting all the employees' details ");
		EmployeeListDto employeeListDto = employerService.getEmployeesByCriteria(searchAndSortCriteriaReqDto);
		log.debug("Request completed for getting all the employees' details");
		return new ResponseEntity<>(employeeListDto, HttpStatus.OK);
	}
	
	@ApiOperation(httpMethod = "GET", value = "The API for getting all the departments")
	@GetMapping(value = "/v1/departments", produces = "application/json")
	public ResponseEntity<List<DepartmentDto>> getAllDepartments() throws IOException, ParseException{
		log.debug("Request recieved for getting all the departments' details ");
		List<DepartmentDto> departmentListDto = employerService.getAllDepartments();
		log.debug("Request completed for getting all the department' details");
		return new ResponseEntity<>(departmentListDto, HttpStatus.OK);
	}

}
