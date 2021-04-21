package com.my.elasticsearch.service.impl;

import com.my.elasticsearch.entity.domain.ItemDO;
import com.my.elasticsearch.mapper.ItemMapper;
import com.my.elasticsearch.mapper.ItemRepository;
import com.my.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public ItemDO getItemById(Long itemId) {
        return itemRepository.findById(itemId).get();
    }

    @Override
    public Boolean addItem(ItemDO itemDO) {

        itemDO = new ItemDO();
        itemDO.setItemTitle("中粮 安达露西 纯正橄榄油750ml 西班牙进口 幼儿孕妇食用油 团购福利礼品");
        itemDO.setCategoryId(1);
        ItemDO save = itemRepository.save(itemDO);
        System.out.println(save);

       /* itemDO = new ItemDO();
        itemDO.setItemTitle("Java进阶之路");
        itemDO.setCategoryId(2);
        itemRepository.save(itemDO);

        itemDO = new ItemDO();
        itemDO.setItemTitle("PHP才是最强的");
        itemDO.setCategoryId(2);
        itemRepository.save(itemDO);

        itemDO = new ItemDO();
        itemDO.setItemTitle("Javascript前端脚本语言");
        itemDO.setCategoryId(2);
        itemRepository.save(itemDO);

        itemDO = new ItemDO();
        itemDO.setItemTitle("Mysql性能强大");
        itemDO.setCategoryId(2);
        itemRepository.save(itemDO);*/
        return Boolean.TRUE;
    }

    @Override
    public Boolean addItem2(ItemDO itemDO) {
        itemDO = new ItemDO();
        itemDO.setItemTitle("中粮 安达露西 纯正橄榄油750ml 西班牙进口 幼儿孕妇食用油 团购福利礼品");
        itemDO.setCategoryId(1);
        int save = itemMapper.insert(itemDO);
        System.out.println(save);
        return Boolean.TRUE;
    }
}
