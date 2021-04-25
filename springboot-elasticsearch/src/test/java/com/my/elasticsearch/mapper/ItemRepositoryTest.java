package com.my.elasticsearch.mapper;

import com.alibaba.fastjson.JSON;
import com.my.elasticsearch.entity.domain.ItemDO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void createIndex() {
        ItemDO save = itemRepository.save(new ItemDO());
        log.info("创建索引response：{}", JSON.toJSONString(save));

    }
}
