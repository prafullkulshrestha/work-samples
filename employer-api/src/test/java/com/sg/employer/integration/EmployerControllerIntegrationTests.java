package com.sg.employer.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.employer.dao.EmployeeDao;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.entity.Department;
import com.sg.employer.entity.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployerControllerIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EmployeeDao employeeDao;

	private EmployeeDto employeeDtoReq;
	private ObjectMapper om;
	private String searchAndSortCriteriaForFirstPage;
	private String searchAndSortCriteriaForSecondPage;
	private String searchAndSortCriteriaForFirstPageDesc;
	private String searchAndSortCriteriaForSecondPageDesc;

	@Before
	public void setup() {
		om = new ObjectMapper();
		this.employeeDtoReq = new EmployeeDto(
				new Employee(0l, "testFirstName", "testLastName", 'f', new Date(), new Department(2l)));
		searchAndSortCriteriaForFirstPage = "{\"pageNo\":0,\"pageSize\":5,\"sortCriteria\":{\"firstName\":0}}";
		searchAndSortCriteriaForSecondPage = "{\"pageNo\":1,\"pageSize\":5,\"sortCriteria\":{\"firstName\":0}}";
		searchAndSortCriteriaForFirstPageDesc = "{\"pageNo\":0,\"pageSize\":5,\"sortCriteria\":{\"firstName\":1}}";
		searchAndSortCriteriaForSecondPageDesc = "{\"pageNo\":1,\"pageSize\":5,\"sortCriteria\":{\"firstName\":1}}";
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void createEmployee_200() throws IOException, Exception {
		mvc.perform(post("/v1/employees").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(employeeDtoReq))).andExpect(status().isCreated());

		@SuppressWarnings("unchecked")
		List<Employee> found = employeeDao.findAll();
		assertThat("First name should be as expected",
				found.stream().anyMatch(e -> e.getFirstName().contains("testFirstName")), equalTo(Boolean.TRUE));
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void createEmployee_500() throws IOException, Exception {
		employeeDtoReq.getDepartment().setDepartmentId(250l);
		mvc.perform(post("/v1/employees").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(employeeDtoReq))).andExpect(status().isInternalServerError());

		@SuppressWarnings("unchecked")
		List<Employee> found = employeeDao.findAll();
		assertThat("First name should be as expected",
				found.stream().anyMatch(e -> e.getFirstName().contains("testFirstName")), equalTo(Boolean.FALSE));
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void createEmployee_415() throws IOException, Exception {
		mvc.perform(post("/v1/employees").contentType(MediaType.APPLICATION_PDF)
				.content(om.writeValueAsString(employeeDtoReq))).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void getEmployees_405() throws IOException, Exception {
		// Given the no data inserted in the tables
		mvc.perform(get("/v1/employees/list").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isMethodNotAllowed());

	}

	@Test
	public void getEmployees_415() throws IOException, Exception {
		// Given the no data inserted in the tables
		mvc.perform(post("/v1/employees/list").contentType(MediaType.APPLICATION_OCTET_STREAM)
				.content(searchAndSortCriteriaForFirstPage).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnsupportedMediaType());

	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployees_OK_firstName_ASC_page1() throws IOException, Exception {

		// Given the data inserted in the tables
		mvc.perform(post("/v1/employees/list").contentType(MediaType.APPLICATION_JSON)
				.content(searchAndSortCriteriaForFirstPage).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCount", equalTo(6)))
				.andExpect(jsonPath("$.employeeList[0].firstName", equalTo("Alyson")))
				.andExpect(jsonPath("$.employeeList[1].firstName", equalTo("Darrel")))
				.andExpect(jsonPath("$.employeeList[2].firstName", equalTo("Mike")))
				.andExpect(jsonPath("$.employeeList[3].firstName", equalTo("Rob")))
				.andExpect(jsonPath("$.employeeList[4].firstName", equalTo("Sam")));

	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployees_OK_firstName_ASC_page2() throws IOException, Exception {

		// Given the data inserted in the tables
		mvc.perform(post("/v1/employees/list").contentType(MediaType.APPLICATION_JSON)
				.content(searchAndSortCriteriaForSecondPage).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCount", equalTo(6)))
				.andExpect(jsonPath("$.employeeList[0].firstName", equalTo("Shaelyn")));

	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployees_OK_firstName_DESC_page1() throws IOException, Exception {

		// Given the data inserted in the tables
		mvc.perform(post("/v1/employees/list").contentType(MediaType.APPLICATION_JSON)
				.content(searchAndSortCriteriaForFirstPageDesc).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCount", equalTo(6)))
				.andExpect(jsonPath("$.employeeList[0].firstName", equalTo("Shaelyn")))
				.andExpect(jsonPath("$.employeeList[1].firstName", equalTo("Sam")))
				.andExpect(jsonPath("$.employeeList[2].firstName", equalTo("Rob")))
				.andExpect(jsonPath("$.employeeList[3].firstName", equalTo("Mike")))
				.andExpect(jsonPath("$.employeeList[4].firstName", equalTo("Darrel")));

	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployees_OK_firstName_DESC_page2() throws IOException, Exception {

		// Given the data inserted in the tables
		mvc.perform(post("/v1/employees/list").contentType(MediaType.APPLICATION_JSON)
				.content(searchAndSortCriteriaForSecondPageDesc).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCount", equalTo(6)))
				.andExpect(jsonPath("$.employeeList[0].firstName", equalTo("Alyson")));

	}

	@Test
	public void getEmployees_404() throws IOException, Exception {
		// Given the no data inserted in the tables
		mvc.perform(post("/v1/employees/list").contentType(MediaType.APPLICATION_JSON)
				.content(searchAndSortCriteriaForFirstPage).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getAllDepartments_200() throws IOException, Exception {
		// Given the data inserted in the tables
		mvc.perform(get("/v1/departments").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()", equalTo(3)));

	}

	@Test
	public void getAllDepartments_404() throws IOException, Exception {
		// Given the no data inserted in the tables
		mvc.perform(get("/v1/departments").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void getAllDepartments_415() throws IOException, Exception {
		// Given the no data inserted in the tables
		mvc.perform(get("/v1/departments").accept(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(status().isNotAcceptable());
	}

}
