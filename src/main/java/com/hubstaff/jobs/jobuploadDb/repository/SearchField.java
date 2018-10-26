package com.hubstaff.jobs.jobuploadDb.repository;
import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchField {
    /**
     * The name of the ES field that will be searched for the value of the annotated field.
     */
    @AliasFor("fieldName")
    String value() default "";

    /**
     * The name of the ES field that will be searched for the value of the annotated field.
     */
    @AliasFor("value")
    String fieldName() default "";
}