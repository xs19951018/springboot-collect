package com.my.elasticsearch.entity.domain;

import com.my.elasticsearch.common.annotation.ElasticField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "user", type = "user")
public class User {

    @Id
    @ElasticField(value = "id", type = FieldType.Long)
    private Long id;

    @ElasticField(value = "name", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;

    @ElasticField(value = "desc", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String desc;

    @ElasticField(value = "age", type = FieldType.Integer)
    private int age;

}
