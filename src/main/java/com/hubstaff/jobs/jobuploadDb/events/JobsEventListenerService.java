package com.hubstaff.jobs.jobuploadDb.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.hubstaff.jobs.jobuploadDb.domain.JobEvent;
import com.hubstaff.jobs.jobuploadDb.repository.Jobs;
import com.hubstaff.jobs.jobuploadDb.repository.JobsJpaRepository;
import com.hubstaff.jobs.jobuploadDb.repository.JobsRepository;
import com.hubstaff.jobs.jobuploadDb.service.JobsService;

//@Service
@EnableBinding(JobsEventInputChannel.class)
public class JobsEventListenerService {

    @Autowired
    JobsJpaRepository jobsJpaRepository;
    
    @Autowired
    JobsService jobsService;

    @StreamListener(JobsEventInputChannel.CUSTOMER_JOURNEY_EVENT_CHANNEL)
    public void processEvent(@Payload JobEvent job) {
    	System.out.println("Message Recieved ==============================");
    	System.out.println(job.getTitle() + " " + job.getDescription());
    	
    	jobsService.createJob(job);
    }
}