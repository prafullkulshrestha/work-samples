package com.sg.employer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.DataException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.employer.api.controller.EmployerController;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.entity.Department;
import com.sg.employer.entity.Employee;
import com.sg.employer.service.EmployerService;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployerController.class)
@AutoConfigureMockMvc
public class EmployerControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployerService employerService;
	private EmployeeDto employeeDtoReq;
	private EmployeeDto employeeDtoRes;
	private ObjectMapper om;
	@Before
	public void setup() throws JsonProcessingException {
		om = new ObjectMapper();
		this.employeeDtoReq = new EmployeeDto(new Employee(
						 0l
						, "testFirstName"
						,  "testLastName"
						,  'f'
						,  new Date()
						, new Department(200l)));
		this.employeeDtoRes = new EmployeeDto(new Employee(
				  100l
				, "testFirstName"
				,  "testLastName"
				,  'f'
				,  new Date()
				, new Department(200l)));
		
	}
	
	@Test
	public void saveEmployee() throws Exception {

		when(employerService.createEmployee(any())).thenReturn(employeeDtoRes);

		ResultActions resultActions = this.mockMvc
				.perform(post("/v1/employees").content(om.writeValueAsString(employeeDtoReq)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string(containsString("\"employeeId\":100")));
		String response = resultActions.andReturn().getResponse().getContentAsString();
		assertThat("Response data is null", response,
				not(nullValue()));
		EmployeeDto employeeDtoresult = om
				.readValue(response, EmployeeDto.class);
		assertThat("Department is null", employeeDtoresult.getDepartment(), notNullValue());
		verify(employerService, times(1)).createEmployee(any());
	}
	
}
