package com.my.springbootselenium.baidu;

import com.my.springbootselenium.baidu.entity.NewInfo;
import com.my.springbootselenium.util.FileHepler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CctvnewsSpider {

    private static FileHepler fileHepler = new FileHepler();

    public static void main(String[] args) {
        log.info("========== 获取新闻信息开始 ==========");
        String url = "https://news.cctv.com/";
        WebDriver driver = new HtmlUnitDriver(false);
        driver.get(url);
        List<WebElement> elements = driver.findElements(By.className("silde"));
        List<NewInfo> newInfos = new ArrayList<NewInfo>();
        NewInfo nInfo = null;
        String imgUrl = null;
        for (WebElement el : elements) {
            nInfo = new NewInfo();
            nInfo.setTitle(el.findElement(By.tagName("h3")).findElement(By.tagName("a")).getText());
            nInfo.setContent(el.findElement(By.tagName("p")).findElement(By.tagName("a")).getText());
            imgUrl = el.findElement(By.className("lazy")).getAttribute("data-src");
            if (StringUtils.isBlank(imgUrl)) imgUrl = el.findElement(By.className("lazy")).getAttribute("data-echo");
            nInfo.setImgUrl("http:" + imgUrl);
            newInfos.add(nInfo);
            // 文件下载
            fileHepler.downloadFronInet(nInfo.getImgUrl());
        }
        log.info(newInfos.toString());
        log.info("========== 获取新闻信息结束 ==========");
    }

}
