package com.wangjun.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wangjun.modules.user.mapper.UserMapper;
import com.wangjun.modules.user.po.User;
import com.wangjun.modules.user.service.UserService;

/**
 * 标题
 *
 * @author wangjun
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //查数据库
        User user = userService.loadUserByUsername( userName );

        return (UserDetails) user;
    }
}
