package com.wangjun.modules.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 标题
 *
 * @author wangjun
 */
@Component
@ConfigurationProperties(prefix = "obj")
public class ObjProperties {
    private final List<User> list = new ArrayList<>();

    public List<User> getList() {
        return this.list;
    }
}
