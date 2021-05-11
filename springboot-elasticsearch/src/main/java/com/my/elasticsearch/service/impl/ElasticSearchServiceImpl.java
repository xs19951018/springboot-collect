package com.my.elasticsearch.service.impl;

import com.my.common.OrikaMappingUtil;
import com.my.common.exception.ServiceException;
import com.my.elasticsearch.api.ElasticSearchApi;
import com.my.elasticsearch.common.annotation.Document;
import com.my.elasticsearch.common.annotation.ElasticField;
import com.my.elasticsearch.entity.constants.ElasticAnalyzerEnum;
import com.my.elasticsearch.entity.constants.ElasticErrCodeEnum;
import com.my.elasticsearch.entity.domain.User;
import com.my.elasticsearch.entity.dto.UserDTO;
import com.my.elasticsearch.entity.param.SearchWordParam;
import com.my.elasticsearch.service.ElasticSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private ElasticSearchApi elasticSearchApi;

    @Override
    public List<UserDTO> searchUser(SearchWordParam searchUserParam) {
        // 获取索引
        Document document = User.class.getAnnotation(Document.class);
        Optional.ofNullable(document).orElseThrow(() -> new ServiceException(ElasticErrCodeEnum.INDEX_NO_DEFINE));
        String index = document.index();

        if (!elasticSearchApi.existIndex(index)) {
            throw new ServiceException(ElasticErrCodeEnum.INDEX_NOT_EXIST);
        }

        SearchRequest searchRequest = new SearchRequest(index);
        // 条件构造
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(searchUserParam.getFrom());
        searchSourceBuilder.size(searchUserParam.getSize());
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(10));
        searchSourceBuilder.sort("id", SortOrder.ASC);
        searchSourceBuilder.query(QueryBuilders
                        .multiMatchQuery(searchUserParam.getKeyWord(), "name", "desc")
                        .analyzer(ElasticAnalyzerEnum.IK_SMART.toString().toLowerCase())
                );
        searchRequest.source(searchSourceBuilder);

        // 搜索
        List<Map<String, Object>> result = elasticSearchApi.searchRequest(searchRequest);
        List<UserDTO> userDTOList = OrikaMappingUtil.getInstance().mapAsList(result, UserDTO.class);

        return userDTOList;
    }

    @Override
    public List<Map<String, Object>> search(Class<?> clazz, SearchWordParam searchUserParam) {
        // 获取索引
        Document document = clazz.getAnnotation(Document.class);
        Optional.ofNullable(document).orElseThrow(() -> new ServiceException(ElasticErrCodeEnum.INDEX_NO_DEFINE));

        String index = document.index();
        if (!elasticSearchApi.existIndex(index)) {
            throw new ServiceException(ElasticErrCodeEnum.INDEX_NOT_EXIST);
        }
        // 查询条件
        SearchSourceBuilder searchSourceBuilder = convertSearchBilder(clazz, searchUserParam);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);

        List<Map<String, Object>> result = elasticSearchApi.searchRequest(searchRequest);
        return result;
    }

    /**
     * 根据实体来生成查询条件
     * @param clazz
     * @param searchWordParam
     * @return
     */
    private SearchSourceBuilder convertSearchBilder(Class clazz, SearchWordParam searchWordParam) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .from(searchWordParam.getFrom())
                .size(searchWordParam.getSize())
                .timeout(TimeValue.timeValueSeconds(10));

        String name;
        String anlyzer = null;
        List<String> multiField = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            ElasticField elasticField = f.getAnnotation(ElasticField.class);
            if (Objects.isNull(elasticField)) {
                continue;
            }

            name = elasticField.value();
            if (elasticField.search()) {
                multiField.add(name);
                if (anlyzer == null) {
                    anlyzer = elasticField.searchAnalyzer();
                }
            }
            if (elasticField.sort()) {
                searchSourceBuilder.sort(name, elasticField.sortOrder());
            }
        }

        searchSourceBuilder.query(QueryBuilders
                .multiMatchQuery(searchWordParam.getKeyWord(), multiField.toArray(new String[multiField.size()]))
                .analyzer(anlyzer.toLowerCase()));
        return searchSourceBuilder;
    }
}
