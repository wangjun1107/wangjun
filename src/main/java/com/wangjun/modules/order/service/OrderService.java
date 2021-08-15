package com.wangjun.modules.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 订单
 *
 * @author wangjun
 * 2021-08-14
 **/
@Slf4j
@Service
public class OrderService {

    public Integer createOrder(String s) {
        log.info(LocalDateTime.now().toString());
        return 0;
    }
}
