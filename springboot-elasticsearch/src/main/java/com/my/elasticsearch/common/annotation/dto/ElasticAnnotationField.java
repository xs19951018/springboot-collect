package com.my.elasticsearch.common.annotation.dto;

import com.my.elasticsearch.common.annotation.ElasticField;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;

@Data
public class ElasticAnnotationField {

    private ElasticField fieldMeta;
    private Field field;
    private List<ElasticAnnotationField> annotationFieldList;

    public ElasticAnnotationField(ElasticField fieldMeta, java.lang.reflect.Field field) {
        super();
        this.fieldMeta = fieldMeta;
        this.field = field;
    }
}
