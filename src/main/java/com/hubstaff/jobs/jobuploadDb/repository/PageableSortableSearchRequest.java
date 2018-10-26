package com.hubstaff.jobs.jobuploadDb.repository;

import java.util.List;

public interface PageableSortableSearchRequest extends SearchRequest {
    Integer getLimit();

    Integer getOffset();

    List<String> getSortBy();

    Boolean getSortAsc();
}