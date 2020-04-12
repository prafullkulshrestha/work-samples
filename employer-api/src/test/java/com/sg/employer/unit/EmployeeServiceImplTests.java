package com.sg.employer.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sg.employer.dao.DepartmentDao;
import com.sg.employer.dao.EmployeeDao;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.dto.EmployeeListDto;
import com.sg.employer.dto.SearchAndSortCriteriaReqDto;
import com.sg.employer.entity.Department;
import com.sg.employer.entity.Employee;
import com.sg.employer.service.EmployerService;
import com.sg.employer.service.impl.EmployerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTests {

	private EmployerService employerService;
	@Mock
	private EmployeeDao employeeDao;
	@Mock
	private DepartmentDao departmentDao;
	private EmployeeDto employeeDtoReq;
	private Employee employeeRes;
	private List<Employee> firstNameSrtedEmployeeList;
	private SearchAndSortCriteriaReqDto searchAndSortCriteriaForFirstPage;

	@Before
	public void setup() {
		this.employerService = new EmployerServiceImpl(employeeDao, departmentDao);
		this.employeeDtoReq = new EmployeeDto(
				new Employee(0l, "testFirstName", "testLastName", 'f', new Date(), new Department(200l)));
		this.employeeRes = new Employee(100l, "testFirstName", "testLastName", 'f', new Date(), new Department(200l));
		firstNameSrtedEmployeeList = Stream.of(
				new Employee(100l, "testFirstName1", "testLastName1", 'm', new Date(), new Department(501l)),
				new Employee(101l, "testFirstName2", "testLastName2", 'f', new Date(), new Department(502l))
				).collect(Collectors.toList());
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Boolean> sortCriteria = new HashMap() {
			{
				put("firstName", Boolean.FALSE);
			}
		};
		searchAndSortCriteriaForFirstPage = new SearchAndSortCriteriaReqDto(0, 5, null, sortCriteria);
	}

	@Test
	public void createEmployee() throws Exception {
		when(employeeDao.save(any())).thenReturn(this.employeeRes);
		EmployeeDto empDtoResp = this.employerService.createEmployee(employeeDtoReq);
		assertThat("The created emplyee should be as expected", empDtoResp.getEmployee().getEmployeeId(),
				equalTo(100l));
		verify(employeeDao, times(1)).save(any());
	}

	@Test(expected = ConstraintViolationException.class)
	public void createEmployeeException() throws Exception {
		when(employeeDao.save(any()))
				.thenThrow(new ConstraintViolationException("Department doesn't exist", null, "fk_employees_di"));
		this.employerService.createEmployee(employeeDtoReq);
	}

	@Test
	public void GetEmployeeByCriteria() throws Exception {
		Page<Employee> page = new PageImpl<>(firstNameSrtedEmployeeList, PageRequest.of(0, 5), 2l);
		when(employeeDao.findAll(any(Pageable.class))).thenReturn(page);
		EmployeeListDto empDtoResp = this.employerService.getEmployeesByCriteria(searchAndSortCriteriaForFirstPage);
		assertThat("The employee count should be as expected", empDtoResp.getTotalCount(),
				equalTo(2l));
		assertThat("First name should be as expected",
				empDtoResp.getEmployeeList().stream().anyMatch(e -> e.getFirstName().contains("testFirstName1")), equalTo(Boolean.TRUE));
		assertThat("First name should be as expected",
				empDtoResp.getEmployeeList().stream().anyMatch(e -> e.getFirstName().contains("testFirstName2")), equalTo(Boolean.TRUE));
		verify(employeeDao, times(1)).findAll(any(Pageable.class));
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void GetEmployeeByCriteriaNoDataFound() throws Exception {
		when(employeeDao.findAll(any(Pageable.class)))
				.thenThrow(new ConstraintViolationException("Department doesn't exist", null, "fk_employees_di"));
		this.employerService.getEmployeesByCriteria(searchAndSortCriteriaForFirstPage);
	}

}