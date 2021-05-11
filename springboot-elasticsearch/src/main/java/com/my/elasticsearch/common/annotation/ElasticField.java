package com.my.elasticsearch.common.annotation;

import com.my.elasticsearch.common.annotation.enums.FieldType;
import org.elasticsearch.search.sort.SortOrder;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited
public @interface ElasticField {

    String value() default "";

    FieldType type() default FieldType.Auto;

    String index() default "";

    String searchAnalyzer() default "";

    String analyzer() default "";

    boolean search() default false;

    /**
     * 是否排序
     * text 不能排序,换成 keyword
     * @return
     */
    boolean sort() default false;

    SortOrder sortOrder() default SortOrder.ASC;

}
