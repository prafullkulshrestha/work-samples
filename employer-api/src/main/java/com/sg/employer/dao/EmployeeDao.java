package com.sg.employer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.employer.entity.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
