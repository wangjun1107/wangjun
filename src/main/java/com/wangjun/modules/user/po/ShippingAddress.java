package com.wangjun.modules.user.po;

import java.util.Date;

import lombok.Data;

@Data
public class ShippingAddress {
    private Long id;

    private Long userId;

    private Integer provinceId;

    private Integer cityId;

    private Integer countryId;

    private String provinceName;

    private String cityName;

    private String countryName;

    private String address;

    private Date createTime;

    private Date updateTime;

}