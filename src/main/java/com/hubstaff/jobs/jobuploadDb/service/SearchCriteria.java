package com.hubstaff.jobs.jobuploadDb.service;

import java.util.List;

import com.hubstaff.jobs.jobuploadDb.domain.AvailabilityType;

public class SearchCriteria {
    private String keyword;
    private List<String> skills;
    private AvailabilityType availability;
    private String operation;
    private Object value;
    
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<String> getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	public AvailabilityType getAvailability() {
		return availability;
	}
	public void setAvailability(AvailabilityType availability) {
		this.availability = availability;
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
    
    
}