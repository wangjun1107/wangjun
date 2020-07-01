package com.wangjun.config.web;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private Integer code;

    private String message;

    public static ExceptionResponse fail(ExceptionType exceptionType) {
        return fail(400, exceptionType.getMessage());
    }

    public static ExceptionResponse fail(Integer code, String message) {
        return ExceptionResponse.builder().code(code).message(message).build();
    }

}
