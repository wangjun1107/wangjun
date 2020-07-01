package com.wangjun.modules.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店铺后台
 *
 * @author wangjun
 * @date 2020-03-19 14:09
 */
@Slf4j
@Validated
@RestController("openShopController")
@RequestMapping("/api/shops")
public class ShopController {

}
