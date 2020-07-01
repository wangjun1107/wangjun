package com.wangjun.modules.wechat.bo;

import lombok.Data;

/**
 * <标题>
 *
 * @author wangjun
 * @date 2020-04-14 17:16
 */
@Data
public class WxCodeUnlimitedResponse {
    /**
     * 请求失败错误码
     */
    private String errcode;

    /**
     * 请求失败错误信息
     */
    private String errmsg;

    /**
     * 图片信息
     */
    private byte[] buffer;
}
