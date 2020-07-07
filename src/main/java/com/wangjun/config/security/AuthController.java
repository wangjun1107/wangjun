package com.wangjun.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标题
 *
 * @author wangjun
 */
@RestController
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;

    /**
     * 登录
     */
    @PostMapping(value = "/auth/login")
    public String login( String username,String password ) throws AuthenticationException {
        // 登录成功会返回Token给用户
        return authService.login( username, password );
    }
}
