package com.my.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.my.elasticsearch.entity.domain.Student;
import com.my.elasticsearch.entity.domain.User;
import com.my.elasticsearch.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@Slf4j
@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Test
    void createIndexTest() throws Exception {
        boolean b = elasticSearchService.createIndex("test_index");
        log.info("创建索引：{}", b);
    }

    @Test
    void createIndexTest2() throws Exception {
        boolean b = elasticSearchService.createIndex(User.class);
        log.info("创建索引2：{}", b);
    }

    @Test
    void existIndexTest() throws Exception {
        boolean b = elasticSearchService.existIndex("test_index");
        log.info("判断索引是否存在：{}", b);
    }

    @Test
    void deleteIndexTest() throws Exception {
        boolean b = elasticSearchService.deleteIndex("test_index");
        log.info("删除索引：{}", b);
    }

    @Test
    void addDocumentTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setAge(12);
        user.setName("小明");
        user.setDesc("活泼开朗");

        boolean b = elasticSearchService.addDocument("elastic", user.getId().toString(), JsonUtils.objectToJson(user));
        log.info("新增文档：{}", b);
    }

    @Test
    void addDocumentTest2() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setAge(17);
        user.setName("小刚");
        user.setDesc("文静秀气");

        boolean b = elasticSearchService.addDocument("elastic", user.getId().toString(), JsonUtils.objectToJson(user));
        log.info("新增文档2：{}", b);
    }

    @Test
    void addDocumentTest3() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("钱多多");
        student.setStuNo("20140101");
        student.setRoom("九年级二班");

        boolean b = elasticSearchService.addDocument("elastic", student.getId().toString(), JsonUtils.objectToJson(student));
        log.info("新增文档2：{}", b);
    }

    @Test
    void isExistsDocumentTest() throws Exception {
        boolean b = elasticSearchService.isExistsDocument("test_index", "1");
        log.info("判断是否存在文档：{}", b);
    }

    @Test
    void getDocumentTest() throws Exception {
        String document = elasticSearchService.getDocument("test_index", "1");
        log.info("获取文档：{}", document);
    }

    @Test
    void updateDocumentTest() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setAge(33);
        user.setName("测试name");
        user.setDesc("测试des");

        boolean b = elasticSearchService.updateDocument("test_index", user.getId().toString(), JsonUtils.objectToJson(user));
        log.info("更新文档：{}", b);
    }

    @Test
    void deleteDocumentTest() throws Exception {
        boolean b = elasticSearchService.deleteDocument("test_index", "1");
        log.info("删除文档：{}", b);
    }

    @Test
    void bulkRequestTest() throws Exception {
        // 批量插入
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId((long) i);
            user.setAge(i);
            user.setName("测试name" + i);
            user.setDesc("测试des" + i);
            users.add(user);
        }

        elasticSearchService.bulkRequest("test_index", users);
    }

    @Test
    void searchRequestTest() throws Exception {
        List<Map<String, Object>> result = elasticSearchService.searchRequest("elastic", "文静开朗");
        log.info("搜索请求response：{}", JSON.toJSONString(result));
    }

    @Test
    void searchAllRequestTest() throws Exception {
        // 搜索请求
        List<Integer> result = elasticSearchService.searchAllRequest("test_index");
        log.info("搜索请求response：{}", JSON.toJSONString(result));
    }

    @BeforeEach
    void testBefore(){
        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter(){
        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
