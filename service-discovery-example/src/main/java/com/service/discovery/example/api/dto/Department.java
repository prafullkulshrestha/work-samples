package com.service.discovery.example.api.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author prafullkulshrestha
 *
 */

public class Department {

	private long departmentId;
	private String description;
	private String departmentName;

	public Department() {
	}

	public Department(long departmentId) {
		this.departmentId = departmentId;
	}

	public Department(long departmentId, String departmentName, String description) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.description = description;
	}

	
	public long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

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
		if (!(other instanceof Department))
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
