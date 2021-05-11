package com.my.elasticsearch.entity.param;

import com.my.elasticsearch.util.Page;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SearchWordParam extends Page {

    @NotBlank(message = "查询条件不能为空")
    private String keyWord;

}
