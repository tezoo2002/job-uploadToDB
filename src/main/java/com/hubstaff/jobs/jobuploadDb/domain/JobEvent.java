package com.hubstaff.jobs.jobuploadDb.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JobEvent {

    private String title;
    private String description;
    private AvailabilityType availability;
    private String company;
    private String location;
    private long createdDate;

    public JobEvent() {
    }

    public JobEvent(Long id, String title, String description) {
        this.title = title;
        this.description = description;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public AvailabilityType getAvailability() {
		return availability;
	}

	public void setAvailability(AvailabilityType availability) {
		this.availability = availability;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
    public String toString() {
        return "City{" + " title=" + title
                + ", description=" + description + '}';
    }

}
