package com.hubstaff.jobs.jobuploadDb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hubstaff.jobs.jobuploadDb.domain.JobEvent;

@Repository
public class JobsRepository extends BaseRepository<Jobs, JobsJpaRepository> {
    @Autowired
    private JobsJpaRepository jobsJpaRepository;

    @Autowired
    private SortAndFilterProperties sortAndFilterProperties;

    @Override
    protected JobsJpaRepository getInnerRepository() {
        return jobsJpaRepository;
    }

    @Override
    protected List<SortAndFilterProperties.SortDefinition> getDefaultSort() {
        return sortAndFilterProperties.getDefaultJobSort();
    }

//    public Set<String> retrieveAllHashes() {
//        return getInnerRepository().retrieveAllHashes();
//    }

}