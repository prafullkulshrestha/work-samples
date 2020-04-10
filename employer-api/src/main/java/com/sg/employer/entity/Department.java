package com.sg.employer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author prafullkulshrestha
 *
 */
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {

	private static final long serialVersionUID = -6912422684240024345L;
	private long departmentId;
	private String description;
	private String departmentName;

	public Department() {
	}

	public Department(long departmentId) {
		this.departmentId = departmentId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id", unique = true, nullable = false)
	public long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "department_name", length = 100)
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("departmentId", departmentId)
				.append("departmentName", departmentName).append("description", description).toString();
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

		Department castOther = (Department) other;

		return new EqualsBuilder().append(departmentName, castOther.departmentName)
				.append(description, castOther.description).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return new HashCodeBuilder().append(departmentName).append(description).toHashCode();
	}

}
