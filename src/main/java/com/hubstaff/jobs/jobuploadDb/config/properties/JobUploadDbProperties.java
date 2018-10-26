package com.hubstaff.jobs.jobuploadDb.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.hubstaff.jobs.jobuploadDb.repository.SortAndFilterProperties;

@RefreshScope
@Component
@ConfigurationProperties(prefix = "jobuploaddb")
public class JobUploadDbProperties {
    @NestedConfigurationProperty
    SortAndFilterProperties sortAndFilter = new SortAndFilterProperties();

    public SortAndFilterProperties getSortAndFilter() {
        return sortAndFilter;
    }

    public JobUploadDbProperties setSortAndFilter(SortAndFilterProperties sortAndFilter) {
        this.sortAndFilter = sortAndFilter;
        return this;
    }

    public static class Filter {
        private String pattern;
        private String replacement;

        public boolean isValid() {
            return !pattern.isEmpty();
        }

        public String getPattern() {
            return pattern;
        }

        public Filter setPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public String getReplacement() {
            return replacement;
        }

        public Filter setReplacement(String replacement) {
            this.replacement = replacement;
            return this;
        }
    }
}