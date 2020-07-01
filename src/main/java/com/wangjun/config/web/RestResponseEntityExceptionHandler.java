package com.wangjun.config.web;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * RequestParam或PathVariable没有通过校验规则
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>(ExceptionResponse.fail(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
        HttpStatus status, WebRequest request) {
        // 请求方法不支持
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            body = ExceptionResponse.fail(ExceptionType.REQUEST_METHOD_NOT_SUPPORTED.getCode(), ExceptionMessageUtils
                .getHttpRequestMethodNotSupportedExceptionMessage((HttpRequestMethodNotSupportedException)ex));
        }
        // 媒体类型不支持
        else if (ex instanceof HttpMediaTypeNotSupportedException) {
            body = ExceptionResponse.fail(ExceptionType.MEDIA_TYPE_NOT_SUPPORTED.getCode(), ExceptionMessageUtils
                .getHttpMediaTypeNotSupportedExceptionMessage((HttpMediaTypeNotSupportedException)ex));
        }
        // 缺少请求参数
        else if (ex instanceof MissingServletRequestParameterException) {
            body = ExceptionResponse.fail(ExceptionType.BAD_REQUEST.getCode(), ExceptionMessageUtils
                .getMissingServletRequestParameterExceptionMessage((MissingServletRequestParameterException)ex));
        }
        // Servlet请求绑定异常
        else if (ex instanceof ServletRequestBindingException) {
            body = ExceptionResponse.fail(ExceptionType.SERVLET_REQUEST_BINDING_EXCEPTION);
        }
        // Bean转换不支持
        else if (ex instanceof ConversionNotSupportedException) {
            body = ExceptionResponse.fail(ExceptionType.SERVER_ERROR);
        }
        // 参数类型不匹配
        else if (ex instanceof TypeMismatchException) {
            body = ExceptionResponse.fail(ExceptionType.BAD_REQUEST.getCode(),
                ExceptionMessageUtils.getTypeMismatchExceptionMessage((TypeMismatchException)ex));
        }
        // 消息不可读
        else if (ex instanceof HttpMessageNotReadableException) {
            body = ExceptionResponse.fail(ExceptionType.HTTP_MESSAGE_NOT_READABLE);
        }
        // 方法参数不合法
        else if (ex instanceof MethodArgumentNotValidException) {
            body = ExceptionResponse.fail(ExceptionType.BAD_REQUEST.getCode(), ExceptionMessageUtils
                .getFieldErrorMessage(((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors()));
        }
        // 文件上传缺少Part
        else if (ex instanceof MissingServletRequestPartException) {
            body = ExceptionResponse.fail(ExceptionType.MISSING_SERVLET_REQUEST_PART);
        }
        // 绑定异常
        else if (ex instanceof BindException) {
            body = ExceptionResponse.fail(ExceptionType.BAD_REQUEST.getCode(),
                ExceptionMessageUtils.getFieldErrorMessage(((BindException)ex).getBindingResult().getFieldErrors()));
        }
        if (status.is4xxClientError()) {
            log.warn(ex.getMessage());
            log.warn("##################################################");
        } else if (status.is5xxServerError()) {
            log.error(ex.getMessage());
            log.warn("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
