package com.wangjun.modules.wechat.bo;

import lombok.Data;

/**
 * <标题>
 *
 * @author wangjun
 * @date 2020-04-14 11:34
 */
@Data
public class QRCodeParameters {

    private String access_token;
    private String scene = "";
    private String page = "pages/index/index";
    private Integer width = 430;
}
