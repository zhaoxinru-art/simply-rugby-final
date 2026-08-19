package com.sqa.simplyrugby.common.exception;

import com.sqa.simplyrugby.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 注入日志对象，替代e.printStackTrace()，更规范
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result<String> handle(Exception e) {
        // 用日志打印异常，替代e.printStackTrace()，消除警告
        log.error("系统异常：", e);
        // 用Result.fail()替代不存在的failed()方法
        return Result.fail("系统异常：" + e.getMessage());
    }
}