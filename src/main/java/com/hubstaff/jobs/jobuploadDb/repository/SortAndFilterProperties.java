package com.hubstaff.jobs.jobuploadDb.repository;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class SortAndFilterProperties {
    private List<SortDefinition> defaultJobSort = new ArrayList<>();
    private SortProperties sort = new SortProperties();

    public List<SortDefinition> getDefaultJobSort() {
        return defaultJobSort;
    }

    public SortAndFilterProperties setDefaultJobSort(List<SortDefinition> defaultJobSort) {
        this.defaultJobSort = defaultJobSort;
        return this;
    }

    public SortProperties getSort() {
        return sort;
    }

    public void setSort(SortProperties sort) {
        this.sort = sort;
    }

    public static class SortProperties {
        private List<SortField> jobs = new ArrayList<>();
        private SortedMap<String, SortField> jobsFieldMap;

        public SortedMap<String, SortField> getProductsFieldMap() {

//            if (productsFieldMap == null) {
//                productsFieldMap = MapUtils.listToTreeMap(products, SortField::getCode);
//            }
            return jobsFieldMap;
        }

        public List<SortField> getJobs() {
            return jobs;
        }

        public void setJobs(List<SortField> jobs) {
            this.jobs = jobs;
        }
    }

    public static class SortDefinition {
        private String fieldName;
        private SortDirection direction;

        public String getFieldName() {
            return fieldName;
        }

        public SortDefinition setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public SortDirection getDirection() {
            return direction;
        }

        public SortDefinition setDirection(SortDirection direction) {
            this.direction = direction;
            return this;
        }

        public enum SortDirection {
            ASC,
            DESC
        }
    }
}