package com.hubstaff.jobs.jobuploadDb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.hubstaff.jobs.jobuploadDb.util.DateUtility;

import org.apache.http.client.utils.DateUtils;

import com.hubstaff.jobs.jobuploadDb.domain.AvailabilityType;


public class JobsResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    private String availability;
    private String company;
    private String location;
    private String createdDate;

    public JobsResponse() {
    }

    public JobsResponse(Long id, String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
		return id;
	}

	public JobsResponse setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public JobsResponse setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public JobsResponse setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public JobsResponse setCreatedDate(long createdDate) {
		this.createdDate = DateUtility.formatDateToISO8601(createdDate);
		return this;
	}

	public String getAvailability() {
		return availability;
	}

	public JobsResponse setAvailability(String availability) {
		this.availability = availability;
		return this;
	}

	public String getCompany() {
		return company;
	}

	public JobsResponse setCompany(String company) {
		this.company = company;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public JobsResponse setLocation(String location) {
		this.location = location;
		return this;
	}

	@Override
    public String toString() {
        return "City{" + "id=" + id + ", title=" + title
                + ", description=" + description + '}';
    }
}