package com.my.springbootselenium.util.entity;

import lombok.Data;

@Data
public class FileInfo {

    private String name;

    private String suffix;//后缀

    private String size;

    private String saveUrl;//存储地址
}
