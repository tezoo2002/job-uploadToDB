package com.hubstaff.jobs.jobuploadDb.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtility {

    private static final Logger logger = LoggerFactory.getLogger(DateUtility.class);

    private static final String EXCEPTION_LOGGER = "Exception while formatting the date";
    private static final String DATE_OUTPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String UTC = "UTC";

    private DateUtility() {
    }

    public static String formatDateToISO8601(Long dateValue) {
        String dateString = "";
        try {
            if (Objects.nonNull(dateValue)) {
                LocalDateTime date = Instant.ofEpochMilli(dateValue).atZone(ZoneId.of(UTC)).toLocalDateTime();
                ZonedDateTime zonedDateTime = ZonedDateTime.of(date, ZoneId.of(UTC));
                dateString = DateTimeFormatter.ofPattern(DATE_OUTPUT_FORMAT).format(zonedDateTime);
            }
        } catch (Exception e) {
            logger.error(EXCEPTION_LOGGER, e);
        }
        return dateString;
    }
    
    public static String formatTimeStampToISO8601Date(String dateValue) {
        try {
            // Assuming the dateValue is coming in UTC
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STAMP_FORMAT);
            LocalDateTime formatDateTime = LocalDateTime.parse(dateValue, formatter);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(formatDateTime, ZoneId.of(UTC));
            return DateTimeFormatter.ofPattern(DATE_OUTPUT_FORMAT).format(zonedDateTime);
        } catch (Exception e) {
                logger.error(EXCEPTION_LOGGER,e);
            return dateValue;
        }
    }
    
    public static long epochFromISO8601Date(String dateValue) {
        try {
            // Assuming the dateValue is coming in UTC
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STAMP_FORMAT);
            LocalDateTime formatDateTime = LocalDateTime.parse(dateValue + " 00:00:00", formatter);
            return formatDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
        } catch (Exception e) {
                logger.error(EXCEPTION_LOGGER,e);
            return 0;
        }
    }
}
