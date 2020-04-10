package com.sg.employer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.employer.dao.DepartmentDao;
import com.sg.employer.dao.EmployeeDao;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.entity.Employee;
import com.sg.employer.service.EmployerService;

/**
 * The implementation service class for defining organization specific business
 * logic
 * 
 * @author prafullkulshrestha
 *
 */
@Service("employeeService")
public class EmployerServiceImpl implements EmployerService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private EmployeeDao employeeDao;

	@Autowired
	public EmployerServiceImpl(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/* (non-Javadoc)
	 * @see com.sg.employer.service.EmployerService#saveEmployee(com.sg.employer.dto.EmployeeDto)
	 */
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		log.debug("Service reuest received to create the employee {}", employeeDto.getEmployee());
		Employee employeeRes = employeeDao.save(employeeDto.getEmployee());
		log.debug("Service reuest received to create the employee {} completed", employeeRes);
		return new EmployeeDto(employeeRes);
	}
	
	
	
}
