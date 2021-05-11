package com.my.elasticsearch.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.elasticsearch.annotations.*;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface ElasticField {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    FieldType type() default FieldType.Auto;

    String searchAnalyzer() default "";

    String analyzer() default "";

    boolean index() default true;

}
