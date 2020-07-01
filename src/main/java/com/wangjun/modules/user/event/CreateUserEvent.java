package com.wangjun.modules.user.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

/**
 * 创建用户成功事件
 *
 * @author wangjun
 */
public class CreateUserEvent extends ApplicationEvent {

    @Getter
    private final Long userId;

    @Getter
    private final String phone;

    public CreateUserEvent(Object source, Long userId, String phone) {
        super(source);
        this.userId = userId;
        this.phone = phone;
    }
}
