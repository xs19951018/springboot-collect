package com.my.elasticsearch.service.impl;

import com.google.common.collect.Maps;
import com.my.elasticsearch.common.annotation.ElasticField;
import com.my.elasticsearch.entity.domain.User;
import com.my.elasticsearch.entity.dto.ElasticAnnotationField;
import com.my.elasticsearch.service.ElasticSearchService;
import com.my.elasticsearch.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RabbitMqUtils
 * @author luoyu
 * @date 2019/03/16 22:08
 * @description
 */
@Slf4j
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @param index
     */
    @Override
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

    @Override
    public boolean createIndex(Class<?> clazz) throws Exception {
        Boolean result;

        Assert.isTrue(clazz.isAnnotationPresent(Document.class), "Unable to identify index name. " + clazz.getSimpleName()
                + " is not a Document. Make sure the document class is annotated with @Document(indexName=\"foo\")");

        Document document = clazz.getAnnotation(Document.class);
        String indexName = document.indexName();
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
    @Override
    public boolean existIndex(String index) throws Exception {
        // 判断索引是否存在
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     * @param index
     */
    @Override
    public boolean deleteIndex(String index) throws Exception {
        // 判断索引是否存在
        if(!this.existIndex(index)){
            return true;
        }
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }

    /**
     * 新增文档
     * @param index
     * @param id
     * @param content
     */
    @Override
    public boolean addDocument(String index, String id, String content) throws Exception {
        if(!this.createIndex(index)){
            return false;
        }

        IndexRequest indexRequest = new IndexRequest(index);
        // 设置超时时间
        indexRequest.id(id);
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        // 转换为json字符串
        indexRequest.source(content, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.status().getStatus() == 200;
    }

    /**
     * 判断是否存在文档
     * @param index
     * @param id
     */
    @Override
    public boolean isExistsDocument(String index, String id) throws Exception {
        // 判断是否存在文档
        GetRequest getRequest = new GetRequest(index, id);
        // 不获取返回的_source的上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * 获取文档
     * @param index
     * @param id
     */
    @Override
    public String getDocument(String index, String id) throws Exception {
        // 获取文档
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsString();
    }

    /**
     * 更新文档
     * @param index
     * @param id
     * @param content
     */
    @Override
    public boolean updateDocument(String index, String id, String content) throws Exception {
        // 更新文档
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.timeout(TimeValue.timeValueSeconds(1));
        updateRequest.doc(content, XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.status().getStatus() == 200;
    }

    /**
     * 删除文档
     * @param index
     * @param id
     */
    @Override
    public boolean deleteDocument(String index, String id) throws Exception {
        if(!this.isExistsDocument(index, id)){
            return true;
        }

        // 删除文档
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        deleteRequest.timeout(TimeValue.timeValueSeconds(1));
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return deleteResponse.status().getStatus() == 200;
    }

    /**
     * 批量插入
     * @param index
     * @param contents
     */
    @Override
    public boolean bulkRequest(String index, List<User> contents) throws Exception {
        // 批量插入
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(TimeValue.timeValueSeconds(1));
        contents.forEach(x -> {
            bulkRequest.add(
                    new IndexRequest(index)
                            .id(x.getId().toString())
                            .source(JsonUtils.objectToJson(x), XContentType.JSON));
        });
        BulkResponse bulkItemResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkItemResponse.hasFailures();
    }

    /**
     * 搜索请求
     * @param index
     * @param keyword
     */
    @Override
    public List<Map<String, Object>> searchRequest(String index, String keyword) throws Exception {
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
    @Override
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
