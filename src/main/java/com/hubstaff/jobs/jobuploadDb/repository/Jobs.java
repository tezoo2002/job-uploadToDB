package com.hubstaff.jobs.jobuploadDb.repository;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hubstaff.jobs.jobuploadDb.domain.AvailabilityType;

@Entity
@Table(name = "JOBS")
public class Jobs implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;
    
    @Column
    private String description;
    
    @Column
    private String availability;
    
    @Column
    private String company;
    
    @Column
    private String location;
    
    @Column
    private long createdDate;

    public Jobs() {
    }

    public Jobs(Long id, String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
		return id;
	}

	public Jobs setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Jobs setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Jobs setDescription(String description) {
		this.description = description;
		return this;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public Jobs setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public String getAvailability() {
		return availability;
	}

	public Jobs setAvailability(String availability) {
		this.availability = availability;
		return this;
	}

	public String getCompany() {
		return company;
	}

	public Jobs setCompany(String company) {
		this.company = company;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public Jobs setLocation(String location) {
		this.location = location;
		return this;
	}

	@Override
    public String toString() {
        return "City{" + "id=" + id + ", title=" + title
                + ", description=" + description + '}';
    }
}