package com.my.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfiguration {

    /**
     * elasticsearch CRUD支持
     *
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(@Value("${elastic.client.host}") String host) {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, 9200, "http")
                )
        );
        return restHighLevelClient;
    }

}
