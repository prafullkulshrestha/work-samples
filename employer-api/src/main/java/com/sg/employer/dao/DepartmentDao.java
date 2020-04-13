package com.sg.employer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.employer.entity.Department;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Long> {

}
