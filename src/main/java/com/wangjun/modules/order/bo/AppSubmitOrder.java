package com.wangjun.modules.order.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * app提交订单信息
 *
 * @author wangjun
 * 2020-08-29
 **/
@Data
public class AppSubmitOrder {

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

    /**
     * 支付方式
     */
    @NotNull
    private Integer paymentMethod;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 备注
     */
    private String remakes;

    /**
     * 餐具数量
     */
    private Integer quantityOfTableware;

    /**
     * 订单商品
     */
    private List<AppSubmitOrderProduct> orderProducts;
}
