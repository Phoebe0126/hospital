package com.yygh.common.handler;

import com.yygh.common.exception.GeneralException;
import com.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: 陈玉婷
 * @create: 2021-07-20 20:06
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(GeneralException.class)
    public Result error(GeneralException e) {
        return Result.build(e.getCode(), e.getMessage());
    }


}
