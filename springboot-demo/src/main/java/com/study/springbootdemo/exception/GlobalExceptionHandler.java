package com.study.springbootdemo.exception;

import com.study.springbootdemo.common.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.study.springbootdemo.common.BusinessException;
import org.springframework.http.ResponseEntity;
import com.study.springbootdemo.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
@RestControllerAdvice // = @ControllerAdvice + @ResponseBody，拦截所有 Controller 异常，直接返回 JSON
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1、参数校验
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        // 取第一个字段的错误信息
        /*
         * e.getBindingResult() → 所有绑定结果
         * .getFieldErrors() → 每个字段的错误列表
         * .getFieldError() → 第一个错误
         * .getDefaultMessage() → 你注解里写的 message，如「书名不能为空」
         */
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        logger.warn("参数校验异常:{}", message);
        return Result.error(ResultCode.BAD_REQUEST, message);
    }

    // 2、业务异常
    @ExceptionHandler(BusinessException.class)
    // public Result<Void> handleBusinessException(BusinessException e) {
    //     return Result.error(e.getCode(), e.getMsg());
    // }
    //更规范写法（HTTP 状态码和 body 一致）
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException e) {
        logger.warn("业务异常,code:{},msg:{}", e.getCode(), e.getMsg());
        return ResponseEntity.status(e.getCode()).body(Result.error(e.getCode(), e.getMsg()));
    }

    // 3、兜底：其他异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleException(Exception e) {
        // e.printStackTrace();
        logger.error("服务器异常:{}", e.getMessage());
        return Result.error(ResultCode.SERVER_ERROR, "服务器异常");
    }

    @ExceptionHandler({
        AccessDeniedException.class,
        AuthorizationDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<Void> handleAccessDenied(Exception e) {
        logger.warn("无权限: {}", e.getMessage());
        return Result.error(ResultCode.FORBIDDEN, "Access denied");
    }
    
}
