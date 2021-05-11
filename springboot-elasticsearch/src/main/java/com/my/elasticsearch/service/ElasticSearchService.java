package com.my.elasticsearch.service;

import com.my.elasticsearch.entity.dto.UserDTO;
import com.my.elasticsearch.entity.param.SearchWordParam;

import java.util.List;
import java.util.Map;

public interface ElasticSearchService {

    /**
     * 检索用户
     * @param searchUserParam
     * @return
     */
    List<UserDTO> searchUser(SearchWordParam searchUserParam);

    /**
     * 检索信息
     * @param clazz
     * @param searchUserParam
     * @return
     */
    List<Map<String, Object>> search(Class<?> clazz, SearchWordParam searchUserParam);

}
