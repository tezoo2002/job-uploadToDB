package com.hubstaff.jobs.jobuploadDb.repository;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import com.hubstaff.jobs.jobuploadDb.domain.JobEvent;

public interface SearchRequest {
    default List<Predicate<JobEvent>> getVariantFilters() {
        return Collections.emptyList();
    }
}