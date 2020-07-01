package com.wangjun.modules.shop.controller.open;

import com.wangjun.modules.shop.po.Shop;
import com.wangjun.modules.shop.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app店铺
 *
 * @author wangjun
 * @date 2020-03-19 14:09
 */
@Api(tags = "app店铺")
@Slf4j
@Validated
@RestController("OpenShopController")
@RequestMapping("/open/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 店铺详情
     * @param id id
     */
    @GetMapping("/{id}")
    @ApiOperation("店铺详情")
    public Shop detail(@PathVariable Long id){
        return shopService.detail(id);
    }
}
