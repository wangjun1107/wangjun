package com.wangjun.modules.wechat.bo;

import lombok.Data;

/**
 * <标题>
 *
 * @author wangjun
 * @date 2020-04-14 16:44
 */
@Data
public class AccessToken {

    private String access_token;
    private String expires_in;
    private String errcode;
    private String errmsg;
}
