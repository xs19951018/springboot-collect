package com.my.elasticsearch.api;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.my.elasticsearch.common.annotation.Document;
import com.my.elasticsearch.common.annotation.ElasticField;
import com.my.elasticsearch.common.annotation.dto.ElasticAnnotationField;
import com.my.elasticsearch.common.annotation.enums.FieldType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * elasticsearch 接口
 */
@Slf4j
@Component
public class ElasticSearchApi {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @param index
     */
    public boolean createIndex(String index) throws Exception {
        // 判断索引是否存在
        if(this.existIndex(index)){
            return true;
        }
        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }


    public boolean createIndex(Class<?> clazz) throws Exception {
        Boolean result;

        Assert.isTrue(clazz.isAnnotationPresent(Document.class), "Unable to identify index name. " + clazz.getSimpleName()
                + " is not a Document. Make sure the document class is annotated with @Document(indexName=\"foo\")");

        Document document = clazz.getAnnotation(Document.class);
        String indexName = document.index();
        if(!existIndex(indexName)){
            result = createIndex(indexName,convertAnnotationField(clazz));
            return result;
        }

        return false;
    }

    /**
     * 创建索引
     * @param indexName
     * @throws IOException
     */
    private boolean createIndex(String indexName,List<ElasticAnnotationField> annotationFieldList) throws IOException {
        try {
            Map<String,Object> source = Maps.newHashMap();
            Map<String,Object> properties = convertPropertyMapping(annotationFieldList);
            source.put("enabled",Boolean.TRUE);
            CreateIndexRequest index = new CreateIndexRequest(indexName);
            XContentBuilder builder = JsonXContent.contentBuilder();
            builder.startObject()
                    .startObject("mappings")
                    .field("_source",source)
                    .field("properties",properties)
                    .endObject()
                    .startObject("settings")
                    .field("number_of_shards",3)
                    .field("number_of_replicas",1)
                    .endObject()
                    .endObject();

            index.source(builder);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(index, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 封装索引的文档结构
     * @param annotationFieldList
     * @return
     * @throws IOException
     */
    public Map<String,Object> convertPropertyMapping(List<ElasticAnnotationField> annotationFieldList) throws IOException {
        Map<String,Object> propertie = null;
        Map<String,Object> properties = Maps.newHashMap();

        for(ElasticAnnotationField annotationField : annotationFieldList){
            propertie = Maps.newHashMap();
            if(annotationField.getFieldMeta().type() == FieldType.Nested){
                propertie.put("type",annotationField.getFieldMeta().type().toString().toLowerCase());
                propertie.put("properties",convertPropertyMapping(annotationField.getAnnotationFieldList()));
            }else{
                propertie.put("type",annotationField.getFieldMeta().type().toString().toLowerCase());
                propertie.put("index",annotationField.getFieldMeta().index());

                if(StringUtils.isNotBlank(annotationField.getFieldMeta().analyzer())){
                    propertie.put("analyzer",annotationField.getFieldMeta().analyzer());
                }
                if(StringUtils.isNotBlank(annotationField.getFieldMeta().searchAnalyzer())){
                    propertie.put("search_analyzer",annotationField.getFieldMeta().searchAnalyzer());
                }
            }
            properties.put(annotationField.getFieldMeta().value(),propertie);
        }
        return properties;
    }

    /**
     * 递归文档的所有字段
     * @param clazz
     * @return
     */
    private List<ElasticAnnotationField> convertAnnotationField(Class<?> clazz){
        Class<?> type;
        List<ElasticAnnotationField> annotationFieldList = new ArrayList<>();
        for(Field field : clazz.getDeclaredFields()){
            //获取字段中包含fieldMeta的注解
            if(null != field){
                ElasticField fieldMeta = field.getAnnotation(ElasticField.class);
                if(null != fieldMeta){
                    ElasticAnnotationField annotationField = new ElasticAnnotationField(fieldMeta,field);
                    // 是否嵌套
                    if(fieldMeta.type() == FieldType.Nested){
                        if(field.getType().isArray()){
                            type = field.getType().getComponentType();
                        }else{
                            type = field.getType();
                        }
                        annotationField.setAnnotationFieldList(convertAnnotationField(type));
                    }
                    annotationFieldList.add(annotationField);
                }
            }
        }
        return annotationFieldList;
    }

    /**
     * 判断索引是否存在
     * @param index
     */
    public boolean existIndex(String index) {
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest().indices(index);
            return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("索引不存在:{}", e);
            return Boolean.FALSE;
        }
    }

    /**
     * 搜索请求
     * @param searchRequest
     * @return
     */
    public List<Map<String, Object>> searchRequest(SearchRequest searchRequest) {
        List<Map<String, Object>> searchList = new ArrayList<>();
        try {
            log.info("搜索请求request:{}", searchRequest.source().toString());
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] hits = searchResponse.getHits().getHits();
            searchList = Arrays.stream(hits).map(e -> e.getSourceAsMap()).collect(Collectors.toList());
            log.info("搜索结果response,count:{},result:{}", searchList.size(), JSON.toJSONString(searchList));
        } catch (IOException e) {
            log.error("搜索失败：{}", e);
        }
        return searchList;
    }

    /**
     * 搜索请求
     * @param index
     * @param keyword
     */
    public List<Map<String, Object>> searchRequest(String index, String keyword) throws Exception {
        SearchRequest searchRequest;
        if(StringUtils.isEmpty(index)){
            searchRequest = new SearchRequest();
        }else {
            searchRequest = new SearchRequest(index);
        }
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 第几页
        searchSourceBuilder.from(0);
        // 每页多少条数据
        searchSourceBuilder.size(1000);
        // 配置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").field("desc");
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        //searchSourceBuilder.highlighter(highlightBuilder);
        // 精确查询
//        QueryBuilders.termQuery();
        // 匹配所有
//        QueryBuilders.matchAllQuery();
        // 最细粒度划分：ik_max_word，最粗粒度划分：ik_smart
        searchSourceBuilder.query(QueryBuilders.matchQuery("desc", keyword).analyzer("ik_max_word"));
//        searchSourceBuilder.query(QueryBuilders.matchQuery("name", keyword));
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(10));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
            HighlightField title = highlightFieldMap.get("name");
            HighlightField description = highlightFieldMap.get("desc");
            // 原来的结果
            Map<String, Object> sourceMap = searchHit.getSourceAsMap();
            // 解析高亮字段，替换掉原来的字段
            if (title != null){
                Text[] fragments = title.getFragments();
                StringBuilder n_title = new StringBuilder();
                for (Text text : fragments){
                    n_title.append(text);
                }
                sourceMap.put("name", n_title.toString());
            }
            if (description != null){
                Text[] fragments = description.getFragments();
                StringBuilder n_description = new StringBuilder();
                for (Text text : fragments){
                    n_description.append(text);
                }
                sourceMap.put("desc", n_description.toString());
            }
            results.add(sourceMap);
        }
        return results;
    }

    /**
     * 搜索所有id
     * @param index
     */
    public List<Integer> searchAllRequest(String index) throws Exception {
        // 搜索请求
        SearchRequest searchRequest;
        if(StringUtils.isEmpty(index)){
            searchRequest = new SearchRequest();
        }else {
            searchRequest = new SearchRequest(index);
        }
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 第几页
        searchSourceBuilder.from(0);
        // 每页多少条数据
        searchSourceBuilder.size(1000);
        // 匹配所有
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(10));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Integer> results = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            results.add(Integer.valueOf(searchHit.getId()));
        }
        return results;
    }
}
