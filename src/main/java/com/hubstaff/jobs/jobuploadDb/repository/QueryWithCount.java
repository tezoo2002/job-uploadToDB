package com.hubstaff.jobs.jobuploadDb.repository;
import java.util.Map;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;

@SuppressWarnings("squid:S1452")
public class QueryWithCount<T> {
    private final CriteriaQuery<T> query;
    private final CriteriaQuery<Long> countQuery;
    private final Map<String, Path<?>> pathMapping;

    public QueryWithCount(CriteriaQuery<T> query, CriteriaQuery<Long> countQuery, Map<String, Path<?>> pathMapping) {
        this.query = query;
        this.countQuery = countQuery;
        this.pathMapping = pathMapping;
    }

    public Map<String, Path<?>> getPathMapping() {
        return pathMapping;
    }

    public CriteriaQuery<T> getQuery() {
        return query;
    }

    public CriteriaQuery<Long> getCountQuery() {
        return countQuery;
    }
}