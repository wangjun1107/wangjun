package com.wangjun.modules.order.bo;

import lombok.Data;

/**
 * app提交订单商品参数
 *
 * @author wangjun
 * 2020-08-29
 **/
@Data
public class AppSubmitOrderProduct {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer productNumber;
}
