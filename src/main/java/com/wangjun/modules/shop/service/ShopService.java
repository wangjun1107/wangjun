package com.wangjun.modules.shop.service;

import com.wangjun.modules.shop.bo.ResponseShop;
import com.wangjun.modules.shop.mapper.ShopMapper;
import com.wangjun.modules.shop.po.Shop;
import com.wangjun.modules.user.mapper.UserMapper;
import com.wangjun.modules.user.po.User;

import org.springframework.beans.BeanUtils;
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

    @Autowired
    private UserMapper userMapper;

    /**
     * 店铺详情
     * @param id 店铺id shopId
     * @return Shop
     */
    public ResponseShop detail(Long id){
        Shop shop = shopMapper.selectById(id);
        ResponseShop responseShop = new ResponseShop();
        BeanUtils.copyProperties(responseShop,shop);
        User user = userMapper.selectById(shop.getUserId());
        responseShop.setUserName(user.getName());
        return responseShop;
    }
}
