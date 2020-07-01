package com.wangjun.config.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web自动配置类
 */
@Configuration
public class WebAutoConfiguration {
    /**
     * 新的Web配置, 主要使用了http状态码响应以及直接返回正确响应结果, 不包装code message
     */
    @Configuration
   public static class NewConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public RestResponseEntityExceptionHandler restResponseEntityExceptionHandler() {
            return new RestResponseEntityExceptionHandler();
        }
    }

}
