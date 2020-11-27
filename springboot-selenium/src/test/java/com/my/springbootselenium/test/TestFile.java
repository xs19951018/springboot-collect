package com.my.springbootselenium.test;

import java.io.File;

public class TestFile {

    public static void main(String[] args) {
        String path = "http://p4.img.cctvpic.com/photoAlbum/page/performance/img/2020/11/27/1606441183085_265.png";
        File file = new File(path);
        System.out.println(file.getName());
        System.out.println(file.exists());

        System.out.println(path.replaceAll("/", "\\\\"));

    }
}
