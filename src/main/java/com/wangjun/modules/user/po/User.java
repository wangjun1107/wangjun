package com.wangjun.modules.user.po;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;

/**
 * <标题>
 *
 * @author wangjun
 * @date 2020-03-20 9:24
 */
@Data
public class User {

    private Long id;

    /**
     * 手机号
     **/
    @NotBlank
    private String phone;
    /**
     * 姓名
     **/
    private String userName;
    /**
     * 密码
     **/
    private String password;
    /**
     * 头像url
     **/
    private String avatarUrl;
    /**
     * 推荐人
     **/
    private Long recommend;
    /**
     * 创建时间
     **/
    private Date createTime;
    /**
     * 更新时间
     **/
    private Date updateTime;

    @Version
    private int version;
}
