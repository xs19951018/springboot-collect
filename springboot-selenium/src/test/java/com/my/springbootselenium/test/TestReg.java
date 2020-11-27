package com.my.springbootselenium.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TestReg {

    static Pattern appear_reg_pat = Pattern.compile("<p>(您好，您管理的网络.+?)：<\\/p><p>\\n告警来源：(.+?)<br>告警级别：(.+?)<br>设备IP：(.+?)<br>告警类型：(.+?)<br>告警产生时间：(.+?)<\\/p><p>(.+?)<\\/p>");
    static Pattern appear_regTwo_pat = Pattern.compile("<p>(您好，您管理的网络.+?)：<\\/p><p>\\n告警来源：(.+?)<br>告警级别：(.+?)<br>设备IP：(.+?)<br>告警类型：(.+?)<br>告警产生时间：(.+?)<\\/p>");
    static Pattern appear_alldown_reg_pat = Pattern.compile("<p>(您好，您管理的网络.+?)：<\\/p><p>\\n告警来源：(.+?)<br>告警级别：(.+?)<br>告警类型：(.+?)<br>告警产生时间：(.+?)<\\/p>");

    static Pattern disappear_reg_pat = Pattern.compile("<p>(您好，您管理的网络.+?)：<\\/p><p>\\n告警来源：(.+?)<br>设备IP：(.+?)<br>告警类型：(.+?)<br>告警产生时间：(.+?)<br>告警消除时间：(.+?)<br>告警持续时间：(.+?)<\\/p><p>(.+?)<\\/p>");
    static Pattern disappear_regTwo_pat = Pattern.compile("<p>(您好，您管理的网络.+?)：<\\/p><p>\\n告警来源：(.+?)<br>设备IP：(.+?)<br>告警类型：(.+?)<br>告警产生时间：(.+?)<br>告警消除时间：(.+?)<br>告警持续时间：(.+?)<\\/p>");

    public static void main(String[] args) {
        mailWarn();
        //        reduceMailWarn();
    }

    // 邮件告警
    public static void mailWarn() {
        JSONObject object = new JSONObject();

        log.info("========== 测试一开始 ==========");
        // 测试一
        String htmlMsg = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><p>您好，您管理的网络出现异常告警：</p><p>\n" +
                "告警来源：无锡丰茂烧烤（SN:G1PD597016944/24口交换机-丰茂烧烤-无锡）<br>告警级别：轻量<br>设备IP：192.168.123.91<br>" +
                "告警类型：交换机端口状态<br>告警产生时间：2020-11-26 01:49:34</p><p>设备端口Gi13状态变为Down</p><a href=\"http://cloud.ruijie.com.cn\">查看详情请点击这里</a>";
        String htmlMsg2 = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><p>您好，您管理的网络出现异常告警：</p><p>\n" +
                "告警来源：无锡丰茂烧烤（SN:G1PD597016944/24口交换机-丰茂烧烤-无锡）<br>告警级别：轻量<br>设备IP：192.168.123.91<br>" +
                "告警类型：交换机端口状态<br>告警产生时间：2020-11-26 01:49:34</p><a href=\"http://cloud.ruijie.com.cn\">查看详情请点击这里</a>";
        String htmlMsg3 = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><p>您好，您管理的网络出现异常告警：</p><p>\n" +
                "告警来源：张江测试路由2<br>告警级别：普通<br>告警类型：全网设备离线<br>告警产生时间：2020-11-25 19:01:08</p><p></p>" +
                "<a href=\"http://cloud.ruijie.com.cn\">查看详情请点击这里</a>";
        Matcher matcher = appear_reg_pat.matcher(htmlMsg3);
        // 1.有消息详情的,切换=>无消息
        if (!matcher.find()) {
            matcher.usePattern(appear_regTwo_pat);
        }
        // 2.无消息详情的,切换=>全设备离线
        if (!matcher.find(0)) {
            matcher.usePattern(appear_alldown_reg_pat);
            // 3.全设备离线
            if (matcher.find(0)) {
                int  warn_title = 2;
                object.put("warn_title", warn_title);
                object.put("warn_src", matcher.group(2));
                object.put("warn_level", matcher.group(3));
                object.put("warn_type", matcher.group(4));
                object.put("appear_time", matcher.group(5));
                log.info(object.toString());
                return;
            }
        }
        if (matcher.find(0)) {
            String string = matcher.group(1);//提取匹配到的结果
            int  warn_title = 1;
            object.put("warn_title", warn_title);
            object.put("warn_src", matcher.group(2));
            object.put("warn_level", matcher.group(3));
            object.put("device_ip", matcher.group(4));
            object.put("warn_type", matcher.group(5));
            object.put("appear_time", matcher.group(6));
            if (matcher.pattern() == appear_reg_pat) object.put("warn_desc", StringUtils.isBlank(matcher.group(7)) ? "" : matcher.group(7));
            log.info(object.toString());
        }
        log.info("========== 测试一结束 ==========");

    }

    // 告警消除
    public static void reduceMailWarn() {
        JSONObject object = new JSONObject();

        log.info("========== 测试二开始 ==========");
        // 测试一
        String htmlMsg = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><p>您好，您管理的网络告警已消除：</p><p>\n" +
                "告警来源：无锡丰茂烧烤（SN:G1PD597016944/24口交换机-丰茂烧烤-无锡）<br>设备IP：192.168.123.91<br>" +
                "告警类型：交换机端口状态<br>告警产生时间：2020-11-26 01:49:34<br>告警消除时间：2020-11-26 01:49:34<br>告警持续时间：0 天 0 小时 4 分 2 秒</p><p>设备端口Gi13状态变为Up</p><a href=\"http://cloud.ruijie.com.cn\">查看详情请点击这里</a>";
        String htmlMsg2 = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><p>您好，您管理的网络告警已消除：</p><p>\n" +
                "告警来源：无锡丰茂烧烤（SN:G1PD597016944/24口交换机-丰茂烧烤-无锡）<br>设备IP：192.168.123.91<br>" +
                "告警类型：交换机端口状态<br>告警产生时间：2020-11-26 01:49:34<br>告警消除时间：2020-11-26 01:49:34<br>告警持续时间：0 天 0 小时 4 分 2 秒</p><a href=\"http://cloud.ruijie.com.cn\">查看详情请点击这里</a>";
        Matcher matcher = disappear_reg_pat.matcher(htmlMsg2);
        // 1.有消息详情的,切换=>无消息
        if (!matcher.find()) {
            matcher.usePattern(disappear_regTwo_pat);
        }
        // 2.无消息详情的,切换=>全设备离线
        if (matcher.find(0)) {
            String string = matcher.group(1);//提取匹配到的结果
            int  warn_title = 1;
            object.put("warn_title", warn_title);
            object.put("warn_src", matcher.group(2));
            object.put("device_ip", matcher.group(3));
            object.put("warn_type", matcher.group(4));
            object.put("appear_time", matcher.group(5));
            object.put("disappear_time", matcher.group(6));
            object.put("duration", matcher.group(7));
            if (matcher.pattern() == disappear_reg_pat) object.put("warn_desc", StringUtils.isBlank(matcher.group(8)) ? "" : matcher.group(8));
            log.info(object.toString());
        }
        log.info("========== 测试二结束 ==========");
    }
}
