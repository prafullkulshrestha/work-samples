package com.sg.employer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author prafullkulshrestha
 *
 */
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

	private static final long serialVersionUID = -6912422684240024345L;
	private long employeeId;
	private String firstName;
	private String lastName;
	private char gender;
	private Date dateOfBirth;
	private Department department;

	public Employee() {
		super();
	}

	public Employee(long employeeId, Department department) {
		super();
		this.employeeId = employeeId;
		this.department = department;
	}

	public Employee(long employeeId, String firstName, String lastName, char gender, Date dateOfBirth,
			Department department) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.department = department;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", unique = true, nullable = false)
	public long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "first_name", unique = true, length = 100)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	@Column(name = "last_name", length = 100)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "gender", length = 1)
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", length = 13)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = false)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("employeeId", employeeId)
				.append("firstName", firstName).append("lastName", lastName).append("gender", gender)
				.append("dateOfBirth", dateOfBirth).append("department", department).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {

		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Employee))
			return false;

		Employee castOther = (Employee) other;

		return new EqualsBuilder().append(firstName, castOther.firstName).append(lastName, castOther.lastName)
				.append(gender, castOther.gender).append(dateOfBirth, castOther.dateOfBirth)
				.append(department, castOther.department).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return new HashCodeBuilder().append(firstName).append(lastName).append(gender).append(dateOfBirth)
				.append(department).toHashCode();
	}

}
