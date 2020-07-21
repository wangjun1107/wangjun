package com.wangjun.modules.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjun.modules.product.mapper.ProductMapper;
import com.wangjun.modules.product.po.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <标题>
 *
 * @author wangjun
 * 2020-07-05
 **/
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public Product detail(Long id) {
        return productMapper.selectById(id);
    }

    public Page<Product> page(Page<Product> page) {
        return productMapper.selectPage(page, null);
    }
}
