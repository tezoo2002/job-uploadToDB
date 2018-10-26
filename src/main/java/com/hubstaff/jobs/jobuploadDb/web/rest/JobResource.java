package com.hubstaff.jobs.jobuploadDb.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hubstaff.jobs.jobuploadDb.domain.AvailabilityType;
import com.hubstaff.jobs.jobuploadDb.domain.JobEvent;
import com.hubstaff.jobs.jobuploadDb.domain.JobsResponse;
import com.hubstaff.jobs.jobuploadDb.model.search.JobsSearchRequest;
import com.hubstaff.jobs.jobuploadDb.repository.Jobs;
import com.hubstaff.jobs.jobuploadDb.service.JobsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/jobs")
public class JobResource {
	
	@Autowired 
	JobsService jobsService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.ALL_VALUE)
	@ApiOperation(value = "create job", notes = "create job")
    public void createJob(@ApiParam(required = true) @RequestBody JobEvent job) {
		jobsService.createJob(job);
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "search job", notes = "search job")
    public List<JobsResponse> searchJob(JobsSearchRequest jobsSearchRequest) {
		return jobsService.searchJobs(jobsSearchRequest);
    }
	
}
