package com.tensquare.base.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import entity.Result;
import entity.StatusCode;

/**
 * 公共异常处理类 处理controller异常
 * RestControllerAdvice:对控制层拦截所有的异常 ，进行处理。
 *
 * @RestControllerAdvice = 注解：@ControllerAdvice+@ResponseBody
 */
@RestControllerAdvice
public class BaseExceptionHandler {
    private  static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * ExceptionHandler :接收异常  并处理异常的注解
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result error(Exception e){
        LOGGER.error("error info:", e);
        return new Result(false, StatusCode.NAERROR,"未知异常");
    }
}
