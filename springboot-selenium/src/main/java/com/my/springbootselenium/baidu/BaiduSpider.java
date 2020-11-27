package com.my.springbootselenium.baidu;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

@Slf4j
public class BaiduSpider {

    public static void main(String[] args) {
        String url = "http://www.baidu.com";
        WebDriver driver = new HtmlUnitDriver(false);
        driver.get(url);
        driver.findElement(By.id("kw")).sendKeys("pixiv");
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).perform();
        log.info("========== 获取标题开始 ==========");
        log.info(driver.getTitle());
        log.info("========== 获取标题结束 ==========");
    }
}
