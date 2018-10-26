package com.hubstaff.jobs.jobuploadDb.repository;
import java.util.List;

public class PageResult<T> {
    private List<T> content;
    private long totalElements;

    public List<T> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }
}