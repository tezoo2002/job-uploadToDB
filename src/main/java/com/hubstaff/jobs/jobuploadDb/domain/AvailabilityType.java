package com.hubstaff.jobs.jobuploadDb.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AvailabilityType {

    PARTTIME("PartTime"),
    FULLTIME("FullTime");

    private final String value;

    AvailabilityType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static AvailabilityType fromValue(String text) {
        for (AvailabilityType availabilityType : AvailabilityType.values()) {
            if (String.valueOf(availabilityType.value).equalsIgnoreCase(text)) {
                return availabilityType;
            }
        }
        return null;
    }
}