package com.my.elasticsearch.entity.domain;

import com.my.elasticsearch.common.annotation.ElasticField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "elastic", type = "student")
public class Student {

    @Id
    @ElasticField(value = "id", type = FieldType.Long)
    private Long id;

    @ElasticField(value = "name", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;

    @ElasticField(value = "stuNo", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String stuNo;

    @ElasticField(value = "room", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String room;

}
