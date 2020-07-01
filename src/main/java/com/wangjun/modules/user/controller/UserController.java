package com.wangjun.modules.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjun.modules.user.po.User;
import com.wangjun.modules.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * <标题>
 *
 * @author wangjun
 * @date 2020-03-19 14:09
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     */
    @GetMapping("/all")
    public List<User> userList() {
        log.info("获取用户列表");
        return userService.userList();
    }

    /**
     * 用户列表
     */
    @GetMapping
    public IPage<User> userList(Page<User> page) {
        log.info("获取用户分页列表");
        return userService.userPageList(page);
    }

    /**
     * 用户详情
     */
    @GetMapping("/{userId}")
    public User userDetail(@PathVariable Long userId) {
        log.info("获取用户详情userId:{}", userId);
        return userService.userDetail(userId);
    }

    /**
     * 更新用户
     */
    @PutMapping("/{userId}")
    public void update(@PathVariable Long userId, @RequestBody @Validated User user) {
        log.info("更新用户");
        user.setId(userId);
        userService.update(user);
    }

    /**
     * 创建用户
     */
    @PostMapping
    public void save(@RequestBody @Validated User user) {
        log.info("新增用户");
        userService.save(user);
    }

}
