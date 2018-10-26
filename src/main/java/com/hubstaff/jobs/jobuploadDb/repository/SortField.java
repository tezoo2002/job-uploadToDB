package com.hubstaff.jobs.jobuploadDb.repository;

public class SortField {
    private String indexFieldName;
    private String displayName;
    private String code;
    private SortDirection direction;

    public SortDirection getDirection() {
        return direction;
    }

    public SortField setDirection(SortDirection direction) {
        this.direction = direction;
        return this;
    }

    public String getIndexFieldName() {
        return indexFieldName;
    }

    public SortField setIndexFieldName(String indexFieldName) {
        this.indexFieldName = indexFieldName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SortField setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getCode() {
        return code;
    }

    public SortField setCode(String code) {
        this.code = code;
        return this;
    }

    public enum SortDirection {
        ASC,
        DESC
    }
}