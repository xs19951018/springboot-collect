package com.my.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
public class ElasticsearchConfiguration {

    private final int ADDRESS_LENGTH = 2;
    private final String HTTP_SCHEME = "http";

    @Value("${elasticsearch.hosts}")
    private String[] hosts;

    /**
     * elasticsearch CRUD支持
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] httpHosts = Arrays.stream(hosts)
                .map(this::convertHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        log.info("elasticsearch:hosts:{}", Arrays.toString(httpHosts));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(httpHosts)
        );
        return restHighLevelClient;
    }

    /**
     * 转化地址
     * @param host
     * @return
     */
    private HttpHost convertHttpHost(String host) {
        if (StringUtils.isBlank(host)) {
            throw new RuntimeException("elasticsearch配置解析错误");
        }
        String[] address = host.split(":");
        if (ADDRESS_LENGTH == address.length) {
            return new HttpHost(address[0], Integer.parseInt(address[1]), HTTP_SCHEME);
        }
        return null;
    }
}
