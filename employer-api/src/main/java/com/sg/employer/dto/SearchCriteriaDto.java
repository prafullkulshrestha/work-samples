package com.sg.employer.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchCriteriaDto {
		
		private String key;
	    private String operation;
	    private Object value;
	    
		public SearchCriteriaDto() {
			super();
		}

		public SearchCriteriaDto(String key, String operation, Object value) {
			super();
			this.key = key;
			this.operation = operation;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {

			return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("key", key).append("operation", operation)
					.append("value", value).toString();
		}
}
