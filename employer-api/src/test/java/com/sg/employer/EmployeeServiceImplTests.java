package com.sg.employer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sg.employer.dao.EmployeeDao;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.entity.Department;
import com.sg.employer.entity.Employee;
import com.sg.employer.service.EmployerService;
import com.sg.employer.service.impl.EmployerServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTests {
	
	private EmployerService employerService;
	@Mock
	private EmployeeDao employeeDao;
	private EmployeeDto employeeDtoReq;
	private Employee employeeRes;
	
	@Before
	public void setup() {
		this.employerService = new EmployerServiceImpl(employeeDao);
		this.employeeDtoReq = new EmployeeDto(new Employee(
				 0l
				, "testFirstName"
				,  "testLastName"
				,  'f'
				,  new Date()
				, new Department(200l)));
		this.employeeRes = new Employee(
				  100l
				, "testFirstName"
				,  "testLastName"
				,  'f'
				,  new Date()
				, new Department(200l));
	}
	
	@Test
	public void createEmployee() throws Exception {
		when(employeeDao.save(any())).thenReturn(this.employeeRes);
		EmployeeDto empDtoResp = this.employerService.createEmployee(employeeDtoReq);
		assertThat("The created emplyee is should be as expected", empDtoResp.getEmployee().getEmployeeId(), equalTo(100l));
		verify(employeeDao, times(1)).save(any());
	}

}