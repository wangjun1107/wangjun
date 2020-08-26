package com.wangjun.modules.test;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 标题
 *
 * @author wangjun
 */
@Data
public class Product {
    private String productName;

    private String productCode;

    private Integer price;

    private String color;
    private String remark;

    public Product() {
    }

    public Product(String productName, String productCode, Integer price, String color, String remark) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.color = color;
        this.remark = remark;
    }


    public List<Product> getProductList() {

      List<Product> products = new ArrayList<>();
        for(int i=1;i<100;i++){
            Product product1 = new Product("产品"+i, "cp"+i, 120+i, "红色"+i, "大卖"+i);
            products.add(product1);
        }
        Product product = new Product();
        product.setColor("222");
        products.add(product);
        return products;
    }
}
