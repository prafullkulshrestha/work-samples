package com.sg.employer.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sg.employer.dao.DepartmentDao;
import com.sg.employer.dao.EmployeeDao;
import com.sg.employer.dto.DepartmentDto;
import com.sg.employer.dto.EmployeeDto;
import com.sg.employer.dto.EmployeeListDto;
import com.sg.employer.dto.SearchAndSortCriteriaReqDto;
import com.sg.employer.entity.Department;
import com.sg.employer.entity.Employee;
import com.sg.employer.exception.NoDataFoundException;
import com.sg.employer.service.EmployerService;
import com.sg.employer.utils.EmployerApiApplicationConstants;

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
	private DepartmentDao departmentDao;
	
	@Autowired
	public EmployerServiceImpl(EmployeeDao employeeDao, DepartmentDao departmentDao) {
		this.employeeDao = employeeDao;
		this.departmentDao = departmentDao;
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
	
	/* (non-Javadoc)
	 * @see com.sg.employer.service.EmployerService#getEmployeesByCriteria(com.sg.employer.dto.SearchAndSortCriteriaReqDto)
	 */
	@Override
	public EmployeeListDto getEmployeesByCriteria(SearchAndSortCriteriaReqDto searchAndSortCriteriaReqDto)
			throws ParseException {

		int pageNo = searchAndSortCriteriaReqDto.getPageNo();
		int pageSize = searchAndSortCriteriaReqDto.getPageSize();
		Map<String, Boolean> sortCriteria = searchAndSortCriteriaReqDto.getSortCriteria();
		
		String sortKey = sortCriteria.keySet().toArray()[0].toString();
		Boolean sortValue = sortCriteria.get(sortKey);
		Sort sort = (!sortValue) ? Sort.by(sortKey).ascending() : Sort.by(sortKey).descending();
		Pageable allEmployeeSortedBySortKeyPageable = 
				  PageRequest.of(pageNo, pageSize, sort);
		
		Page<Employee> employeesPage = employeeDao.findAll(allEmployeeSortedBySortKeyPageable);
		long totalCount = employeesPage.getTotalElements();
		List<EmployeeDto> employeeDtos = new ArrayList<>();
		if (totalCount == 0l) {
			log.debug("No employees found");
			throw new NoDataFoundException("No employees found");
		}
		employeesPage.get().forEach(emp -> employeeDtos.add(new EmployeeDto(emp)));
		return new EmployeeListDto(employeeDtos, totalCount);
	}

	/* (non-Javadoc)
	 * @see com.sg.employer.service.EmployerService#getAllDepartments()
	 */
	@Override
	public List<DepartmentDto> getAllDepartments() {
		List<DepartmentDto> departmentDtos = new ArrayList<>();
		List<Department> departments = departmentDao.findAll(Sort.by(EmployerApiApplicationConstants.DEPARTMENT_NAME).ascending());
		if(CollectionUtils.isEmpty(departments)) {
			log.debug("No departments found");
			throw new NoDataFoundException("No departments found");
		}
		departments.stream().forEach(dept -> departmentDtos.add(new DepartmentDto(dept)));
		return departmentDtos;
	}

	
}
