package com.wangjun.modules.product.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品
 *
 * @author wangjun
 * 2020-07-05
 **/
@Data
public class Product {
    /**
     * 商品主键id
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private String pictureUrl;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 销售价格(分)
     */
    private Integer salesPrice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
