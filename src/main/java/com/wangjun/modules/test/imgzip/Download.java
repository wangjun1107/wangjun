package com.wangjun.modules.test.imgzip;

import lombok.Data;

/**
 * @author wangjun
 */
@Data
public class Download {
    private String name;
    private String url;

    public Download(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
