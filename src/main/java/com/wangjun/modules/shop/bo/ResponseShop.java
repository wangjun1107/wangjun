package com.wangjun.modules.shop.bo;

import lombok.Data;

/**
 * @author wangjun
 */
@Data
public class ResponseShop {
    private Long id;

    private Integer shopCode;

    private String shopName;

    private String shopDesc;

    private String shopFacadePhoto;

    private String userName;

    private String provinceName;

    private String cityName;

    private String countryName;

    private String address;

}