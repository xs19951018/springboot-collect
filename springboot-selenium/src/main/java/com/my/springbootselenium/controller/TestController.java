package com.my.springbootselenium.controller;

import com.my.springbootselenium.util.FileHepler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @Autowired
    private FileHepler fileHepler;

    public void test() {
        System.out.println(11);
    }
}
