package com.sg.employer.unit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.exception.ConstraintViolationException;
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
import com.sg.employer.dto.DepartmentDto;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.dto.EmployeeListDto;
import com.sg.employer.dto.SearchAndSortCriteriaReqDto;
import com.sg.employer.entity.Department;
import com.sg.employer.entity.Employee;
import com.sg.employer.exception.NoDataFoundException;
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
	private SearchAndSortCriteriaReqDto searchAndSortCriteriaForFirstPage;
	private EmployeeListDto employeeListDto;
	private List<EmployeeDto> firstNameSrtedEmployeeList;
	private List<DepartmentDto> departmentsDto;

	@Before
	public void setup() throws JsonProcessingException {
		om = new ObjectMapper();
		this.employeeDtoReq = new EmployeeDto(
				new Employee(0l, "testFirstName", "testLastName", 'f', new Date(), new Department(200l)));
		this.employeeDtoRes = new EmployeeDto(
				new Employee(100l, "testFirstName", "testLastName", 'f', new Date(), new Department(200l)));
		EmployeeDto employeeDtoRes1 = new EmployeeDto(
				new Employee(102l, "testFirstName1", "testLastName1", 'f', new Date(), new Department(201l)));
		EmployeeDto employeeDtoRes2 = new EmployeeDto(
				new Employee(103l, "testFirstName2", "testLastName3", 'm', new Date(), new Department(203l)));
		firstNameSrtedEmployeeList = Stream.of(employeeDtoRes, employeeDtoRes1, employeeDtoRes2)
				.collect(Collectors.toList());
		employeeListDto = new EmployeeListDto(firstNameSrtedEmployeeList, 3);
		@SuppressWarnings("unchecked")
		Map<String, Boolean> sortCriteria = new HashMap() {
			{
				put("firstName", Boolean.FALSE);
			}
		};
		searchAndSortCriteriaForFirstPage = new SearchAndSortCriteriaReqDto(0, 5, null, sortCriteria);
		departmentsDto = Stream
				.of(new DepartmentDto(new Department(501, "testDepartment1", "testDescription1")),
						new DepartmentDto(new Department(501, "testDepartment2", "testDescription2")))
				.collect(Collectors.toList());
	}

	@Test
	public void createEmployee() throws Exception {

		when(employerService.createEmployee(any())).thenReturn(employeeDtoRes);

		ResultActions resultActions = this.mockMvc
				.perform(post("/v1/employees").content(om.writeValueAsString(employeeDtoReq))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string(containsString("\"employeeId\":100")));
		String response = resultActions.andReturn().getResponse().getContentAsString();
		assertThat("Response data is null", response, not(nullValue()));
		EmployeeDto employeeDtoresult = om.readValue(response, EmployeeDto.class);
		assertThat("Department is null", employeeDtoresult.getDepartment(), notNullValue());
		verify(employerService, times(1)).createEmployee(any());
	}

	@Test
	public void createEmployeeWithInvalidDepartment() throws Exception {
		employeeDtoReq.getDepartment().setDepartmentId(250l);
		when(employerService.createEmployee(any()))
				.thenThrow(new ConstraintViolationException("Department doesn't exist", null, "fk_employees_di"));
		this.mockMvc
				.perform(post("/v1/employees").content(om.writeValueAsString(employeeDtoReq))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isInternalServerError());

	}

	@Test
	public void getEmployees() throws Exception {
		when(employerService.getEmployeesByCriteria(any())).thenReturn(employeeListDto);
		this.mockMvc
				.perform(post("/v1/employees/list").content(om.writeValueAsString(searchAndSortCriteriaForFirstPage))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.totalCount", equalTo(3)))
				.andExpect(jsonPath("$.employeeList[0].firstName", equalTo("testFirstName")))
				.andExpect(jsonPath("$.employeeList[1].firstName", equalTo("testFirstName1")))
				.andExpect(jsonPath("$.employeeList[2].firstName", equalTo("testFirstName2")));

	}

	@Test
	public void getEmployeesNoDataFound() throws Exception {
		when(employerService.getEmployeesByCriteria(any())).thenThrow(new NoDataFoundException("No employee found"));
		this.mockMvc
				.perform(post("/v1/employees/list").content(om.writeValueAsString(searchAndSortCriteriaForFirstPage))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	public void getDepartments() throws Exception {
		when(employerService.getAllDepartments()).thenReturn(departmentsDto);
		this.mockMvc.perform(get("/v1/departments").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.size()", equalTo(2)))
				.andExpect(jsonPath("$.[0].departmentName", equalTo("testDepartment1")))
				.andExpect(jsonPath("$.[1].departmentName", equalTo("testDepartment2")));

	}

	@Test
	public void getDepartmentsNoDataFound() throws Exception {
		when(employerService.getAllDepartments()).thenThrow(new NoDataFoundException("No departments found"));
		this.mockMvc.perform(get("/v1/departments").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNotFound());

	}
}
