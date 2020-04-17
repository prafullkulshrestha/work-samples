package com.service.discovery.example.api.controller;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.ServiceUnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.discovery.example.api.dto.Department;
import com.service.discovery.example.utils.ServiceDiscoveryExampleUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The controller exposes APIs for the service discovery related operations
 * @author prafullkulshrestha
 *
 */
@RestController
@Api(value = "/", tags = "Sevice discovery example API")

@RequestMapping("/")
public class ServiceDiscoveryExampleController {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private ServiceDiscoveryExampleUtils serviceDiscoveryExampleUtils;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${employer-api.context-path}")
	private String employerApiContextPath;
	
	@Value("${employer-api.departments.endpoint.uri}")
	private String employerApiDepartmentsEndpointId;
	
	@Value("${employer-api.instance.id}")
	private String employerApiInstnaceId;
	
	@ApiOperation(httpMethod = "GET", value = "The API for searching the employer service and getting all the departments from it")
	@GetMapping(value = "/v1/departments", produces = "application/json")
	public ResponseEntity<List<String>> getAllDepartments() throws IOException, ParseException, ServiceUnavailableException{
		log.debug("Request recieved for getting all the departments' details ");
		URI service = serviceDiscoveryExampleUtils.serviceUrl(employerApiInstnaceId)
			      .map(s -> s.resolve(employerApiContextPath + employerApiDepartmentsEndpointId))
			      .orElseThrow(ServiceUnavailableException::new);
			     String departmentsString =  restTemplate.getForEntity(service, String.class).getBody();
			    		 ObjectMapper mapper = new ObjectMapper();
			    		 
			    List<Department> departmentsList = mapper.readValue(departmentsString, new TypeReference<List<Department>>(){});
			     List<String> departments =  departmentsList.stream().map((d) -> d.getDepartmentName()).collect(Collectors.toList());
		log.debug("Request completed for getting all the department' details");
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

}
