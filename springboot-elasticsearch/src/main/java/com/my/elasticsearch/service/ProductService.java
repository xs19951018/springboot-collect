package com.my.elasticsearch.service;

import com.my.elasticsearch.entity.domain.ItemDO;

public interface ProductService {

    ItemDO getItemById(Long itemId);

    Boolean addItem(ItemDO itemDO);

    Boolean addItem2(ItemDO itemDO);

}
