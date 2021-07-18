package com.wangjun.modules.jsoup;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <标题>
 *
 * @author wangjun
 * 2021-07-18
 **/
@Data
@TableName("t_company")
public class Kw {
    private Long id;
    private String name;
    private String tag;

    public Kw() {
    }

    public Kw(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }
}
