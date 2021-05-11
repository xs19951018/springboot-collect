package com.my.elasticsearch.service;


import java.util.List;
import java.util.Map;

public interface ElasticSearchService {

    /**
     * 搜索请求
     * @param index
     * @param keyword
     */
    List<Map<String, Object>> searchRequest(String index, String keyword) throws Exception;

}
