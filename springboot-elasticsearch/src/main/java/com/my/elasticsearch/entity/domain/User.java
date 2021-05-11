package com.my.elasticsearch.entity.domain;

import com.my.elasticsearch.common.annotation.Document;
import com.my.elasticsearch.common.annotation.ElasticField;
import com.my.elasticsearch.common.annotation.enums.FieldType;
import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

@Data
@Document(index = "elastics_user")
public class User {

    @ElasticField(value = "id", type = FieldType.Long, sort = true, sortOrder = SortOrder.ASC)
    private Long id;

    @ElasticField(value = "name", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", search = true)
    private String name;

    @ElasticField(value = "desc", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", search = true)
    private String desc;

    @ElasticField(value = "age", type = FieldType.Integer)
    private int age;

}
