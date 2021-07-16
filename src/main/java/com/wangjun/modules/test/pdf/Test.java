package com.wangjun.modules.test.pdf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 标题
 *
 * @author wangjun
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    private ObjProperties objProperties;

    @GetMapping("/hello")
    public String hello() {
        log.info(objProperties.getList().toString());
        return "Hello Word";
    }

    @GetMapping("/hello-exception")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void exception() {
    }

    @GetMapping("/users")
    public List<User> users() {
        return objProperties.getList();
    }

}
