package com.hubstaff.jobs.jobuploadDb.model.search;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;

import io.swagger.annotations.ApiParam;

import com.hubstaff.jobs.jobuploadDb.domain.AvailabilityType;
import com.hubstaff.jobs.jobuploadDb.repository.SearchField;
import com.hubstaff.jobs.jobuploadDb.repository.SearchRequest;
import com.hubstaff.jobs.jobuploadDb.util.DateUtility;

public class JobsSearchRequest implements SearchRequest {

    @SearchField(value = "title")
    private String title;
    private String description;
    private String availability;
    @SearchField(value = "availability")
    private List<String> availabilityTypes= new ArrayList<>();
    private String company;
    private String location;
    private long createdDate;
    @SearchField(value = "keyword")
    private String keyword;
    @SearchField(value = "after")
    private long after;
    @SearchField(value = "before")
    private long before;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public JobsSearchRequest setDescription(String description) {
		this.description = description;
		return this;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
		if(availability!=null && !availability.isEmpty())
			updateAvailabilityTypes();
	}
	public List<String> getAvailabilityTypes() {
		return availabilityTypes;
	}
	public void setAvailabilityTypes(List<String> availabilityTypes) {
		this.availabilityTypes = availabilityTypes;
	}
	private void updateAvailabilityTypes() {
		for (String string : this.availability.split(",")) {
			this.availabilityTypes.add(string);
		}
	}
	public String getCompany() {
		return company;
	}
	public JobsSearchRequest setCompany(String company) {
		this.company = company;
		return this;
	}
	public String getLocation() {
		return location;
	}
	public JobsSearchRequest setLocation(String location) {
		this.location = location;
		return this;
	}
	public long getCreatedDate() {
		return createdDate;
	}
	public JobsSearchRequest setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public long getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = DateUtility.epochFromISO8601Date(after);
	}
	public long getBefore() {
		return before;
	}
	public void setBefore(String before) {
		this.before = DateUtility.epochFromISO8601Date(before);
	}
}