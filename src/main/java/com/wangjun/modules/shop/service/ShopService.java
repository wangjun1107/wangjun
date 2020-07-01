package com.wangjun.modules.shop.service;

import com.wangjun.modules.shop.mapper.ShopMapper;
import com.wangjun.modules.shop.po.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <标题>
 *
 * @author wangjun
 * 2020-07-01
 **/
@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 店铺详情
     * @param id 店铺id shopId
     * @return Shop
     */
    public Shop detail(Long id){
        return shopMapper.selectById(id);
    }
}
