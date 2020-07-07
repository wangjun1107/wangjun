package com.wangjun.modules.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjun.modules.user.event.CreateUserEvent;
import com.wangjun.modules.user.mapper.UserMapper;
import com.wangjun.modules.user.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * <--用户service-->
 *
 * @author wangjun
 * @date 2020-03-20 9:45
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public List<User> userList() {
        return userMapper.selectList(null);
    }

    public IPage<User> userPageList(Page<User> page) {
        return userMapper.selectPage(page, null);
    }

    public User userDetail(Long userId) {
        return userMapper.selectById(userId);
    }

    public User loadUserByUsername(String userName){
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,userName));
    }

    public void update(User user) {
        userMapper.updateById(user);
    }

    @Transactional
    public void save(User user) {
        userMapper.insert(user);
        applicationEventPublisher.publishEvent(new CreateUserEvent(this, user.getId(), user.getPhone()));
    }

    /**
     * 创建用户监听器
     *
     * @param event 事件
     */
    @TransactionalEventListener
    public void listenerCreateUser(CreateUserEvent event) {
        log.info("监听到创建用户 id: {}, phone: {}", event.getUserId(), event.getPhone());
    }

}
