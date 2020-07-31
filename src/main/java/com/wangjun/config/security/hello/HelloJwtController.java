package com.wangjun.config.security.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.wangjun.config.security.config.JwtUserDetailsService;
import com.wangjun.config.security.jwt.JwtTokenTemplate;
import com.wangjun.config.security.param.JwtParam;

@RestController
@RequestMapping(value = "auth")
public class HelloJwtController {

    @Autowired
    private JwtTokenTemplate jwtTokenTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "login")
    public String login(@RequestBody JwtParam body) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(body.getUsername());
        return jwtTokenTemplate.generateToken(userDetails);
    }

    @GetMapping(value = "hello")
    public String hello() {
        return "Hello Jwt!!!";
    }

}