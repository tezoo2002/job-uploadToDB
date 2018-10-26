package com.hubstaff.jobs.jobuploadDb.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface JobsEventInputChannel {

    String CUSTOMER_JOURNEY_EVENT_CHANNEL = "surya";

    @Input(JobsEventInputChannel.CUSTOMER_JOURNEY_EVENT_CHANNEL)
    MessageChannel notificationRequestEvents();

}