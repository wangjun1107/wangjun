package com.wangjun;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wangjun.modules.wechat.controller.WeChatController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class WangjunApplicationTests {

    @Autowired
    private WeChatController weChatController;

    @Test
    void contextLoads() {}

    @Test
    void test(){
        System.out.println(String.format("T%s%07d","123",0));
    }


}
