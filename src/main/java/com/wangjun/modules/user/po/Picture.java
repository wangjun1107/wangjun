package com.wangjun.modules.user.po;

import java.util.Date;

import lombok.Data;

@Data
public class Picture {
    private Long id;

    private Long shopId;

    private String pictureUrl;

    private Boolean isShare;

    private Date createTime;

    private Date updateTime;
}