package com.wangjun.modules.user.po;

import java.util.Date;

import lombok.Data;

@Data
public class Shop {
    private Long id;

    private Integer shopCode;

    private String shopName;

    private String shopFacadePhoto;

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