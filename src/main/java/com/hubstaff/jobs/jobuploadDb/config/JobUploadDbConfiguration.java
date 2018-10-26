package com.hubstaff.jobs.jobuploadDb.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hubstaff.jobs.jobuploadDb.config.properties.JobUploadDbProperties;
import com.hubstaff.jobs.jobuploadDb.repository.SortAndFilterProperties;

@Configuration
public class JobUploadDbConfiguration {
    @Autowired
    private JobUploadDbProperties properties;

    @RefreshScope
    @Bean
    public SortAndFilterProperties sortAndFilterProperties() {
        return properties.getSortAndFilter();
    }
}