package com.sg.employer.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import com.sg.employer.dao.EmployeeDao;
import com.sg.employer.dto.DepartmentDto;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.dto.EmployeeListDto;
import com.sg.employer.dto.SearchAndSortCriteriaReqDto;
import com.sg.employer.entity.Department;
import com.sg.employer.entity.Employee;
import com.sg.employer.exception.NoDataFoundException;
import com.sg.employer.service.EmployerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployerServiceIntegrationTests {
	@Autowired
	private EmployerService employerService;
	@Autowired
	EmployeeDao employeeDao;
	private EmployeeDto employeeDtoReq;
	private SearchAndSortCriteriaReqDto searchAndSortCriteriaReqDto;

	@Before
	public void setup() {
		this.employeeDtoReq = new EmployeeDto(
				new Employee(0l, "testFirstName", "testLastName", 'f', new Date(), new Department(2l)));
		@SuppressWarnings({ "unchecked", "serial" })
		Map<String, Boolean> sortCriteria = new HashMap() {
			{
				put("firstName", Boolean.FALSE);
			}
		};
		searchAndSortCriteriaReqDto = new SearchAndSortCriteriaReqDto(0, 5, null, sortCriteria);

	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void createEmployee() throws IOException, Exception {
		EmployeeDto employeeResDto = this.employerService.createEmployee(employeeDtoReq);
		assertThat("The firstname is not as per the expectation", employeeResDto.getFirstName(),
				equalTo(employeeDtoReq.getFirstName()));

		List<Employee> found = employeeDao.findAll();
		assertThat("First name should be as expected",
				found.stream().anyMatch(e -> e.getFirstName().contains("testFirstName")), equalTo(Boolean.TRUE));
	}

	@Test(expected = Exception.class)
	public void createEmployee_Expect_Exception() throws IOException, Exception {
		employeeDtoReq.getDepartment().setDepartmentId(250l);
		EmployeeDto employeeResDto = this.employerService.createEmployee(employeeDtoReq);
		assertThat("The firstname is not as per the expectation", employeeResDto.getFirstName(),
				equalTo(employeeDtoReq.getFirstName()));
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployeeFirstNameASCPage1() throws IOException, Exception {

		EmployeeListDto employeeListDto = this.employerService.getEmployeesByCriteria(searchAndSortCriteriaReqDto);
		assertThat("The total count is not as per the expectation", employeeListDto.getTotalCount(), equalTo(6l));
		assertThat("The employee list should not be null", employeeListDto.getEmployeeList(), notNullValue());
		assertThat("The lastname at position one should be as per the expectation",
				employeeListDto.getEmployeeList().get(0).getLastName(), equalTo("Kelly"));
		assertThat("The department id for the employee should be as per the expectation",
				employeeListDto.getEmployeeList().get(0).getDepartment().getDepartmentId(), equalTo(1l));
		assertThat("The department name for the employee should be as per the expectation",
				employeeListDto.getEmployeeList().get(0).getDepartment().getDepartmentName(),
				equalTo("Human Resource"));
		assertThat("The lastname at position two should be as per the expectation",
				employeeListDto.getEmployeeList().get(1).getLastName(), equalTo("Bush"));
		assertThat("The lastname at position three should be as per the expectation",
				employeeListDto.getEmployeeList().get(2).getLastName(), equalTo("Phillip"));
		assertThat("The lastname at position four should be as per the expectation",
				employeeListDto.getEmployeeList().get(3).getLastName(), equalTo("Curtin"));
		assertThat("The lastname at position five should be as per the expectation",
				employeeListDto.getEmployeeList().get(4).getLastName(), equalTo("Parrish"));
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployeeFirstNameASCPage2() throws IOException, Exception {

		searchAndSortCriteriaReqDto.setPageNo(1);
		EmployeeListDto employeeListDto = this.employerService.getEmployeesByCriteria(searchAndSortCriteriaReqDto);
		assertThat("The total count is not as per the expectation", employeeListDto.getTotalCount(), equalTo(6l));
		assertThat("The employee list should not be null", employeeListDto.getEmployeeList(), notNullValue());
		assertThat("The lastname at position one should be as per the expectation",
				employeeListDto.getEmployeeList().get(0).getLastName(), equalTo("Ellis"));
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployeeFirstNameDESCPage1() throws IOException, Exception {

		searchAndSortCriteriaReqDto.getSortCriteria().put("firstName", Boolean.TRUE);
		EmployeeListDto employeeListDto = this.employerService.getEmployeesByCriteria(searchAndSortCriteriaReqDto);
		assertThat("The total count is not as per the expectation", employeeListDto.getTotalCount(), equalTo(6l));
		assertThat("The employee list should not be null", employeeListDto.getEmployeeList(), notNullValue());
		assertThat("The lastname at position five should be as per the expectation",
				employeeListDto.getEmployeeList().get(4).getLastName(), equalTo("Bush"));
		assertThat("The lastname at position four should be as per the expectation",
				employeeListDto.getEmployeeList().get(3).getLastName(), equalTo("Phillip"));
		assertThat("The lastname at position three should be as per the expectation",
				employeeListDto.getEmployeeList().get(2).getLastName(), equalTo("Curtin"));
		assertThat("The lastname at position two should be as per the expectation",
				employeeListDto.getEmployeeList().get(1).getLastName(), equalTo("Parrish"));
		assertThat("The lastname at position one should be as per the expectation",
				employeeListDto.getEmployeeList().get(0).getLastName(), equalTo("Ellis"));
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getEmployeeFirstNameDESCPage2() throws IOException, Exception {
		searchAndSortCriteriaReqDto.setPageNo(1);
		searchAndSortCriteriaReqDto.getSortCriteria().put("firstName", Boolean.TRUE);
		EmployeeListDto employeeListDto = this.employerService.getEmployeesByCriteria(searchAndSortCriteriaReqDto);
		assertThat("The total count is not as per the expectation", employeeListDto.getTotalCount(), equalTo(6l));
		assertThat("The employee list should not be null", employeeListDto.getEmployeeList(), notNullValue());
		assertThat("The lastname at position one should be as per the expected",
				employeeListDto.getEmployeeList().get(0).getLastName(), equalTo("Kelly"));
	}

	@Test(expected = NoDataFoundException.class)
	public void getEmployeeNoDataFound() throws IOException, Exception {
		this.employerService.getEmployeesByCriteria(searchAndSortCriteriaReqDto);
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql"),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/afterTestRun.sql") })
	public void getAllDepartments() throws IOException, Exception {
		List<DepartmentDto> departmentListDto = this.employerService.getAllDepartments();
		assertThat("The department list is null", departmentListDto, notNullValue());
		assertThat("The total count is not as per the expectation", departmentListDto.size(), equalTo(3));

		assertThat("'Human Resource' department should exist",
				departmentListDto.stream().anyMatch(e -> e.getDepartmentName().contains("Human Resource")),
				equalTo(Boolean.TRUE));
		assertThat("'Information Technology' department should exist",
				departmentListDto.stream().anyMatch(e -> e.getDepartmentName().contains("Information Technology")),
				equalTo(Boolean.TRUE));
		assertThat("Finance", departmentListDto.stream().anyMatch(e -> e.getDepartmentName().contains("Finance")),
				equalTo(Boolean.TRUE));

	}

	@Test(expected = NoDataFoundException.class)
	public void getAllDepartmentsNoDataFound() throws IOException, Exception {
		this.employerService.getAllDepartments();
	}

}
