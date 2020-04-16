package com.sg.employer.dto;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SearchAndSortCriteriaReqDto {

	private int pageNo;
	private int pageSize;
	private List<SearchCriteriaDto> searchCriteria;
	private Map<String, Boolean> sortCriteria;

	public SearchAndSortCriteriaReqDto() {

	}

	public SearchAndSortCriteriaReqDto(int pageNo, int pageSize, List<SearchCriteriaDto> searchCriteria,
			Map<String, Boolean> sortCriteria) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.searchCriteria = searchCriteria;
		this.sortCriteria = sortCriteria;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<SearchCriteriaDto> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(List<SearchCriteriaDto> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public Map<String, Boolean> getSortCriteria() {
		return sortCriteria;
	}

	public void setSortCriteria(Map<String, Boolean> sortCriteria) {
		this.sortCriteria = sortCriteria;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("pageNo", pageNo).append("pageSize", pageSize)
				.append("searchCriteria", searchCriteria).append("sortCriteria", sortCriteria).toString();
	}
	
}
