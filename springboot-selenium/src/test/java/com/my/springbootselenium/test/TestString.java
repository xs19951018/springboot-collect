package com.my.springbootselenium.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestString {

    public static void main(String[] args) throws ParseException {
        test2();
    }

    static void test2() {
        String str = "111111[点击查看详情](http://cloud.ruijie.com.cn)222222";

        str = str.replaceAll("\\[点击查看详情\\]\\(http://cloud.ruijie.com.cn\\)", "");
        System.out.println(str);
    }

    static void test1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "09:30:00";
        String time2 = "10:00:00";
        Date date = sdf.parse(time);
        Date date2 = sdf.parse(time2);
        int n = date.compareTo(date2);

        System.out.println(sdf.parse(time));
        System.out.println(sdf2.format(sdf.parse(time)));
        System.out.println(sdf2.format(sdf.parse(time2)));

        System.out.println("比较1：" + n);
//        System.out.println("比较2：" + date2.compareTo(date));

        System.out.println(String.valueOf(null));
    }
}
