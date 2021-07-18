package com.wangjun.modules.order.controller;

import com.wangjun.modules.order.bo.AppSubmitOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * appOrder
 *
 * @author wangjun
 * 2020-08-29
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/api/app/orders")
public class AppOrderController {

    /**
     * app提交订单
     *
     */
    @PostMapping("/submit")
    public String submit(@RequestBody AppSubmitOrder appSubmitOrder){

        log.info(appSubmitOrder.getUserId().toString());
        return "orderNO";
    }
}
