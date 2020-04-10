package com.sg.employer.api.controller;

import java.io.IOException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.employer.dto.EmployeeDto;
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
}
