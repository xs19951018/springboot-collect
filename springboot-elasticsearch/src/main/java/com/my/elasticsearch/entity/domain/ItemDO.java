package com.my.elasticsearch.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@TableName("item")
@Document(indexName = "item", type = "item")
public class ItemDO {

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 是否删除：1(删除) 0(未删除) */
    private Boolean isDeleted;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;

    /** 商品标题 */
    private String itemTitle;

    /** 商品备注 */
    private String itemDescription;

    /** 商品关键词 */
    private String itemKeyword;

    /** 商品备注 */
    private String itemRemarks;

    /** 主图地址 */
    private String mainImgUrl;

    /** 库存编号 */
    private Long stockId;

    /** 退货仓库 */
    private Long refundStockId;

    /** 计量单位 */
    private String unit;

    /** 供应商ID */
    private Long supplierId;

    /** 公司名称 */
    private String supplierName;

    /** 一级类目 */
    private Long firstCatId;

    /** 二级类目 */
    private Long secondCatId;

    /** 叶子类目ID */
    private Long catId;

    /** 品牌ID */
    private Long brandId;

    /** 含税率值 */
    private BigDecimal tax;

    /** 上架状态(0:下架状态；1:上架状态) */
    private Integer shelfStatus;

    /** 商品介绍短视频 */
    private String mainVideoUrl;

    /** 自定义分类id */
    private Integer categoryId;
}
