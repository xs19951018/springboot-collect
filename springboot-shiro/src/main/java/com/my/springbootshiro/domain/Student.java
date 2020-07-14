package com.my.springbootshiro.domain;

import java.io.Serializable;

public class Student implements Serializable {
    private String stuUuid;

    private String teaUuid;

    private String stuName;

    private static final long serialVersionUID = 1L;

    public String getStuUuid() {
        return stuUuid;
    }

    public void setStuUuid(String stuUuid) {
        this.stuUuid = stuUuid;
    }

    public String getTeaUuid() {
        return teaUuid;
    }

    public void setTeaUuid(String teaUuid) {
        this.teaUuid = teaUuid;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
}