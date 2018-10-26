package com.hubstaff.jobs.jobuploadDb.repository;
import java.util.List;

class QueryResultWithCount<T> {
    private List<T> resultList;
    private Long totalRecords;

    public QueryResultWithCount(List<T> resultList, Long totalRecords) {
        this.resultList = resultList;
        this.totalRecords = totalRecords;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }
}