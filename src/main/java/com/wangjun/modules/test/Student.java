package com.wangjun.modules.test;

import lombok.Data;

/**
 * @author wangjun
 */
@Data
public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }
}
