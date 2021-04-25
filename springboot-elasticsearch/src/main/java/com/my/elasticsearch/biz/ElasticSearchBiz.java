package com.my.elasticsearch.biz;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ElasticSearchBiz {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

}
