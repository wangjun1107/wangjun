package com.wangjun.modules.test.imgzip;

import java.util.List;

import lombok.Data;

/**
 * @author wangjun
 */
@Data
public class Contents {
    private String name;
    private List<Download> downloads;

    public Contents(String name, List<Download> downloads) {
        this.name = name;
        this.downloads = downloads;
    }
}
