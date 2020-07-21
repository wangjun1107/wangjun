package com.wangjun.modules.product.controller.open;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjun.common.request.PageRequest;
import com.wangjun.modules.product.po.Product;
import com.wangjun.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品对外接口
 *
 * @author wangjun
 */
@Api(tags = "商品对外开放接口")
@Slf4j
@RestController("OpenProductController")
@RequestMapping("/open/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    /**
     * 商品详情
     */
    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id){
        return productService.detail(id);
    }

    /**
     * 商品分页列表
     */
    @GetMapping
    public Page<Product> page(PageRequest pageRequest){
        Page<Product> page = new Page<>(pageRequest.getPageNumber(),pageRequest.getPageSize());
        return productService.page(page);
    }
}
