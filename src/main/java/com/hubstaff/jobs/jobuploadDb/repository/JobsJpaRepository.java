package com.hubstaff.jobs.jobuploadDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hubstaff.jobs.jobuploadDb.domain.JobEvent;

@Repository
public interface JobsJpaRepository extends JpaRepository<Jobs, String> {

}