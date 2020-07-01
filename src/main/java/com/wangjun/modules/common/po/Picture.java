package com.wangjun.modules.common.po;

import lombok.Data;

import java.util.Date;

@Data
public class Picture {
    private Long id;

    private Long shopId;

    private String pictureUrl;

    private Boolean isShare;

    private Date createTime;

    private Date updateTime;
}