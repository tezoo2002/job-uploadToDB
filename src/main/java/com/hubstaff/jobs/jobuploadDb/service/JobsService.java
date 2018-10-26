package com.hubstaff.jobs.jobuploadDb.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubstaff.jobs.jobuploadDb.domain.JobEvent;
import com.hubstaff.jobs.jobuploadDb.domain.JobsResponse;
import com.hubstaff.jobs.jobuploadDb.model.search.JobsSearchRequest;
import com.hubstaff.jobs.jobuploadDb.repository.Jobs;
import com.hubstaff.jobs.jobuploadDb.repository.JobsJpaRepository;
import com.hubstaff.jobs.jobuploadDb.repository.JobsRepository;

@Service
public class JobsService {

    @Autowired
    JobsJpaRepository jobsJpaRepository;
    @Autowired
    JobsRepository jobsRepository;

    public void createJob(JobEvent job) {
    	Jobs jobEntity = new Jobs();
    	jobEntity.setTitle(job.getTitle());
    	jobEntity.setDescription(job.getDescription());
    	Date date = new Date();
    	jobEntity.setCreatedDate((job.getCreatedDate()==0)?date.getTime():job.getCreatedDate());
    	jobEntity.setAvailability(job.getAvailability().toString());
    	jobEntity.setCompany(job.getCompany());
    	jobEntity.setLocation(job.getLocation());
    	jobsJpaRepository.save(jobEntity);
    }
    
    public List<JobsResponse> searchJobs(JobsSearchRequest jobsSearchRequest) {
	    return jobsRepository.findBySearchRequest(jobsSearchRequest, Jobs.class)
	    		.parallelStream()
	    		.map(job -> mapJobToResponse(job))
	    		.collect(Collectors.toList());
    }
    
    private static JobsResponse mapJobToResponse(Jobs job) {
    	JobsResponse jobsResponse =new JobsResponse();
    	return jobsResponse.setAvailability(job.getAvailability())
    				.setCompany(job.getCompany())
    				.setCreatedDate(job.getCreatedDate())
    				.setDescription(job.getDescription())
    				.setId(job.getId())
    				.setLocation(job.getLocation())
    				.setTitle(job.getTitle());
    	
    }
}