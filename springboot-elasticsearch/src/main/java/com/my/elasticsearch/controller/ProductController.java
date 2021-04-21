package com.my.elasticsearch.controller;

import com.my.elasticsearch.entity.domain.ItemDO;
import com.my.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/getItemById")
    public ItemDO getItemById(@RequestParam("itemId") Long itemId){
        return productService.getItemById(itemId);
    }

    @RequestMapping("/addItem")
    public Boolean addItem(){
        return productService.addItem(new ItemDO());
    }

    @RequestMapping("/addItem2")
    public Boolean addItem2(){
        return productService.addItem2(new ItemDO());
    }


}
