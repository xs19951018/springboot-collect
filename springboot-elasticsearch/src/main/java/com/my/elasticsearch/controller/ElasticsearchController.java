package com.my.elasticsearch.controller;

import com.my.common.ResponseResult;
import com.my.elasticsearch.entity.domain.User;
import com.my.elasticsearch.entity.dto.UserDTO;
import com.my.elasticsearch.entity.param.SearchWordParam;
import com.my.elasticsearch.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elastic")
public class ElasticsearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;

    /**
     * 检索用户信息
     * @param searchUserParam
     * @return
     */
    @RequestMapping(value = "/searchUser", method = RequestMethod.POST)
    public ResponseResult<UserDTO> searchUser(@RequestBody @Valid SearchWordParam searchUserParam) {
        List<UserDTO> userDTOList = elasticSearchService.searchUser(searchUserParam);
        return ResponseResult.success(userDTOList);
    }

    /**
     * 检索信息
     * @param searchUserParam
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseResult<UserDTO> search(@RequestBody @Valid SearchWordParam searchUserParam) {
        List<Map<String, Object>> userDTOList = elasticSearchService.search(User.class, searchUserParam);
        return ResponseResult.success(userDTOList);
    }
}
