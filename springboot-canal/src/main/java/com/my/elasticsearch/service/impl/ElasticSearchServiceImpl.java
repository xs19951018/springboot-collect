package com.my.elasticsearch.service.impl;

import com.my.elasticsearch.api.ElasticSearchApi;
import com.my.elasticsearch.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private ElasticSearchApi elasticSearchApi;

    @Override
    public List<Map<String, Object>> searchRequest(String index, String keyword) throws Exception {
        return null;
    }
}
