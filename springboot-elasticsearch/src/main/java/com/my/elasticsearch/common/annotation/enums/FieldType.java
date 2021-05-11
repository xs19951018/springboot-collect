package com.my.elasticsearch.common.annotation.enums;

public enum FieldType {
    Auto,
    Text,
    Keyword,
    Long,
    Integer,
    Short,
    Byte,
    Double,
    Float,
    Half_Float,
    Scaled_Float,
    Date,
    Date_Nanos,
    Boolean,
    Binary,
    Integer_Range,
    Float_Range,
    Long_Range,
    Double_Range,
    Date_Range,
    Ip_Range,
    Object,
    Nested,
    Ip,
    TokenCount,
    Percolator,
    Flattened,
    Search_As_You_Type;

    private FieldType() {
    }
}
